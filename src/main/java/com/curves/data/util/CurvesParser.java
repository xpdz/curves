package com.curves.data.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurvesParser {
    private static Logger logger = LoggerFactory.getLogger(CurvesParser.class);

    private static File baseDir = null;
    private static Set<String> allClub = new HashSet<String>();
    private static List<File> allPJ = new ArrayList<File>();
    private static List<File> allCA = new ArrayList<File>();
    private static List<File> allOthers = new ArrayList<File>();

    private static FormulaEvaluator evaluator = null;

    private static void processPJ() throws Exception {
        Map<String, String> nameId = getClubNameIdMap();

        Set<String> yMs = new HashSet<String>(); // clubId:yyyy-MM

        Connection conn = getJdbcConnection();
        PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO curves.pj_sum (club_id,year,month,new_sales,shift_in,shift_out,increment,revenue,enrolled,leaves,valids,sales_ratio,exit_ratio,leave_ratio,working_days,max_work_outs,new_sales_revenue,products_revenue,dues_drafts_revenue,other_revenue,incoming_calls,incoming_apo,outgoing_calls,outgoing_apo,br_own_ref,br_others_ref,branded_newspaper,branded_tv,branded_internet,branded_sign,branded_mate,branded_others,agp_in_direct_mail,agp_in_mail_flyer,agp_in_hand_flyer,agp_in_cp,agp_out_apo_out,agp_out_apo_in,agp_out_apo_blog,agp_out_apo_bag,fa_sum,enroll_ach,enroll_monthly,enroll_all_prepay,exits) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO curves.pj (pj_sum_id,pj_date,working_days,work_outs,new_sales_revenue,products_revenue,dues_drafts_revenue,other_revenue,incoming_calls,incoming_apo,outgoing_calls,outgoing_apo,br_own_ref,br_others_ref,branded_newspaper,branded_tv,branded_internet,branded_sign,branded_mate,branded_others,agp_in_direct_mail,agp_in_mail_flyer,agp_in_hand_flyer,agp_in_cp,agp_out_apo_out,agp_out_apo_in,agp_out_apo_blog,agp_out_apo_bag,fa,enroll_ach,enroll_monthly,enroll_all_prepay,exits) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        PreparedStatement stmt3 = conn.prepareStatement("SELECT MAX(id) FROM pj_sum");

        for (File f : allPJ) {
            Workbook wb = null;
            try {
                wb = WorkbookFactory.create(f);
            } catch (Exception e) {
                logger.error("==!!!== OPEN FILE ERROR ==!!!=="+f);
                continue;
            }

            String clubName = getClubNameFromFileName(f);
            String sId = nameId.get(clubName);
            if (sId == null) {
                for (String name : nameId.keySet()) {
                    if (name.contains(clubName)) {
                        sId = nameId.get(name);
                        break;
                    }
                }
            }

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                conn.setAutoCommit(false);
                stmt1.clearParameters();
                stmt2.clearParameters();

                Sheet sh = wb.getSheetAt(i);
                if (sh.getSheetName().contains("\u6ce8\u610f")) {
                    continue;
                }

                evaluator = wb.getCreationHelper().createFormulaEvaluator();

                try {
                    int iId = 0;
                    if (sId == null) {
                        try {
                            sId = getCellValue(sh.getRow(1).getCell(0));
                            iId = (int)Double.parseDouble(sId);
                            if (iId < 100000 && iId > 999999) {
                                throw new Exception("ID overflow!");
                            }
                        } catch (Exception e) {
                            logger.error(">>>> ERROR ID: " + f + ", sheet: " + sh.getSheetName()+", Exception: "+e);
                            continue;
                        }
                    } else {
                        iId = Integer.parseInt(sId);
                    }

                    int year, month, lastDayOfMonth;
                    try {
                        Date pjDt = sh.getRow(14).getCell(0).getDateCellValue();
                        Calendar c = Calendar.getInstance();
                        c.setTime(pjDt);
                        year = c.get(Calendar.YEAR);
                        month = c.get(Calendar.MONTH);
                        String yM = sId + ":" + year + "-" + (month + 1);
                        if (yMs.contains(yM)) {
                            continue;
                        }
                        yMs.add(yM);
                        lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    } catch (Exception e) {
                        logger.error("@@@@ DATE ERROR: " + f + ", sheet: " + sh.getSheetName()+", id:"+sId, e);
                        continue;
                    }

                    int sumRowIdx = lastDayOfMonth + 5;
                    CellReference ref = new CellReference("AJ"+sumRowIdx);
                    Cell cTest = sh.getRow(ref.getRow()).getCell(ref.getCol());
                    while (sumRowIdx < 50 && Cell.CELL_TYPE_FORMULA != cTest.getCellType()) {
                        sumRowIdx++;
                        ref = new CellReference("AJ"+sumRowIdx);
                        cTest = sh.getRow(ref.getRow()).getCell(ref.getCol());
                    }
                    logger.info("---- ideal idx: " + (lastDayOfMonth + 5) + ", real idx: "+sumRowIdx);

                    stmt1.setInt(1, iId);
                    stmt1.setInt(2, year);
                    stmt1.setInt(3, month);
                    stmt1.setInt(5, 0);
                    stmt1.setInt(6, 0);
                    stmt1.setInt(7, 0);
                    stmt1.setInt(20, 0);
                    stmt1.setInt(26, 0);
                    int enrolled = getCellIntValue(sh, 1, 3);
                    stmt1.setInt(9, enrolled);
                    int leaves = getCellIntValue(sh, 1, 4);
                    stmt1.setInt(10, leaves);
                    int valids = getCellIntValue(sh, 1, 5);
                    stmt1.setInt(11, valids);
                    int exits = getCellIntValue(sh, 1, 6);
                    stmt1.setInt(45, exits);
                    float workingDays = 0;
                    try {
                        workingDays = (float)sh.getRow(sumRowIdx).getCell(3).getNumericCellValue();
                    } catch (Exception e) {
                    }
                    stmt1.setFloat(15, workingDays);
                    int maxWorkOuts = getCellIntValue(sh, sumRowIdx, 4);
                    stmt1.setInt(16, maxWorkOuts);
                    int newSalesRevenue = getCellIntValue(sh, sumRowIdx, 5);
                    stmt1.setInt(17, newSalesRevenue);
                    int duesDraftsRevenue = getCellIntValue(sh, sumRowIdx, 6);
                    stmt1.setInt(19, duesDraftsRevenue);
                    int productsRevenue = getCellIntValue(sh, sumRowIdx, 7);
                    stmt1.setInt(18, productsRevenue);
                    int revenue = getCellIntValue(sh, sumRowIdx, 8);
                    stmt1.setInt(8, revenue);
                    String exitRatio = getCellValue(sh.getRow(sumRowIdx).getCell(9));
                    stmt1.setString(13, exitRatio);
                    String leaveRatio = getCellValue(sh.getRow(sumRowIdx).getCell(10));
                    stmt1.setString(14, leaveRatio);
                    int newSales = getCellIntValue(sh, sumRowIdx, 11);
                    stmt1.setInt(4, newSales);
                    String salesRatio = getCellValue(sh.getRow(sumRowIdx).getCell(12));
                    stmt1.setString(12, salesRatio);
                    int brOwnRef = getCellIntValue(sh, sumRowIdx, 13);
                    stmt1.setInt(25, brOwnRef);
                    int brandedNewspaper = getCellIntValue(sh, sumRowIdx, 14);
                    stmt1.setInt(27, brandedNewspaper);
                    int brandedTv = getCellIntValue(sh, sumRowIdx, 15);
                    stmt1.setInt(28, brandedTv);
                    int brandedInternet = getCellIntValue(sh, sumRowIdx, 16);
                    stmt1.setInt(29, brandedInternet);
                    int brandedSign = getCellIntValue(sh, sumRowIdx, 17);
                    stmt1.setInt(30, brandedSign);
                    int brandedMate = getCellIntValue(sh, sumRowIdx, 18);
                    stmt1.setInt(31, brandedMate);
                    int brandedOthers = getCellIntValue(sh, sumRowIdx, 19);
                    stmt1.setInt(32, brandedOthers);
                    int agpInDirectMail = getCellIntValue(sh, sumRowIdx, 20);
                    stmt1.setInt(33, agpInDirectMail);
                    int agpInMailFlyer = getCellIntValue(sh, sumRowIdx, 21);
                    stmt1.setInt(34, agpInMailFlyer);
                    int agpInHandFlyer = getCellIntValue(sh, sumRowIdx, 22);
                    stmt1.setInt(35, agpInHandFlyer);
                    int agpInCp = getCellIntValue(sh, sumRowIdx, 23);
                    stmt1.setInt(36, agpInCp);
                    int agpOutApoOut = getCellIntValue(sh, sumRowIdx, 24);
                    stmt1.setInt(37, agpOutApoOut);
                    int agpOutApoIn = getCellIntValue(sh, sumRowIdx, 25);
                    stmt1.setInt(38, agpOutApoIn);
                    int agpOutApoBlog = getCellIntValue(sh, sumRowIdx, 26);
                    stmt1.setInt(39, agpOutApoBlog);
                    int agpOutApoBag = getCellIntValue(sh, sumRowIdx, 27);
                    stmt1.setInt(40, agpOutApoBag);
                    int faSum = getCellIntValue(sh, sumRowIdx, 28);
                    stmt1.setInt(41, faSum);
                    int enrollAch = getCellIntValue(sh, sumRowIdx, 29);
                    stmt1.setInt(42, enrollAch);
                    int enrollMonthly = getCellIntValue(sh, sumRowIdx, 30);
                    stmt1.setInt(43, enrollMonthly);
                    int enrollAllPrepay = getCellIntValue(sh, sumRowIdx, 31);
                    stmt1.setInt(44, enrollAllPrepay);
                    int incomingCalls = getCellIntValue(sh, sumRowIdx, 32);
                    stmt1.setInt(21, incomingCalls);
                    int incomingApo = getCellIntValue(sh, sumRowIdx, 33);
                    stmt1.setInt(22, incomingApo);
                    int outgoingCalls = getCellIntValue(sh, sumRowIdx, 34);
                    stmt1.setInt(23, outgoingCalls);
                    int outgoingApo = getCellIntValue(sh, sumRowIdx, 35);
                    stmt1.setInt(24, outgoingApo);

                    stmt1.executeUpdate();
                    ResultSet rs = stmt3.executeQuery();
                    long pj_sum_id = -1;
                    if (rs.first()) {
                        pj_sum_id = rs.getLong(1);
                    }

                    for (int j = 4; j < sumRowIdx; j++) {
                        Date pjDate = null;
                        try {
                            pjDate = sh.getRow(j).getCell(0).getDateCellValue();
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(pjDate);
                            if (c1.get(Calendar.YEAR) != year && c1.get(Calendar.MONTH) != month) {
                                continue;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                        stmt2.setLong(1, pj_sum_id);
                        stmt2.setDate(2, new java.sql.Date(pjDate.getTime()));
                        stmt2.setInt(8, 0);
                        float workingDays1 = 0;
                        try {
                            workingDays1 = (float)sh.getRow(j).getCell(3).getNumericCellValue();
                        } catch (Exception e) {
                        }
                        stmt2.setFloat(3, workingDays1);
                        int workOuts1 = getCellIntValue(sh, j, 4);
                        stmt2.setInt(4, workOuts1);
                        int newSalesRevenue1 = getCellIntValue(sh, j, 5);
                        stmt2.setInt(5, newSalesRevenue1);
                        int brOwnRef1 = getCellIntValue(sh, j, 13);
                        stmt2.setInt(13, brOwnRef1);
                        stmt2.setInt(14, 0);
                        int brandedNewspaper1 = getCellIntValue(sh, j, 14);
                        stmt2.setInt(15, brandedNewspaper1);
                        int brandedTv1 = getCellIntValue(sh, j, 15);
                        stmt2.setInt(16, brandedTv1);
                        int brandedInternet1 = getCellIntValue(sh, j, 16);
                        stmt2.setInt(17, brandedInternet1);
                        int brandedSign1 = getCellIntValue(sh, j, 17);
                        stmt2.setInt(18, brandedSign1);
                        int brandedMate1 = getCellIntValue(sh, j, 18);
                        stmt2.setInt(19, brandedMate1);
                        int brandedOthers1 = getCellIntValue(sh, j, 19);
                        stmt2.setInt(20, brandedOthers1);
                        int agpInDirectMail1 = getCellIntValue(sh, j, 20);
                        stmt2.setInt(21, agpInDirectMail1);
                        int agpInMailFlyer1 = getCellIntValue(sh, j, 21);
                        stmt2.setInt(22, agpInMailFlyer1);
                        int agpInHandFlyer1 = getCellIntValue(sh, j, 22);
                        stmt2.setInt(23, agpInHandFlyer1);
                        int agpInCp1 = getCellIntValue(sh, j, 23);
                        stmt2.setInt(24, agpInCp1);
                        int agpOutApoOut1 = getCellIntValue(sh, j, 24);
                        stmt2.setInt(25, agpOutApoOut1);
                        int agpOutApoIn1 = getCellIntValue(sh, j, 25);
                        stmt2.setInt(26, agpOutApoIn1);
                        int agpOutApoBlog1 = getCellIntValue(sh, j, 26);
                        stmt2.setInt(27, agpOutApoBlog1);
                        int agpOutApoBag1 = getCellIntValue(sh, j, 27);
                        stmt2.setInt(28, agpOutApoBag1);
                        int fa1 = getCellIntValue(sh, j, 28);
                        stmt2.setInt(29, fa1);
                        int enrollAch1 = getCellIntValue(sh, j, 29);
                        stmt2.setInt(30, enrollAch1);
                        int enrollMonthly1 = getCellIntValue(sh, j, 30);
                        stmt2.setInt(31, enrollMonthly1);
                        int enrollAllPrepay1 = getCellIntValue(sh, j, 31);
                        stmt2.setInt(32, enrollAllPrepay1);
                        int exits1 = getCellIntValue(sh, j, 32);
                        stmt2.setInt(33, exits1);
                        int incomingCalls1 = getCellIntValue(sh, j, 33);
                        stmt2.setInt(9, incomingCalls1);
                        int incomingApo1 = getCellIntValue(sh, j, 34);
                        stmt2.setInt(10, incomingApo1);
                        int outgoingCalls1 = getCellIntValue(sh, j, 35);
                        stmt2.setInt(11, outgoingCalls1);
                        int outgoingApo1 = getCellIntValue(sh, j, 36);
                        stmt2.setInt(12, outgoingApo1);
                        int productsRevenue1 = getCellIntValue(sh, j, 37);
                        stmt2.setInt(6, productsRevenue1);
                        int duesDraftsRevenue1 = getCellIntValue(sh, j, 38);
                        stmt2.setInt(7, duesDraftsRevenue1);
                        stmt2.addBatch();
                    }

                    stmt2.executeBatch();

                    conn.commit();
                } catch (Exception e) {
                    conn.rollback();
                    logger.error("==!!!==ERROR==!!=="+f+",sheet:"+sh.getSheetName()+", id:"+sId +", Exception: ", e);
                }
            }
        }
    }

    private static int getCellIntValue(Sheet sh, int row, int col) {
        int value = 0;
        try {
            value = (int)sh.getRow(row).getCell(col).getNumericCellValue();
        } catch (Exception e) {
        }
        return value;
    }

    private static String getClubNameFromFileName(File f) {
        String fname = f.getName();
        String clubName = "";
        Pattern pat = Pattern.compile("([\\u4e00-\\u9fa5]+)");
        Matcher mat = pat.matcher(fname);
        while (mat.find()) {
            clubName = mat.group(0);
        }
        return clubName;
    }

    private static void processCA() throws Exception {
        logger.info("total CA: " + allCA.size());
        for (File f : allCA) {
            logger.info("===processing: " + f);
            Workbook wb = null;
            try {
                wb = WorkbookFactory.create(f);
            } catch (Exception e) {
                continue;
            }
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sh = wb.getSheetAt(i);
                String sheetName = sh.getSheetName();
                if (sheetName.indexOf(".") != -1) {
                    logger.info("---processing sheet[" + sh.getSheetName());
                } else {
                    logger.info("---skip sheet[" + i + "], name: " + sh.getSheetName());
                    continue;
                }
                try {
                    CellReference ref = new CellReference("A2");
                    String s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
                    int clubId = -1;
                    try {
                        clubId = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                    }
                    ref = new CellReference("B2");
                    s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
                    int month = -1;
                    try {
                        month = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                    }
                    ref = new CellReference("C2");
                    s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
                    int year = -1;
                    try {
                        year = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                    }
                    if (month > 12) {
                        int tmp = month;
                        month = year;
                        year = tmp;
                    }
                    ref = new CellReference("D2");
                    s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
                    int enrolled = -1;
                    try {
                        enrolled = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                    }
                    ref = new CellReference("E2");
                    s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
                    int leaves = -1;
                    try {
                        leaves = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                    }
                    ref = new CellReference("F2");
                    s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
                    int valids = -1;
                    try {
                        valids = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                    }
                    ref = new CellReference("G2");
                    s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
                    int exits = -1;
                    try {
                        exits = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                    }

//                    ref = new CellReference("G2");
//                    s = getCellValue(sh.getRow(ref.getRow()).getCell(ref.getCol()));
//                    int exits = -1;
//                    try {
//                        exits = Integer.parseInt(s);
//                    } catch (NumberFormatException e) {
//                    }

//                    allClub.add(clubId);
                    logger.info("GOT ID: " + clubId);
                } catch (Exception e) {
                    logger.info(">>>>sheet[" + i + "], name: " + sh.getSheetName(), e);
                }
            }
        }
        logger.info("total clubs: "+allClub.size());
        for (String s : allClub) {
            logger.info(s);
        }
    }

    private static String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return "" + cell.getDateCellValue();
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

    private static Map<String, String> getClubNameIdMap() throws Exception {
        File f = new File(baseDir, "../clubInfo.xlsx");
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
//                    logger.info(">>> club info - name:"+clubName+",id:"+clubId);
                    nameId.put(clubName, clubId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameId;
    }

    private static void processUser() throws Exception {
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
//                    logger.info("---row:"+row.getRowNum()+"name:"+clubName+",owner:"+owner+",id:"+clubId);
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

    private static Connection getJdbcConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost/curves?useUnicode=true&characterEncoding=UTF-8", "curves", "curves");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            logger.error("Usage: <program> <path-to-legacy_data_dir> <user/PJ/CA>");
            System.exit(1);
        }

        baseDir = new File(args[0]);
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
        if ("user".equalsIgnoreCase(args[1])) {
            processUser();
        } else if ("PJ".equalsIgnoreCase(args[1])) {
            processPJ();
        } else if ("CA".equalsIgnoreCase(args[1])) {
            processCA();
        }
    }
}
