package com.curves.franchise.util;

import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import com.curves.franchise.repository.PjSumRepository;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurvesParser {
    private final Logger logger = LoggerFactory.getLogger(CurvesParser.class);

    private final Pattern chinesePattern = Pattern.compile("([\\u4e00-\\u9fa5]+)");
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final Map<String, Integer> nameId = new HashMap<>();
    int year, month;
    private String dir = null;
    private List<String> idErr = new ArrayList<>();
    private List<String> ymErr = new ArrayList<>();
    private List<String> nonCaPjErr = new ArrayList<>();

    final CaRepository caRepo;
    final PjSumRepository pjSumRepo;
    private final ClubRepository clubRepo;

    private FormulaEvaluator evaluator = null;

    public CurvesParser(PjSumRepository pjSumRepo, CaRepository caRepo, ClubRepository clubRepo, String dir) {
        this.pjSumRepo = pjSumRepo;
        this.caRepo = caRepo;
        this.clubRepo = clubRepo;
        this.dir = dir;
    }

    public void process() {
        buildClubNameIdMap();

        String home = System.getProperty("user.home");
        String curvesData = home + File.separator + "curves_data";
        logger.info("user.home + curves data folder: " + curvesData);
        if (dir.startsWith("YEAR")) {
            // ?dir=YEAR-2014
            this.year = Integer.parseInt(dir.substring(5));
            for (int m = 0; m < 12; m++) {
                this.month = m;
                File folder = new File(curvesData + File.separator + this.year + File.separator
                        + this.year + (m < 9 ? "0" + (m+1) : (m+1)));
                logger.info("---YEAR---"+folder.getName());
                if (folder.exists()) {
                    processWorkbooks(FileUtils.listFiles(folder, null, true));
                }
            }
        } else if (dir.startsWith("MONTH")) {
            // ?dir=MONTH-201408
            this.year = Integer.parseInt(dir.substring(6, 10));
            this.month = Integer.parseInt(dir.substring(10)) - 1;
            File folder = new File(curvesData + File.separator + this.year + File.separator + this.year + this.month);
            logger.info("---MONTH---" + folder.getName());
            if (folder.exists()) {
                processWorkbooks(FileUtils.listFiles(folder, null, true));
            }
        } else if (dir.startsWith("TEST")) {
            // ?dir=TEST
            File folder = new File(curvesData + File.separator + "test");
            if (folder.exists()) {
                Collection<File> allFiles = FileUtils.listFiles(folder, null, true);
                for (File f : allFiles) {
                    this.year = Integer.parseInt(f.getName().substring(0, 4));
                    this.month = Integer.parseInt(f.getName().substring(4, 6)) - 1;
                    logger.info("---processing---year:"+year+", month:"+month+",file:"+f.getName());
                    processOneFile(f);
                }
                printContent("club id", idErr);
                printContent("year-month: " + year + "-" + month + " not found", ymErr);
                printContent("non-CA/PJ", nonCaPjErr);
            }
        } else {
        }
    }

    private void processWorkbooks(Collection<File> allFiles) {
        for (File f : allFiles) {
            processOneFile(f);
        }
        printContent("club id", idErr);
        printContent("year-month: "+year+"-"+month+" not found", ymErr);
        printContent("non-CA/PJ", nonCaPjErr);
    }

    private void processOneFile(File f) {
        logger.info("=== processing file: " + f.getName());
        int clubId = -1;
        Workbook wb;
        try {
            wb = WorkbookFactory.create(f);
            String s = parseChinese(f.getName());
            clubId = getIdByName(s);
            if (clubId == -1) {
                clubId = getIdByName(s + "\u5e97");
            }
        } catch (Exception e) {
            idErr.add(f.getName());
            return;
        }

        if (clubId == -1) {
            idErr.add(f.getName());
            return;
        }

        Sheet sh = getSheet(wb, f.getName());
        if (sh == null) {
            ymErr.add(f.getName());
            return;
        }

        evaluator = wb.getCreationHelper().createFormulaEvaluator();
        if (f.getName().indexOf("CA") != -1) {
            logger.info("--> Found CA, file: " + f.getName()+", sheet: "+sh.getSheetName());
            new CaDataHandler(this).processCA(sh, evaluator, clubId);
        } else if (f.getName().indexOf("PJ") != -1) {
            logger.info("==> Found PJ, file: " + f.getName()+", sheet: "+sh.getSheetName());
            new PjDataHandler(this).processPJ(sh, evaluator, clubId);
        } else {
            nonCaPjErr.add(f.getName()+" - "+sh.getSheetName());
        }

        try {
            wb.close();
        } catch (Exception ignored) {
        }
    }

    private void printContent(String msg, List<String> list) {
        if (list.size() == 0) {
            return;
        }
        logger.error("========" + msg + "=======");
        for (String s : list) {
            logger.error(s);
        }
    }

    Sheet getSheet(Workbook wb, String fname) {
        Sheet sh = wb.getSheet(year + "0" + (month+1));
        if (sh == null) {
            sh = wb.getSheet(year + "" + (month+1));
        }
        if (sh == null) {
            sh = wb.getSheet(year + ".0" + (month+1));
        }
        if (sh == null) {
            sh = wb.getSheet(year + "." + (month+1));
        }
        if (sh == null) {
            sh = wb.getSheet(year + "-" + (month+1));
        }
        if (sh == null) {
            sh = wb.getSheet(year + "-0" + (month+1));
        }

        if (sh == null) {
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                sh = wb.getSheetAt(i);
                Calendar c = Calendar.getInstance();
                try {
                    if (fname.indexOf("PJ") != -1) {
                        c.setTime(sh.getRow(15).getCell(0).getDateCellValue());
                    } else {
//                        c.setTime(sh.getRow(0).getCell(7).getDateCellValue());
                    }
                } catch (Exception e) {
                }
                if (c.get(Calendar.YEAR) == year && c.get(Calendar.MONTH) == month) {
                    return sh;
                }
            }
            sh = null;
        }
        return sh;
    }

    double getNumberValue(Sheet sh, int row, int col) {
        double value = 0;
        try {
            Cell cell = sh.getRow(row).getCell(col);
            if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                CellValue cellValue = evaluator.evaluate(cell);
                value = cellValue.getNumberValue();
            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                value = cell.getNumericCellValue();
            }
        } catch (Exception ignored) {
        }
        return value;
    }

    private String parseChinese(String s) {
        Matcher mat = chinesePattern.matcher(s);
        if (mat.find()) {
            return mat.group(0);
        }
        return "";
    }

    String replaceChinese(String src, String target) {
        Matcher mat = chinesePattern.matcher(src);
        if (mat.find()) {
            return mat.replaceAll(target);
        }
        return "";
    }

    String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return sdf.format(cell.getDateCellValue());
                } else {
                    return "" + cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return "" + cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                CellValue cellValue = evaluator.evaluate(cell);
                if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    double d = cellValue.getNumberValue();
                    if (d < 1 && d > 0) {
                        return ((int)(d * 100)) + "%";
                    }
                } else if (cellValue.getCellType() == Cell.CELL_TYPE_ERROR) {
                    return null;
                }
                return cellValue.formatAsString();
            default:
                return "";
        }
    }

    private void buildClubNameIdMap() {
        try {
            Iterable<Club> clubs = clubRepo.findAll();
            for (Club club : clubs) {
                nameId.put(club.getName(), club.getClubId());
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private int getIdByName(String clubName) {
        Set<String> keys = nameId.keySet();
        for (String key : keys) {
            if (key.contains(clubName)) {
                return nameId.get(key);
            }
        }
        return -1;
    }
}
