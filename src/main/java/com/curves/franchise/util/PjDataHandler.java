package com.curves.franchise.util;

import com.curves.franchise.domain.Pj;
import com.curves.franchise.domain.PjSum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class PjDataHandler {
    private final Logger logger = LoggerFactory.getLogger(PjDataHandler.class);

    private final CurvesParser cp;
    private int version = -1;
    private FormulaEvaluator evaluator = null;

    public PjDataHandler(CurvesParser cp){
        this.cp = cp;
    }

    public boolean processPJ(Sheet sh, FormulaEvaluator evaluator, int clubId) {
        this.evaluator = evaluator;
        setVersion(sh);
        if (version == -1) {
            logger.error("Cannot determine PJ version !!!");
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(sh.getRow(15).getCell(0).getDateCellValue());
        int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int sumRowIdx = 30;
        for (; sumRowIdx < 45; sumRowIdx++) {
            Row row = sh.getRow(sumRowIdx);
            if (row != null) {
                Cell cTest = row.getCell(25);
                if (cTest != null && Cell.CELL_TYPE_FORMULA == cTest.getCellType()) {
                    break;
                }
            }
        }
        if (sumRowIdx == 45) {
            logger.error("summary line not found!!!");
            return false;
        }

        PjSum pjSum = new PjSum();
        pjSum.setLastModified(new Date());
        pjSum.setClubId(clubId);
        pjSum.setYear(cp.year);
        pjSum.setMonth(cp.month);
        switch (version) {
            case 2012:
                setupPjSum2012(sh, sumRowIdx, pjSum);
                break;
            case 2015:
                setupPjSum2015(sh, sumRowIdx, pjSum);
                break;
        }

        PjSum pjSumX = cp.pjSumRepo.findByClubIdAndYearAndMonth(pjSum.getClubId(), pjSum.getYear(), pjSum.getMonth());
        if (pjSumX != null) {
            pjSum.setId(pjSumX.getId());
        }
        cp.pjSumRepo.save(pjSum);

        logger.info("<== PJ Saved. clubId: "+pjSum.getClubId()+", year: "+pjSum.getYear()+", month: "+pjSum.getMonth()
                +", version: "+version+", sum row idx: "+sumRowIdx+", lastDayOfMonth: "+lastDayOfMonth);
        logger.info("");

        return true;
    }

    private void setVersion(Sheet sh) {
        try {
            if ("CP".equals(sh.getRow(3).getCell(23).getStringCellValue())) {
                version = 2012;
            }
        } catch (Exception e) {
        }
        try {
            if ("CP".equals(sh.getRow(5).getCell(25).getStringCellValue())) {
                version = 2015;
            }
        } catch (Exception e) {
        }
    }

    private void setupPjSum2015(Sheet sh, int sumRowIdx, PjSum pjSum) {
        pjSum.setNewSales((int)cp.getNumberValue(sh, 1, 4));
        pjSum.setExits((int)cp.getNumberValue(sh, 1, 5));
        pjSum.setIncrement((int)cp.getNumberValue(sh, 1, 8));
        pjSum.setRevenue((int)cp.getNumberValue(sh, 3, 0));
        pjSum.setEnrolled((int)cp.getNumberValue(sh, 3, 1));
        pjSum.setLeaves((int)cp.getNumberValue(sh, 3, 2));
        pjSum.setValids((int)cp.getNumberValue(sh, 3, 3));
        pjSum.setSalesRatio((float) cp.getNumberValue(sh, 3, 4));
        pjSum.setExitRatio((float) cp.getNumberValue(sh, 3, 5));
        pjSum.setLeaveRatio((float) cp.getNumberValue(sh, 3, 6));
        pjSum.setWorkingDays((int) cp.getNumberValue(sh, sumRowIdx+1, 3));
        pjSum.setMaxWorkOuts((int) cp.getNumberValue(sh, sumRowIdx+1, 4));
        pjSum.setNewSalesRevenue((int) cp.getNumberValue(sh, sumRowIdx, 5));
        pjSum.setDuesDraftsRevenue((int) cp.getNumberValue(sh, sumRowIdx, 6));
        pjSum.setProductsRevenue((int) cp.getNumberValue(sh, sumRowIdx, 7));
        pjSum.setWheyProteinRevenue((int) cp.getNumberValue(sh, sumRowIdx, 8));
        pjSum.setOtherRevenue((int) cp.getNumberValue(sh, sumRowIdx, 9));
        pjSum.setIncomingCalls((int) cp.getNumberValue(sh, sumRowIdx, 10));
        pjSum.setIncomingApo((int) cp.getNumberValue(sh, sumRowIdx, 11));
        pjSum.setOutgoingCalls((int) cp.getNumberValue(sh, sumRowIdx, 12));
        pjSum.setOutgoingApo((int) cp.getNumberValue(sh, sumRowIdx, 13));
        pjSum.setBrOwnRef((int) cp.getNumberValue(sh, sumRowIdx, 14));
        pjSum.setBrOthersRef((int) cp.getNumberValue(sh, sumRowIdx, 15));
        pjSum.setBrandedNewspaper((int) cp.getNumberValue(sh, sumRowIdx, 16));
        pjSum.setBrandedTv((int) cp.getNumberValue(sh, sumRowIdx, 17));
        pjSum.setBrandedInternet((int) cp.getNumberValue(sh, sumRowIdx, 18));
        pjSum.setBrandedSign((int) cp.getNumberValue(sh, sumRowIdx, 19));
        pjSum.setBrandedMate((int) cp.getNumberValue(sh, sumRowIdx, 20));
        pjSum.setBrandedOthers((int) cp.getNumberValue(sh, sumRowIdx, 21));
        pjSum.setAgpInDirectMail((int) cp.getNumberValue(sh, sumRowIdx, 22));
        pjSum.setAgpInMailFlyer((int) cp.getNumberValue(sh, sumRowIdx, 23));
        pjSum.setAgpInHandFlyer((int) cp.getNumberValue(sh, sumRowIdx, 24));
        pjSum.setAgpInCp((int) cp.getNumberValue(sh, sumRowIdx, 25));
        pjSum.setAgpOutApoOut((int) cp.getNumberValue(sh, sumRowIdx, 26));
        pjSum.setAgpOutApoIn((int) cp.getNumberValue(sh, sumRowIdx, 27));
        pjSum.setAgpOutApoBlog((int) cp.getNumberValue(sh, sumRowIdx, 28));
        pjSum.setAgpOutApoBag((int) cp.getNumberValue(sh, sumRowIdx, 29));
        pjSum.setFaSum((int) cp.getNumberValue(sh, sumRowIdx, 30));
        pjSum.setEnrollAch((int) cp.getNumberValue(sh, sumRowIdx, 31));
        pjSum.setEnrollMonthly((int) cp.getNumberValue(sh, sumRowIdx, 32));
        pjSum.setEnrollAllPrepay((int) cp.getNumberValue(sh, sumRowIdx, 33));

        List<Pj> pjs = new ArrayList<>(31);
        pjSum.setPjSet(pjs);

        for (int j = 6; j < sumRowIdx; j++) {
            Date pjDate;
            try {
                pjDate = sh.getRow(j).getCell(0).getDateCellValue();
                Calendar c1 = Calendar.getInstance();
                c1.setTime(pjDate);
                if (c1.get(Calendar.YEAR) != cp.year && c1.get(Calendar.MONTH) != cp.month) {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }

            Pj pj = new Pj();
            pj.setLastModified(new Date());
            pjs.add(pj);

            pj.setPjDate(pjDate);

            setupPj2015(sh, j, pj);
        }
    }

    private void setupPjSum2012(Sheet sh, int sumRowIdx, PjSum pjSum) {
        pjSum.setEnrolled((int)cp.getNumberValue(sh, 1, 3));
        pjSum.setLeaves((int)cp.getNumberValue(sh, 1, 4));
        pjSum.setValids((int)cp.getNumberValue(sh, 1, 5));
        pjSum.setExits((int)cp.getNumberValue(sh, 1, 6));
        pjSum.setWorkingDays((float)cp.getNumberValue(sh, sumRowIdx, 3));
        pjSum.setMaxWorkOuts((int)cp.getNumberValue(sh, sumRowIdx, 4));
        pjSum.setNewSalesRevenue((int)cp.getNumberValue(sh, sumRowIdx, 5));
        pjSum.setDuesDraftsRevenue((int)cp.getNumberValue(sh, sumRowIdx, 6));
        pjSum.setProductsRevenue((int)cp.getNumberValue(sh, sumRowIdx, 7));
        pjSum.setRevenue((int)cp.getNumberValue(sh, sumRowIdx, 8));
        pjSum.setExitRatio((float)cp.getNumberValue(sh, sumRowIdx, 9));
        pjSum.setLeaveRatio((float)cp.getNumberValue(sh, sumRowIdx, 10));
        pjSum.setNewSales((int)cp.getNumberValue(sh, sumRowIdx, 11));
        pjSum.setSalesRatio((float)cp.getNumberValue(sh, sumRowIdx, 12));
        pjSum.setBrOwnRef((int)cp.getNumberValue(sh, sumRowIdx, 13));
        pjSum.setBrOthersRef((int)cp.getNumberValue(sh, sumRowIdx, 14));
        pjSum.setBrandedTv((int)cp.getNumberValue(sh, sumRowIdx, 15));
        pjSum.setBrandedInternet((int)cp.getNumberValue(sh, sumRowIdx, 16));
        pjSum.setBrandedSign((int)cp.getNumberValue(sh, sumRowIdx, 17));
        pjSum.setBrandedMate((int)cp.getNumberValue(sh, sumRowIdx, 18));
        pjSum.setBrandedOthers((int)cp.getNumberValue(sh, sumRowIdx, 19));
        pjSum.setAgpInDirectMail((int)cp.getNumberValue(sh, sumRowIdx, 20));
        pjSum.setAgpInMailFlyer((int)cp.getNumberValue(sh, sumRowIdx, 21));
        pjSum.setAgpInHandFlyer((int)cp.getNumberValue(sh, sumRowIdx, 22));
        pjSum.setAgpInCp((int)cp.getNumberValue(sh, sumRowIdx, 23));
        pjSum.setAgpOutApoOut((int)cp.getNumberValue(sh, sumRowIdx, 24));
        pjSum.setAgpOutApoIn((int)cp.getNumberValue(sh, sumRowIdx, 25));
        pjSum.setAgpOutApoBlog((int)cp.getNumberValue(sh, sumRowIdx, 26));
        pjSum.setAgpOutApoBag((int)cp.getNumberValue(sh, sumRowIdx, 27));
        pjSum.setFaSum((int)cp.getNumberValue(sh, sumRowIdx, 28));
        pjSum.setEnrollAch((int)cp.getNumberValue(sh, sumRowIdx, 29));
        pjSum.setEnrollMonthly((int)cp.getNumberValue(sh, sumRowIdx, 30));
        pjSum.setEnrollAllPrepay((int)cp.getNumberValue(sh, sumRowIdx, 31));
        pjSum.setIncomingCalls((int)cp.getNumberValue(sh, sumRowIdx, 32));
        pjSum.setIncomingApo((int)cp.getNumberValue(sh, sumRowIdx, 33));
        pjSum.setOutgoingCalls((int)cp.getNumberValue(sh, sumRowIdx, 34));
        pjSum.setOutgoingApo((int)cp.getNumberValue(sh, sumRowIdx, 35));

        List<Pj> pjs = new ArrayList<>(31);
        pjSum.setPjSet(pjs);

        for (int j = 4; j < sumRowIdx; j++) {
            Date pjDate;
            try {
                pjDate = sh.getRow(j).getCell(0).getDateCellValue();
                Calendar c1 = Calendar.getInstance();
                c1.setTime(pjDate);
                if (c1.get(Calendar.YEAR) != cp.year && c1.get(Calendar.MONTH) != cp.month) {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }

            Pj pj = new Pj();
            pj.setLastModified(new Date());
            pjs.add(pj);

            pj.setPjDate(pjDate);

            setupPj2012(sh, j, pj);
        }
    }

    private void setupPj2012(Sheet sh, int j, Pj pj) {
        pj.setWorkingDays((float)cp.getNumberValue(sh, j, 3));
        pj.setWorkOuts((int)cp.getNumberValue(sh, j, 4));
        pj.setNewSalesRevenue((int)cp.getNumberValue(sh, j, 5));
        pj.setBrOwnRef((int)cp.getNumberValue(sh, j, 13));
        pj.setBrandedNewspaper((int)cp.getNumberValue(sh, j, 14));
        pj.setBrandedTv((int)cp.getNumberValue(sh, j, 15));
        pj.setBrandedInternet((int)cp.getNumberValue(sh, j, 16));
        pj.setBrandedSign((int)cp.getNumberValue(sh, j, 17));
        pj.setBrandedMate((int)cp.getNumberValue(sh, j, 18));
        pj.setBrandedOthers((int)cp.getNumberValue(sh, j, 19));
        pj.setAgpInDirectMail((int)cp.getNumberValue(sh, j, 20));
        pj.setAgpInMailFlyer((int)cp.getNumberValue(sh, j, 21));
        pj.setAgpInHandFlyer((int)cp.getNumberValue(sh, j, 22));
        pj.setAgpInCp((int)cp.getNumberValue(sh, j, 23));
        pj.setAgpOutApoOut((int)cp.getNumberValue(sh, j, 24));
        pj.setAgpOutApoIn((int)cp.getNumberValue(sh, j, 25));
        pj.setAgpOutApoBlog((int)cp.getNumberValue(sh, j, 26));
        pj.setAgpOutApoBag((int)cp.getNumberValue(sh, j, 27));
        pj.setFa((int)cp.getNumberValue(sh, j, 28));
        pj.setEnrollAch((int)cp.getNumberValue(sh, j, 29));
        pj.setEnrollMonthly((int)cp.getNumberValue(sh, j, 30));
        pj.setEnrollAllPrepay((int)cp.getNumberValue(sh, j, 31));
        pj.setExits((int)cp.getNumberValue(sh, j, 32));
        pj.setIncomingCalls((int)cp.getNumberValue(sh, j, 33));
        pj.setIncomingApo((int)cp.getNumberValue(sh, j, 34));
        pj.setOutgoingCalls((int)cp.getNumberValue(sh, j, 35));
        pj.setOutgoingApo((int)cp.getNumberValue(sh, j, 36));
        pj.setProductsRevenue((int)cp.getNumberValue(sh, j, 37));
        pj.setDuesDraftsRevenue((int)cp.getNumberValue(sh, j, 38));
    }

    private void setupPj2015(Sheet sh, int j, Pj pj) {
        pj.setWorkingDays((int) cp.getNumberValue(sh, j, 3));
        pj.setWorkOuts((int) cp.getNumberValue(sh, j, 4));
        pj.setNewSalesRevenue((int) cp.getNumberValue(sh, j, 5));
        pj.setDuesDraftsRevenue((int) cp.getNumberValue(sh, j, 6));
        pj.setProductsRevenue((int) cp.getNumberValue(sh, j, 7));
        pj.setWheyProteinRevenue((int) cp.getNumberValue(sh, j, 8));
        pj.setOtherRevenue((int) cp.getNumberValue(sh, j, 9));
        pj.setIncomingCalls((int) cp.getNumberValue(sh, j, 10));
        pj.setIncomingApo((int) cp.getNumberValue(sh, j, 11));
        pj.setOutgoingCalls((int) cp.getNumberValue(sh, j, 12));
        pj.setOutgoingApo((int) cp.getNumberValue(sh, j, 13));
        pj.setBrOwnRef((int) cp.getNumberValue(sh, j, 14));
        pj.setBrOthersRef((int) cp.getNumberValue(sh, j, 15));
        pj.setBrandedNewspaper((int) cp.getNumberValue(sh, j, 16));
        pj.setBrandedTv((int) cp.getNumberValue(sh, j, 17));
        pj.setBrandedInternet((int) cp.getNumberValue(sh, j, 18));
        pj.setBrandedSign((int) cp.getNumberValue(sh, j, 19));
        pj.setBrandedMate((int) cp.getNumberValue(sh, j, 20));
        pj.setBrandedOthers((int) cp.getNumberValue(sh, j, 21));
        pj.setAgpInDirectMail((int) cp.getNumberValue(sh, j, 22));
        pj.setAgpInMailFlyer((int) cp.getNumberValue(sh, j, 23));
        pj.setAgpInHandFlyer((int) cp.getNumberValue(sh, j, 24));
        pj.setAgpInCp((int) cp.getNumberValue(sh, j, 25));
        pj.setAgpOutApoOut((int) cp.getNumberValue(sh, j, 26));
        pj.setAgpOutApoIn((int) cp.getNumberValue(sh, j, 27));
        pj.setAgpOutApoBlog((int) cp.getNumberValue(sh, j, 28));
        pj.setAgpOutApoBag((int) cp.getNumberValue(sh, j, 29));
        pj.setFa((int) cp.getNumberValue(sh, j, 30));
        pj.setEnrollAch((int) cp.getNumberValue(sh, j, 31));
        pj.setEnrollMonthly((int) cp.getNumberValue(sh, j, 32));
        pj.setEnrollAllPrepay((int) cp.getNumberValue(sh, j, 33));
    }
}
