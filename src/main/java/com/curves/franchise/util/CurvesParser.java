package com.curves.franchise.util;

import com.curves.franchise.repository.CaRepository;
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
    private Logger logger = LoggerFactory.getLogger(CurvesParser.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    File baseDir = null;
    List<File> allPJ = new ArrayList<>();
    List<File> allCA = new ArrayList<>();
    List<File> allOthers = new ArrayList<>();
    Map<String, Integer> nameId = new HashMap<>();
    int year, month;

    CaRepository caRepo;
    PjSumRepository pjSumRepo;

    FormulaEvaluator evaluator = null;

    public CurvesParser(PjSumRepository pjSumRepo, CaRepository caRepo, int year, int month) {
        this.pjSumRepo = pjSumRepo;
        this.caRepo = caRepo;
        if (year == -1 && month == -1) {
            sortFiles("C:\\Users\\212307274\\Projects\\Franchise\\doc\\data\\test");
        } else if (month > 12) {
            sortFiles("C:\\Users\\212307274\\Projects\\Franchise\\doc\\data\\" + year);
        } else {
            sortFiles("C:\\Users\\212307274\\Projects\\Franchise\\doc\\data\\" + year + "\\" + year + "0" + month);
        }
        buildClubNameIdMap();
        this.year = year;
        this.month = month - 1;
    }

    public void processPJ() {
        new PjDataHandler(this).processPJ();
    }

    public void processCA() {
        new CaDataHandler(this).processCA();
    }

    int getCellIntValue(Sheet sh, int row, int col) {
        int value = -1;
        try {
            Cell cell = sh.getRow(row).getCell(col);
            if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                CellValue cellValue = evaluator.evaluate(cell);
                value = (int)cellValue.getNumberValue();
            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                value = (int)cell.getNumericCellValue();
            }
        } catch (Exception e) {
        }
        return value;
    }

    String parseChinese(String s) {
        Pattern pat = Pattern.compile("([\\u4e00-\\u9fa5]+)");
        Matcher mat = pat.matcher(s);
        if (mat.find()) {
            return mat.group(0);
        }
        return "";
    }

    String getClubNameFromFileName(File f) {
        return parseChinese(f.getName());
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

    void buildClubNameIdMap() {
        File f = new File("C:\\Users\\212307274\\Projects\\Franchise\\doc\\data\\clubInfo.xlsx");
        try {
            Workbook wb = WorkbookFactory.create(f);
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sh = wb.getSheetAt(i);
                for (Row row : sh) {
                    if (row.getRowNum() == 0 || row.getRowNum() > 88) {
                        continue;
                    }
                    int clubId = (int)row.getCell(4).getNumericCellValue();
                    String clubName = row.getCell(1).getStringCellValue();
                    nameId.put(clubName, clubId);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    int getIdByName(String clubName) {
        Set<String> keys = nameId.keySet();
        for (String key : keys) {
            if (key.contains(clubName)) {
                return nameId.get(key);
            }
        }
        return -1;
    }

    boolean isSheetNameMatch(String name) {
        return (name.startsWith(year+"") && name.endsWith(month+""));
    }

    void sortFiles(String baseFolder) {
        baseDir = new File(baseFolder);
        Collection<File> allFiles = FileUtils.listFiles(baseDir, null, true);
        logger.info("=== files count: " + allFiles.size());
        for (File f : allFiles) {
            String fName = f.getName().toUpperCase();
            if (fName.indexOf("CA") != -1) {
                allCA.add(f);
            } else if (fName.indexOf("PJ") != -1) {
                allPJ.add(f);
            } else {
                allOthers.add(f);
            }
        }
        logger.info("---PJ:"+allPJ.size()+",CA:"+allCA.size()+",others:"+allOthers.size());
    }
}
