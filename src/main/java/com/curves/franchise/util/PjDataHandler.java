package com.curves.franchise.util;

import com.curves.franchise.domain.Pj;
import com.curves.franchise.domain.PjSum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PjDataHandler {
    private Logger logger = LoggerFactory.getLogger(PjDataHandler.class);

    private CurvesParser cp;

    private FormulaEvaluator evaluator = null;

    public PjDataHandler(CurvesParser cp){
        this.cp = cp;
    }

    public void processPJ(Sheet sh, FormulaEvaluator evaluator, int clubId) {
        this.evaluator = evaluator;

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sh.getRow(15).getCell(0).getDateCellValue());
            if (c.get(Calendar.YEAR) != cp.year || c.get(Calendar.MONTH) != cp.month) {
                return;
            }
        } catch (Exception e) {
            return;
        }

        if (clubId == -1) {
            try {
                clubId = (int)sh.getRow(1).getCell(0).getNumericCellValue();
            } catch (Exception e) {
                logger.error(">>> CANNOT GET CLUB-ID FROM PJ");
                return;
            }
        }

        int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int sumRowIdx = lastDayOfMonth + 5;
        for (Cell cTest = sh.getRow(sumRowIdx).getCell(3); cTest != null;cTest = sh.getRow(sumRowIdx).getCell(3)) {
            if (sumRowIdx > 45 || Cell.CELL_TYPE_FORMULA == cTest.getCellType()) {
                break;
            }
            sumRowIdx++;
        }

        PjSum pjSum = new PjSum();
        pjSum.setLastModified(new Date());
        pjSum.setClubId(clubId);
        pjSum.setYear(cp.year);
        pjSum.setMonth(cp.month);
        pjSum.setEnrolled(cp.getCellIntValue(sh, 1, 3));
        pjSum.setLeaves(cp.getCellIntValue(sh, 1, 4));
        pjSum.setValids(cp.getCellIntValue(sh, 1, 5));
        pjSum.setExits(cp.getCellIntValue(sh, 1, 6));

        setupPjSum(sh, sumRowIdx, pjSum);

        List<Pj> pjs = new ArrayList<>(31);
        pjSum.setPjSet(pjs);

        for (int j = 4; j < sumRowIdx; j++) {
            Date pjDate = null;
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

            setupPj(sh, j, pj);
        }

        PjSum pjSumX = cp.pjSumRepo.findByClubIdAndYearAndMonth(pjSum.getClubId(), pjSum.getYear(), pjSum.getMonth());
        if (pjSumX != null) {
            pjSum.setId(pjSumX.getId());
        }
        cp.pjSumRepo.save(pjSum);

        logger.info("### Saved PJ : "+sh.getSheetName() + " ###");
    }

    private void setupPjSum(Sheet sh, int sumRowIdx, PjSum pjSum) {
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(sumRowIdx).getCell(3));pjSum.setWorkingDays((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {pjSum.setMaxWorkOuts(cp.getCellIntValue(sh, sumRowIdx, 4));} catch (Exception e) {}
        try {pjSum.setNewSalesRevenue(cp.getCellIntValue(sh, sumRowIdx, 5));} catch (Exception e) {}
        try {pjSum.setDuesDraftsRevenue(cp.getCellIntValue(sh, sumRowIdx, 6));} catch (Exception e) {}
        try {pjSum.setProductsRevenue(cp.getCellIntValue(sh, sumRowIdx, 7));} catch (Exception e) {}
        try {pjSum.setRevenue(cp.getCellIntValue(sh, sumRowIdx, 8));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(sumRowIdx).getCell(9));pjSum.setExitRatio((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(sumRowIdx).getCell(10));pjSum.setLeaveRatio((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {pjSum.setNewSales(cp.getCellIntValue(sh, sumRowIdx, 11));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(sumRowIdx).getCell(12));pjSum.setSalesRatio((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {pjSum.setBrOwnRef(cp.getCellIntValue(sh, sumRowIdx, 13));} catch (Exception e) {}
        try {pjSum.setBrOthersRef(cp.getCellIntValue(sh, sumRowIdx, 14));} catch (Exception e) {}
        try {pjSum.setBrandedTv(cp.getCellIntValue(sh, sumRowIdx, 15));} catch (Exception e) {}
        try {pjSum.setBrandedInternet(cp.getCellIntValue(sh, sumRowIdx, 16));} catch (Exception e) {}
        try {pjSum.setBrandedSign(cp.getCellIntValue(sh, sumRowIdx, 17));} catch (Exception e) {}
        try {pjSum.setBrandedMate(cp.getCellIntValue(sh, sumRowIdx, 18));} catch (Exception e) {}
        try {pjSum.setBrandedOthers(cp.getCellIntValue(sh, sumRowIdx, 19));} catch (Exception e) {}
        try {pjSum.setAgpInDirectMail(cp.getCellIntValue(sh, sumRowIdx, 20));} catch (Exception e) {}
        try {pjSum.setAgpInMailFlyer(cp.getCellIntValue(sh, sumRowIdx, 21));} catch (Exception e) {}
        try {pjSum.setAgpInHandFlyer(cp.getCellIntValue(sh, sumRowIdx, 22));} catch (Exception e) {}
        try {pjSum.setAgpInCp(cp.getCellIntValue(sh, sumRowIdx, 23));} catch (Exception e) {}
        try {pjSum.setAgpOutApoOut(cp.getCellIntValue(sh, sumRowIdx, 24));} catch (Exception e) {}
        try {pjSum.setAgpOutApoIn(cp.getCellIntValue(sh, sumRowIdx, 25));} catch (Exception e) {}
        try {pjSum.setAgpOutApoBlog(cp.getCellIntValue(sh, sumRowIdx, 26));} catch (Exception e) {}
        try {pjSum.setAgpOutApoBag(cp.getCellIntValue(sh, sumRowIdx, 27));} catch (Exception e) {}
        try {pjSum.setFaSum(cp.getCellIntValue(sh, sumRowIdx, 28));} catch (Exception e) {}
        try {pjSum.setEnrollAch(cp.getCellIntValue(sh, sumRowIdx, 29));} catch (Exception e) {}
        try {pjSum.setEnrollMonthly(cp.getCellIntValue(sh, sumRowIdx, 30));} catch (Exception e) {}
        try {pjSum.setEnrollAllPrepay(cp.getCellIntValue(sh, sumRowIdx, 31));} catch (Exception e) {}
        try {pjSum.setIncomingCalls(cp.getCellIntValue(sh, sumRowIdx, 32));} catch (Exception e) {}
        try {pjSum.setIncomingApo(cp.getCellIntValue(sh, sumRowIdx, 33));} catch (Exception e) {}
        try {pjSum.setOutgoingCalls(cp.getCellIntValue(sh, sumRowIdx, 34));} catch (Exception e) {}
        try {pjSum.setOutgoingApo(cp.getCellIntValue(sh, sumRowIdx, 35));} catch (Exception e) {}
    }

    private void setupPj(Sheet sh, int j, Pj pj) {
        try {pj.setWorkingDays((float)sh.getRow(j).getCell(3).getNumericCellValue());} catch (Exception e) {}
        try {pj.setWorkOuts(cp.getCellIntValue(sh, j, 4));} catch (Exception e) {}
        try {pj.setNewSalesRevenue(cp.getCellIntValue(sh, j, 5));} catch (Exception e) {}
        try {pj.setBrOwnRef(cp.getCellIntValue(sh, j, 13));} catch (Exception e) {}
        try {pj.setBrandedNewspaper(cp.getCellIntValue(sh, j, 14));} catch (Exception e) {}
        try {pj.setBrandedTv(cp.getCellIntValue(sh, j, 15));} catch (Exception e) {}
        try {pj.setBrandedInternet(cp.getCellIntValue(sh, j, 16));} catch (Exception e) {}
        try {pj.setBrandedSign(cp.getCellIntValue(sh, j, 17));} catch (Exception e) {}
        try {pj.setBrandedMate(cp.getCellIntValue(sh, j, 18));} catch (Exception e) {}
        try {pj.setBrandedOthers(cp.getCellIntValue(sh, j, 19));} catch (Exception e) {}
        try {pj.setAgpInDirectMail(cp.getCellIntValue(sh, j, 20));} catch (Exception e) {}
        try {pj.setAgpInMailFlyer(cp.getCellIntValue(sh, j, 21));} catch (Exception e) {}
        try {pj.setAgpInHandFlyer(cp.getCellIntValue(sh, j, 22));} catch (Exception e) {}
        try {pj.setAgpInCp(cp.getCellIntValue(sh, j, 23));} catch (Exception e) {}
        try {pj.setAgpOutApoOut(cp.getCellIntValue(sh, j, 24));} catch (Exception e) {}
        try {pj.setAgpOutApoIn(cp.getCellIntValue(sh, j, 25));} catch (Exception e) {}
        try {pj.setAgpOutApoBlog(cp.getCellIntValue(sh, j, 26));} catch (Exception e) {}
        try {pj.setAgpOutApoBag(cp.getCellIntValue(sh, j, 27));} catch (Exception e) {}
        try {pj.setFa(cp.getCellIntValue(sh, j, 28));} catch (Exception e) {}
        try {pj.setEnrollAch(cp.getCellIntValue(sh, j, 29));} catch (Exception e) {}
        try {pj.setEnrollMonthly(cp.getCellIntValue(sh, j, 30));} catch (Exception e) {}
        try {pj.setEnrollAllPrepay(cp.getCellIntValue(sh, j, 31));} catch (Exception e) {}
        try {pj.setExits(cp.getCellIntValue(sh, j, 32));} catch (Exception e) {}
        try {pj.setIncomingCalls(cp.getCellIntValue(sh, j, 33));} catch (Exception e) {}
        try {pj.setIncomingApo(cp.getCellIntValue(sh, j, 34));} catch (Exception e) {}
        try {pj.setOutgoingCalls(cp.getCellIntValue(sh, j, 35));} catch (Exception e) {}
        try {pj.setOutgoingApo(cp.getCellIntValue(sh, j, 36));} catch (Exception e) {}
        try {pj.setProductsRevenue(cp.getCellIntValue(sh, j, 37));} catch (Exception e) {}
        try {pj.setDuesDraftsRevenue(cp.getCellIntValue(sh, j, 38));} catch (Exception e) {}
    }
}
