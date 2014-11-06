package com.curves.franchise.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurvesParser {
    protected Logger logger = LoggerFactory.getLogger(CurvesParser.class);

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    protected File baseDir = null;
    protected List<File> allPJ = new ArrayList<File>();
    protected List<File> allCA = new ArrayList<File>();
    protected List<File> allOthers = new ArrayList<File>();

    protected FormulaEvaluator evaluator = null;

    public CurvesParser() {}

    protected int getCellIntValue(Sheet sh, int row, int col) {
        int value = 0;
        try {
            value = (int)sh.getRow(row).getCell(col).getNumericCellValue();
        } catch (Exception e) {
        }
        return value;
    }

    protected String getClubNameFromFileName(File f) {
        String fname = f.getName();
        String clubName = "";
        Pattern pat = Pattern.compile("([\\u4e00-\\u9fa5]+)");
        Matcher mat = pat.matcher(fname);
        while (mat.find()) {
            clubName = mat.group(0);
        }
        return clubName;
    }

    protected String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
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

    public Map<String, String> getClubNameIdMap() throws Exception {
        File f = new File(baseDir, "clubInfo.xlsx");
        Map<String, String> nameId = new HashMap<String, String>();
        try {
            Workbook wb = WorkbookFactory.create(f);
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sh = wb.getSheetAt(i);
                for (Row row : sh) {
                    if (row.getRowNum() == 0 || row.getRowNum() > 88) {
                        continue;
                    }
                    String clubId = getCellValue(row.getCell(4));
                    String clubName = getCellValue(row.getCell(1));
                    clubId = clubId.substring(0, clubId.length()-2);
                    nameId.put(clubName, clubId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameId;
    }

    public void processUser() throws Exception {
        File f = new File(baseDir, "clubInfo.xlsx");
        Connection conn = getJdbcConnection();
        PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO users (username, password, enabled) values (?, ?, ?)");
        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO authorities (username, authority) values (?, ?)");
        PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO clubs (id, name, owner) values (?, ?, ?)");
        try {
            Workbook wb = WorkbookFactory.create(f);
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sh = wb.getSheetAt(i);
                for (Row row : sh) {
                    if (row.getRowNum() == 0 || row.getRowNum() > 88) {
                        continue;
                    }
                    String clubId = getCellValue(row.getCell(4));
                    if ("TBA".equals(clubId)) {
                        continue;
                    }
                    String clubName = getCellValue(row.getCell(1));
                    String owner = getCellValue(row.getCell(2));
                    clubId = clubId.substring(0, clubId.length()-2);
                    stmt1.setString(1, clubId);
                    stmt1.setString(2, clubId);
                    stmt1.setBoolean(3, true);
                    stmt1.addBatch();
                    stmt2.setString(1, clubId);
                    stmt2.setString(2, "ROLE_USER");
                    stmt2.addBatch();
                    stmt3.setInt(1, (int)Double.parseDouble(clubId));
                    stmt3.setString(2, clubName);
                    stmt3.setString(3, owner);
                    stmt3.addBatch();
                }
            }
            stmt1.executeBatch();
            stmt2.executeBatch();
            stmt3.executeBatch();
            stmt1.close();
            stmt2.close();
            stmt3.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Connection getJdbcConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost/curves?useUnicode=true&characterEncoding=UTF-8", "curves", "curves");
    }

    public void sortFiles(String baseFolder) {
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
