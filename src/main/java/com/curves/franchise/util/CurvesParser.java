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

    Pattern chinesePattern = Pattern.compile("([\\u4e00-\\u9fa5]+)");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    File dataFolder = null;
    List<File> allPJ = new ArrayList<>();
    List<File> allCA = new ArrayList<>();
    List<File> allOthers = new ArrayList<>();
    Map<String, Integer> nameId = new HashMap<>();
    int year, month;

    CaRepository caRepo;
    PjSumRepository pjSumRepo;

    FormulaEvaluator evaluator = null;

    public CurvesParser(PjSumRepository pjSumRepo, CaRepository caRepo, int year, int month, String folder) {
        this.pjSumRepo = pjSumRepo;
        this.caRepo = caRepo;
        String home = System.getProperty("user.home");
        logger.info("user.home: "+home);
        home += File.separator + folder.replace("-", File.separator);
        logger.info("folder: "+home);
        dataFolder = new File(home);
//        sortFiles(dataFolder);
        buildClubNameIdMap();
        this.year = year;
        this.month = month - 1;
    }

    public void processAll() {
        Collection<File> allFiles = FileUtils.listFiles(dataFolder, null, true);
        int clubId = -1;
        for (File f : allFiles) {
            Workbook wb = null;
            try {
                wb = WorkbookFactory.create(f);

                clubId = getIdByName(parseChinese(f.getName()));
            } catch (Exception e) {
                logger.error(">>> OPEN FILE ERROR: "+f);
                allOthers.add(f);
                continue;
            }

            logger.info("--- open file ---"+f);

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sh = wb.getSheetAt(i);
                String sheetName = sh.getSheetName();
                try {
                    int rows = sh.getLastRowNum();
                    int columns = sh.getRow(0).getPhysicalNumberOfCells();
                    if (rows > 100 && columns < 26) {
                        allCA.add(f);
//                        logger.info("--- processing CA ---"+sheetName);
                        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                        new CaDataHandler(this).processCA(sh, evaluator, clubId);
                    } else if (rows < 80 && columns > 30) {
                        allPJ.add(f);
//                        logger.info("=== processing PJ ==="+sheetName);
                        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                        new PjDataHandler(this).processPJ(sh, evaluator, clubId);
                    } else {
                        allOthers.add(f);
                    }
                } catch (Exception e) {
                    logger.error(">>> ERROR <<< file: " + f + ", sheet: " + sheetName, e);
                }
            }
        }
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
}
