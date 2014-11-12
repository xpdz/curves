package com.curves.franchise.util;

import com.curves.franchise.domain.Ca;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Date;

public class CaDataHandler {
    private Logger logger = LoggerFactory.getLogger(CaDataHandler.class);

    private CurvesParser cp;

    private FormulaEvaluator evaluator = null;

    public CaDataHandler(CurvesParser cp) {
        this.cp = cp;
    }

    public void processCA(Sheet sh, FormulaEvaluator evaluator, int clubId) {
        this.evaluator = evaluator;

        String sheetName = sh.getSheetName();
        sheetName = sheetName.replaceAll("[\\.-]", "");
        if (sheetName.startsWith(cp.year+"") && sheetName.endsWith((cp.month+1)+"")) {
        } else {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(sh.getRow(0).getCell(7).getDateCellValue());
                if (c.get(Calendar.YEAR) != cp.year || c.get(Calendar.MONTH) != cp.month) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
        }

        Ca ca = new Ca();
        ca.setLastModified(new Date());
        ca.setClubId(clubId);
        ca.setCaYear(cp.year);
        ca.setCaMonth(cp.month);

        setupCa(sh, ca);

        Ca cax = cp.caRepo.findByClubIdAndCaYearAndCaMonth(ca.getClubId(), ca.getCaYear(), ca.getCaMonth());
        if (cax != null) {
            BeanUtils.copyProperties(cax, ca);
        }
        cp.caRepo.save(ca);

        logger.info("*** Saved CA : "+sh.getSheetName() + " ***");
    }

    private void setupCa(Sheet sh, Ca ca) {
        try {ca.setGoalsTm((int)sh.getRow(4).getCell(9).getNumericCellValue());} catch (Exception e) {}
        try {ca.setGoalsLastTm((int)sh.getRow(5).getCell(9).getNumericCellValue());} catch (Exception e) {}
        try {ca.setGoalsLastActive((int)sh.getRow(6).getCell(9).getNumericCellValue());} catch (Exception e) {}
        try {ca.setGoalsLastShowRatio((float)sh.getRow(7).getCell(9).getNumericCellValue());} catch (Exception e) {}
        try {ca.setGoalsLastSalesRatio((float)sh.getRow(8).getCell(9).getNumericCellValue());} catch (Exception e) {}
        try {ca.setGoalsExitsRatio((float)sh.getRow(9).getCell(9).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(10).getCell(9));ca.setGoalsNewSales((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(11).getCell(9));ca.setGoalsAppoints((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {
            String content = "";
            for (int i = 4; i < 40; i++) {
                content += cp.getCellValue(sh.getRow(i).getCell(10));
            }
            ca.setThisPlan(content);
        } catch (Exception e) {}

        try {
            String content = "";
            for (int i = 95; i < 115; i++) {
                content += cp.getCellValue(sh.getRow(i).getCell(10));
            }
            ca.setNextPlan(content);
        } catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(13).getCell(3));ca.setSvcTm0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvcTm1((int)sh.getRow(13).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTm2((int)sh.getRow(13).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTm3((int)sh.getRow(13).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTm4((int)sh.getRow(13).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTm5((int)sh.getRow(13).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTm6((int)sh.getRow(13).getCell(9).getNumericCellValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(14).getCell(3));ca.setSvcHold0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvcHold1((int)sh.getRow(14).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcHold2((int)sh.getRow(14).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcHold3((int)sh.getRow(14).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcHold4((int)sh.getRow(14).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcHold5((int)sh.getRow(14).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcHold6((int)sh.getRow(14).getCell(9).getNumericCellValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(15).getCell(3));ca.setSvcActive0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvcActive1((int)sh.getRow(15).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcActive2((int)sh.getRow(15).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcActive3((int)sh.getRow(15).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcActive4((int)sh.getRow(15).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcActive5((int)sh.getRow(15).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcActive6((int)sh.getRow(15).getCell(9).getNumericCellValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(16).getCell(3));ca.setSvcHoldRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(16).getCell(4));ca.setSvcHoldRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(16).getCell(5));ca.setSvcHoldRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(16).getCell(6));ca.setSvcHoldRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(16).getCell(7));ca.setSvcHoldRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(16).getCell(8));ca.setSvcHoldRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(16).getCell(9));ca.setSvcHoldRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(17).getCell(3));ca.setSvcTotalWo0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvcTotalWo1((int)sh.getRow(17).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTotalWo2((int)sh.getRow(17).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTotalWo3((int)sh.getRow(17).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTotalWo4((int)sh.getRow(17).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcTotalWo5((int)sh.getRow(17).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(17).getCell(9));ca.setSvcTotalWo6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(18).getCell(3));ca.setSvcAvgWo0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(18).getCell(4));ca.setSvcAvgWo1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(18).getCell(5));ca.setSvcAvgWo2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(18).getCell(6));ca.setSvcAvgWo3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(18).getCell(7));ca.setSvcAvgWo4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(18).getCell(8));ca.setSvcAvgWo5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(18).getCell(9));ca.setSvcAvgWo6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(19).getCell(3));ca.setSvcMaxWo0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvcMaxWo1((int)sh.getRow(19).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMaxWo2((int)sh.getRow(19).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMaxWo3((int)sh.getRow(19).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMaxWo4((int)sh.getRow(19).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMaxWo5((int)sh.getRow(19).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(19).getCell(9));ca.setSvcMaxWo6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(20).getCell(3));ca.setSvcExits0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvcExits1((int)sh.getRow(20).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcExits2((int)sh.getRow(20).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcExits3((int)sh.getRow(20).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcExits4((int)sh.getRow(20).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcExits5((int)sh.getRow(20).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(20).getCell(9));ca.setSvcExits6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(21).getCell(3));ca.setSvcExitsRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(21).getCell(4));ca.setSvcExitsRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(21).getCell(5));ca.setSvcExitsRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(21).getCell(6));ca.setSvcExitsRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(21).getCell(7));ca.setSvcExitsRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(21).getCell(8));ca.setSvcExitsRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(21).getCell(9));ca.setSvcExitsRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(22).getCell(3));ca.setSvcMeasure0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvcMeasure1((int)sh.getRow(22).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMeasure2((int)sh.getRow(22).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMeasure3((int)sh.getRow(22).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMeasure4((int)sh.getRow(22).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSvcMeasure5((int)sh.getRow(22).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(22).getCell(9));ca.setSvcMeasure6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(23).getCell(3));ca.setSvcMeasureRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(23).getCell(4));ca.setSvcMeasureRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(23).getCell(5));ca.setSvcMeasureRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(23).getCell(6));ca.setSvcMeasureRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(23).getCell(7));ca.setSvcMeasureRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(23).getCell(8));ca.setSvcMeasureRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(23).getCell(9));ca.setSvcMeasureRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(24).getCell(3));ca.setSvc12_0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvc12_5((int)sh.getRow(24).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(24).getCell(9));ca.setSvc12_6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(25).getCell(3));ca.setSvc8to11_0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvc8to11_5((int)sh.getRow(25).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(25).getCell(9));ca.setSvc8to11_6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(26).getCell(3));ca.setSvc4to7_0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvc4to7_5((int)sh.getRow(26).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(26).getCell(9));ca.setSvc4to7_6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(27).getCell(3));ca.setSvc1to3_0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvc1to3_5((int)sh.getRow(27).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(27).getCell(9));ca.setSvc1to3_6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(28).getCell(3));ca.setSvc0_0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSvc0_5((int)sh.getRow(28).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(28).getCell(9));ca.setSvc0_6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(29).getCell(3));ca.setSvc3More0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(29).getCell(4));ca.setSvc3More1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(29).getCell(5));ca.setSvc3More2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(29).getCell(6));ca.setSvc3More3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(29).getCell(7));ca.setSvc3More4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(29).getCell(8));ca.setSvc3More5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(30).getCell(3));ca.setSvcInactive0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(30).getCell(4));ca.setSvcInactive1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(30).getCell(5));ca.setSvcInactive2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(30).getCell(6));ca.setSvcInactive3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(30).getCell(7));ca.setSvcInactive4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(30).getCell(8));ca.setSvcInactive5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(31).getCell(3));ca.setSvcFwoReview0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(31).getCell(4));ca.setSvcFwoReview1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(31).getCell(5));ca.setSvcFwoReview2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(31).getCell(6));ca.setSvcFwoReview3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(31).getCell(7));ca.setSvcFwoReview4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(31).getCell(8));ca.setSvcFwoReview5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(32).getCell(3));ca.setSvcInterview0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(32).getCell(4));ca.setSvcInterview1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(32).getCell(5));ca.setSvcInterview2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(32).getCell(6));ca.setSvcInterview3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(32).getCell(7));ca.setSvcInterview4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(32).getCell(8));ca.setSvcInterview5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(33).getCell(3));ca.setSvcThanks0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(33).getCell(4));ca.setSvcThanks1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(33).getCell(5));ca.setSvcThanks2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(33).getCell(6));ca.setSvcThanks3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(33).getCell(7));ca.setSvcThanks4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(33).getCell(8));ca.setSvcThanks5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(34).getCell(3));ca.setSvc3C0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(34).getCell(4));ca.setSvc3C1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(34).getCell(5));ca.setSvc3C2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(34).getCell(6));ca.setSvc3C3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(34).getCell(7));ca.setSvc3C4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(34).getCell(8));ca.setSvc3C5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(35).getCell(3));ca.setSvcReward0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(35).getCell(4));ca.setSvcReward1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(35).getCell(5));ca.setSvcReward2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(35).getCell(6));ca.setSvcReward3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(35).getCell(7));ca.setSvcReward4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(35).getCell(8));ca.setSvcReward5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(36).getCell(3));ca.setSvcLoyal0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(36).getCell(4));ca.setSvcLoyal1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(36).getCell(5));ca.setSvcLoyal2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(36).getCell(6));ca.setSvcLoyal3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(36).getCell(7));ca.setSvcLoyal4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(36).getCell(8));ca.setSvcLoyal5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(39).getCell(3));ca.setCmPostFlyer0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmPostFlyer1((int) sh.getRow(39).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyer2((int) sh.getRow(39).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyer3((int) sh.getRow(39).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyer4((int) sh.getRow(39).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyer5((int) sh.getRow(39).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(39).getCell(9));ca.setCmPostFlyer6((int) cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(40).getCell(3));ca.setCmHandFlyerHours0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmHandFlyerHours1((float) sh.getRow(40).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerHours2((float) sh.getRow(40).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerHours3((float) sh.getRow(40).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerHours4((float) sh.getRow(40).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerHours5((float) sh.getRow(40).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(40).getCell(9));ca.setCmHandFlyerHours6((float) cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(41).getCell(3));ca.setCmOutGp0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmOutGp1((int)sh.getRow(41).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGp2((int)sh.getRow(41).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGp3((int)sh.getRow(41).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGp4((int)sh.getRow(41).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGp5((int)sh.getRow(41).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(41).getCell(9));ca.setCmOutGp6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(42).getCell(3));ca.setCmCpBox0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmCpBox1((int)sh.getRow(42).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpBox2((int)sh.getRow(42).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpBox3((int)sh.getRow(42).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpBox4((int)sh.getRow(42).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpBox5((int)sh.getRow(42).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(42).getCell(9));ca.setCmCpBox6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(43).getCell(3));ca.setCmOutGot0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmOutGot1((int)sh.getRow(43).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGot2((int)sh.getRow(43).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGot3((int)sh.getRow(43).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGot4((int)sh.getRow(43).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGot5((int)sh.getRow(43).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(43).getCell(9));ca.setCmOutGot6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(44).getCell(3));ca.setCmInGot0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmInGot1((int)sh.getRow(44).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGot2((int)sh.getRow(44).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGot3((int)sh.getRow(44).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGot4((int)sh.getRow(44).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGot5((int)sh.getRow(44).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(44).getCell(9));ca.setCmInGot6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(45).getCell(3));ca.setCmBlogGot0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmBlogGot1((int)sh.getRow(45).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGot2((int)sh.getRow(45).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGot3((int)sh.getRow(45).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGot4((int)sh.getRow(45).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGot5((int)sh.getRow(45).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(45).getCell(9));ca.setCmBlogGot6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(46).getCell(3));ca.setCmBagGot0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmBagGot1((int)sh.getRow(46).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGot2((int)sh.getRow(46).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGot3((int)sh.getRow(46).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGot4((int)sh.getRow(46).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGot5((int)sh.getRow(46).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(46).getCell(9));ca.setCmBagGot6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(47).getCell(3));ca.setCmTotalGot0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(47).getCell(4));ca.setCmTotalGot1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(47).getCell(5));ca.setCmTotalGot2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(47).getCell(6));ca.setCmTotalGot3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(47).getCell(7));ca.setCmTotalGot4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(47).getCell(8));ca.setCmTotalGot5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(47).getCell(9));ca.setCmTotalGot6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(49).getCell(3));ca.setCmCallIn0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmCallIn1((int)sh.getRow(49).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCallIn2((int)sh.getRow(49).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCallIn3((int)sh.getRow(49).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCallIn4((int)sh.getRow(49).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCallIn5((int)sh.getRow(49).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(49).getCell(9));ca.setCmCallIn6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(50).getCell(3));ca.setCmOutGotCall0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmOutGotCall1((int)sh.getRow(50).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGotCall2((int)sh.getRow(50).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGotCall3((int)sh.getRow(50).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGotCall4((int)sh.getRow(50).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutGotCall5((int)sh.getRow(50).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(50).getCell(9));ca.setCmOutGotCall6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(51).getCell(3));ca.setCmInGotCall0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmInGotCall1((int)sh.getRow(51).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGotCall2((int)sh.getRow(51).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGotCall3((int)sh.getRow(51).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGotCall4((int)sh.getRow(51).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInGotCall5((int)sh.getRow(51).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(51).getCell(9));ca.setCmInGotCall6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(52).getCell(3));ca.setCmBlogGotCall0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmBlogGotCall1((int)sh.getRow(52).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGotCall2((int)sh.getRow(52).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGotCall3((int)sh.getRow(52).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGotCall4((int)sh.getRow(52).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogGotCall5((int)sh.getRow(52).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(52).getCell(9));ca.setCmBlogGotCall6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(53).getCell(3));ca.setCmBagGotCall0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmBagGotCall1((int)sh.getRow(53).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGotCall2((int)sh.getRow(53).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGotCall3((int)sh.getRow(53).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGotCall4((int)sh.getRow(53).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagGotCall5((int)sh.getRow(53).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(53).getCell(9));ca.setCmBagGotCall6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(55).getCell(3));ca.setCmOwnRefs0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmOwnRefs1((int)sh.getRow(55).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOwnRefs2((int)sh.getRow(55).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOwnRefs3((int)sh.getRow(55).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOwnRefs4((int)sh.getRow(55).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOwnRefs5((int)sh.getRow(55).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(55).getCell(9));ca.setCmOwnRefs6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(56).getCell(3));ca.setCmNewspaper0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmNewspaper1((int)sh.getRow(56).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmNewspaper2((int)sh.getRow(56).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmNewspaper3((int)sh.getRow(56).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmNewspaper4((int)sh.getRow(56).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmNewspaper5((int)sh.getRow(56).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(56).getCell(9));ca.setCmNewspaper6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(57).getCell(3));ca.setCmTv0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmTv1((int)sh.getRow(57).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmTv2((int)sh.getRow(57).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmTv3((int)sh.getRow(57).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmTv4((int)sh.getRow(57).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmTv5((int)sh.getRow(57).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(57).getCell(9));ca.setCmTv6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(58).getCell(3));ca.setCmInternet0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmInternet1((int)sh.getRow(58).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInternet2((int)sh.getRow(58).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInternet3((int)sh.getRow(58).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInternet4((int)sh.getRow(58).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInternet5((int)sh.getRow(58).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(58).getCell(9));ca.setCmInternet6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(59).getCell(3));ca.setCmSign0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmSign1((int)sh.getRow(59).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmSign2((int)sh.getRow(59).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmSign3((int)sh.getRow(59).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmSign4((int)sh.getRow(59).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmSign5((int)sh.getRow(59).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(59).getCell(9));ca.setCmSign6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(60).getCell(3));ca.setCmMate0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmMate1((int)sh.getRow(60).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMate2((int)sh.getRow(60).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMate3((int)sh.getRow(60).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMate4((int)sh.getRow(60).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMate5((int)sh.getRow(60).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(60).getCell(9));ca.setCmMate6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(61).getCell(3));ca.setCmOthers0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmOthers1((int)sh.getRow(61).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOthers2((int)sh.getRow(61).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOthers3((int)sh.getRow(61).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOthers4((int)sh.getRow(61).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOthers5((int)sh.getRow(61).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(61).getCell(9));ca.setCmOthers6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(62).getCell(3));ca.setCmMailAgpIn0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmMailAgpIn1((int)sh.getRow(62).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMailAgpIn2((int)sh.getRow(62).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMailAgpIn3((int)sh.getRow(62).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMailAgpIn4((int)sh.getRow(62).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmMailAgpIn5((int)sh.getRow(62).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(62).getCell(9));ca.setCmMailAgpIn6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(63).getCell(3));ca.setCmPostFlyerAgpIn0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmPostFlyerAgpIn1((int)sh.getRow(63).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyerAgpIn2((int)sh.getRow(63).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyerAgpIn3((int)sh.getRow(63).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyerAgpIn4((int)sh.getRow(63).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmPostFlyerAgpIn5((int)sh.getRow(63).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(63).getCell(9));ca.setCmPostFlyerAgpIn6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(64).getCell(3));ca.setCmHandFlyerAgpIn0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmHandFlyerAgpIn1((int)sh.getRow(64).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerAgpIn2((int)sh.getRow(64).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerAgpIn3((int)sh.getRow(64).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerAgpIn4((int)sh.getRow(64).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmHandFlyerAgpIn5((int)sh.getRow(64).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(64).getCell(9));ca.setCmHandFlyerAgpIn6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(65).getCell(3));ca.setCmCpAgpIn0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmCpAgpIn1((int)sh.getRow(65).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpAgpIn2((int)sh.getRow(65).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpAgpIn3((int)sh.getRow(65).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpAgpIn4((int)sh.getRow(65).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmCpAgpIn5((int)sh.getRow(65).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(65).getCell(9));ca.setCmCpAgpIn6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(66).getCell(3));ca.setCmOutAgpOut0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmOutAgpOut1((int)sh.getRow(66).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutAgpOut2((int)sh.getRow(66).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutAgpOut3((int)sh.getRow(66).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutAgpOut4((int)sh.getRow(66).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmOutAgpOut5((int)sh.getRow(66).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(66).getCell(9));ca.setCmOutAgpOut6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(67).getCell(3));ca.setCmInAgpOut0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmInAgpOut1((int)sh.getRow(67).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInAgpOut2((int)sh.getRow(67).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInAgpOut3((int)sh.getRow(67).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInAgpOut4((int)sh.getRow(67).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmInAgpOut5((int)sh.getRow(67).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(67).getCell(9));ca.setCmInAgpOut6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(68).getCell(3));ca.setCmBlogAgpOut0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmBlogAgpOut1((int)sh.getRow(68).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogAgpOut2((int)sh.getRow(68).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogAgpOut3((int)sh.getRow(68).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogAgpOut4((int)sh.getRow(68).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBlogAgpOut5((int)sh.getRow(68).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(68).getCell(9));ca.setCmBlogAgpOut6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(69).getCell(3));ca.setCmBagAgpOut0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmBagAgpOut1((int)sh.getRow(69).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagAgpOut2((int)sh.getRow(69).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagAgpOut3((int)sh.getRow(69).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagAgpOut4((int)sh.getRow(69).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmBagAgpOut5((int)sh.getRow(69).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(69).getCell(9));ca.setCmBagAgpOut6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(70).getCell(3));ca.setCmApoTotal0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(70).getCell(4));ca.setCmApoTotal1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(70).getCell(5));ca.setCmApoTotal2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(70).getCell(6));ca.setCmApoTotal3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(70).getCell(7));ca.setCmApoTotal4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(70).getCell(8));ca.setCmApoTotal5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(70).getCell(9));ca.setCmApoTotal6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(71).getCell(3));ca.setCmInApptRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(71).getCell(4));ca.setCmInApptRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(71).getCell(5));ca.setCmInApptRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(71).getCell(6));ca.setCmInApptRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(71).getCell(7));ca.setCmInApptRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(71).getCell(8));ca.setCmInApptRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(71).getCell(9));ca.setCmInApptRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(72).getCell(3));ca.setCmOutApptRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(72).getCell(4));ca.setCmOutApptRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(72).getCell(5));ca.setCmOutApptRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(72).getCell(6));ca.setCmOutApptRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(72).getCell(7));ca.setCmOutApptRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(72).getCell(8));ca.setCmOutApptRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(72).getCell(9));ca.setCmOutApptRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(73).getCell(3));ca.setCmPostPerApo0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(73).getCell(9));ca.setCmPostPerApo6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(74).getCell(3));ca.setCmHandHoursPerApo0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(74).getCell(9));ca.setCmHandHoursPerApo6((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(75).getCell(3));ca.setCmOutGpHoursPerApo0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(75).getCell(9));ca.setCmOutGpHoursPerApo6((float) cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(76).getCell(3));ca.setCmBrAgpRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(76).getCell(4));ca.setCmBrAgpRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(76).getCell(5));ca.setCmBrAgpRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(76).getCell(6));ca.setCmBrAgpRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(76).getCell(7));ca.setCmBrAgpRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(76).getCell(8));ca.setCmBrAgpRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(76).getCell(9));ca.setCmBrAgpRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(77).getCell(3));ca.setCmFaSum0(value.length() > 0);} catch (Exception e) {}
        try {ca.setCmFaSum1((int)sh.getRow(77).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmFaSum2((int)sh.getRow(77).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmFaSum3((int)sh.getRow(77).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmFaSum4((int)sh.getRow(77).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setCmFaSum5((int)sh.getRow(77).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(77).getCell(9));ca.setCmFaSum6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(78).getCell(3));ca.setCmShowRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(78).getCell(4));ca.setCmShowRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(78).getCell(5));ca.setCmShowRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(78).getCell(6));ca.setCmShowRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(78).getCell(7));ca.setCmShowRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(78).getCell(8));ca.setCmShowRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(78).getCell(9));ca.setCmShowRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(79).getCell(3));ca.setCmTraining0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(79).getCell(4));ca.setCmTraining1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(79).getCell(5));ca.setCmTraining2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(79).getCell(6));ca.setCmTraining3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(79).getCell(7));ca.setCmTraining4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(79).getCell(8));ca.setCmTraining5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(80).getCell(3));ca.setCmGot3_0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(80).getCell(4));ca.setCmGot3_1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(80).getCell(5));ca.setCmGot3_2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(80).getCell(6));ca.setCmGot3_3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(80).getCell(7));ca.setCmGot3_4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(80).getCell(8));ca.setCmGot3_5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(81).getCell(3));ca.setCmInvitation0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(81).getCell(4));ca.setCmInvitation1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(81).getCell(5));ca.setCmInvitation2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(81).getCell(6));ca.setCmInvitation3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(81).getCell(7));ca.setCmInvitation4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(81).getCell(8));ca.setCmInvitation5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(83).getCell(3));ca.setSalesAch0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSalesAch1((int)sh.getRow(83).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAch2((int)sh.getRow(83).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAch3((int)sh.getRow(83).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAch4((int)sh.getRow(83).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAch5((int)sh.getRow(83).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(83).getCell(9));ca.setSalesAch6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(84).getCell(3));ca.setSalesMonthly0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSalesMonthly1((int)sh.getRow(84).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesMonthly2((int)sh.getRow(84).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesMonthly3((int)sh.getRow(84).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesMonthly4((int)sh.getRow(84).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesMonthly5((int)sh.getRow(84).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(84).getCell(9));ca.setSalesMonthly6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(85).getCell(3));ca.setSalesAllPrepay0(value.length() > 0);} catch (Exception e) {}
        try {ca.setSalesAllPrepay1((int)sh.getRow(85).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAllPrepay2((int)sh.getRow(85).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAllPrepay3((int)sh.getRow(85).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAllPrepay4((int)sh.getRow(85).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setSalesAllPrepay5((int)sh.getRow(85).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(85).getCell(9));ca.setSalesAllPrepay6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(86).getCell(3));ca.setSalesTotal0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(86).getCell(4));ca.setSalesTotal1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(86).getCell(5));ca.setSalesTotal2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(86).getCell(6));ca.setSalesTotal3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(86).getCell(7));ca.setSalesTotal4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(86).getCell(8));ca.setSalesTotal5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(86).getCell(9));ca.setSalesTotal6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(87).getCell(3));ca.setSalesRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(87).getCell(4));ca.setSalesRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(87).getCell(5));ca.setSalesRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(87).getCell(6));ca.setSalesRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(87).getCell(7));ca.setSalesRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(87).getCell(8));ca.setSalesRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(87).getCell(9));ca.setSalesRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(88).getCell(3));ca.setSalesAchAppRatio0(value.length() > 0);} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(88).getCell(4));ca.setSalesAchAppRatio1((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(88).getCell(5));ca.setSalesAchAppRatio2((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(88).getCell(6));ca.setSalesAchAppRatio3((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(88).getCell(7));ca.setSalesAchAppRatio4((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(88).getCell(8));ca.setSalesAchAppRatio5((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(88).getCell(9));ca.setSalesAchAppRatio6((float)cellValue.getNumberValue());} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(89).getCell(3));ca.setSalesFaReview0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(89).getCell(4));ca.setSalesFaReview1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(89).getCell(5));ca.setSalesFaReview2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(89).getCell(6));ca.setSalesFaReview3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(89).getCell(7));ca.setSalesFaReview4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(89).getCell(8));ca.setSalesFaReview5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(90).getCell(3));ca.setSalesPriceReview0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(90).getCell(4));ca.setSalesPriceReview1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(90).getCell(5));ca.setSalesPriceReview2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(90).getCell(6));ca.setSalesPriceReview3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(90).getCell(7));ca.setSalesPriceReview4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(90).getCell(8));ca.setSalesPriceReview5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(91).getCell(3));ca.setSalesAck0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(91).getCell(4));ca.setSalesAck1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(91).getCell(5));ca.setSalesAck2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(91).getCell(6));ca.setSalesAck3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(91).getCell(7));ca.setSalesAck4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(91).getCell(8));ca.setSalesAck5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(92).getCell(3));ca.setSalesTarget0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(92).getCell(4));ca.setSalesTarget1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(92).getCell(5));ca.setSalesTarget2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(92).getCell(6));ca.setSalesTarget3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(92).getCell(7));ca.setSalesTarget4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(92).getCell(8));ca.setSalesTarget5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(93).getCell(3));ca.setSalesMotivation0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(93).getCell(4));ca.setSalesMotivation1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(93).getCell(5));ca.setSalesMotivation2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(93).getCell(6));ca.setSalesMotivation3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(93).getCell(7));ca.setSalesMotivation4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(93).getCell(8));ca.setSalesMotivation5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(94).getCell(3));ca.setSalesObstacle0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(94).getCell(4));ca.setSalesObstacle1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(94).getCell(5));ca.setSalesObstacle2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(94).getCell(6));ca.setSalesObstacle3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(94).getCell(7));ca.setSalesObstacle4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(94).getCell(8));ca.setSalesObstacle5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(96).getCell(3));ca.setMgmtMeeting0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(96).getCell(4));ca.setMgmtMeeting1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(96).getCell(5));ca.setMgmtMeeting2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(96).getCell(6));ca.setMgmtMeeting3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(96).getCell(7));ca.setMgmtMeeting4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(96).getCell(8));ca.setMgmtMeeting5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(97).getCell(3));ca.setMgmtCa0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(97).getCell(4));ca.setMgmtCa1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(97).getCell(5));ca.setMgmtCa2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(97).getCell(6));ca.setMgmtCa3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(97).getCell(7));ca.setMgmtCa4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(97).getCell(8));ca.setMgmtCa5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(98).getCell(3));ca.setMgmtGp0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(98).getCell(4));ca.setMgmtGp1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(98).getCell(5));ca.setMgmtGp2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(98).getCell(6));ca.setMgmtGp3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(98).getCell(7));ca.setMgmtGp4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(98).getCell(8));ca.setMgmtGp5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(99).getCell(3));ca.setMgmtLearn0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(99).getCell(4));ca.setMgmtLearn1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(99).getCell(5));ca.setMgmtLearn2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(99).getCell(6));ca.setMgmtLearn3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(99).getCell(7));ca.setMgmtLearn4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(99).getCell(8));ca.setMgmtLearn5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(100).getCell(3));ca.setMgmtSheet0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(100).getCell(4));ca.setMgmtSheet1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(100).getCell(5));ca.setMgmtSheet2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(100).getCell(6));ca.setMgmtSheet3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(100).getCell(7));ca.setMgmtSheet4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(100).getCell(8));ca.setMgmtSheet5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(101).getCell(3));ca.setMgmtPolicy0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(101).getCell(4));ca.setMgmtPolicy1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(101).getCell(5));ca.setMgmtPolicy2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(101).getCell(6));ca.setMgmtPolicy3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(101).getCell(7));ca.setMgmtPolicy4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(101).getCell(8));ca.setMgmtPolicy5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(102).getCell(3));ca.setMgmtCompiantSales0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(102).getCell(4));ca.setMgmtCompiantSales1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(102).getCell(5));ca.setMgmtCompiantSales2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(102).getCell(6));ca.setMgmtCompiantSales3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(102).getCell(7));ca.setMgmtCompiantSales4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(102).getCell(8));ca.setMgmtCompiantSales5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(103).getCell(3));ca.setMgmtCompiantMethod0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(103).getCell(4));ca.setMgmtCompiantMethod1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(103).getCell(5));ca.setMgmtCompiantMethod2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(103).getCell(6));ca.setMgmtCompiantMethod3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(103).getCell(7));ca.setMgmtCompiantMethod4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(103).getCell(8));ca.setMgmtCompiantMethod5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(104).getCell(3));ca.setMgmtCompiantProduct0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(104).getCell(4));ca.setMgmtCompiantProduct1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(104).getCell(5));ca.setMgmtCompiantProduct2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(104).getCell(6));ca.setMgmtCompiantProduct3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(104).getCell(7));ca.setMgmtCompiantProduct4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(104).getCell(8));ca.setMgmtCompiantProduct5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(105).getCell(3));ca.setMgmtCompiantAd0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(105).getCell(4));ca.setMgmtCompiantAd1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(105).getCell(5));ca.setMgmtCompiantAd2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(105).getCell(6));ca.setMgmtCompiantAd3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(105).getCell(7));ca.setMgmtCompiantAd4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(105).getCell(8));ca.setMgmtCompiantAd5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(106).getCell(3));ca.setMgmtTraining0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(106).getCell(4));ca.setMgmtTraining1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(106).getCell(5));ca.setMgmtTraining2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(106).getCell(6));ca.setMgmtTraining3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(106).getCell(7));ca.setMgmtTraining4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(106).getCell(8));ca.setMgmtTraining5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(107).getCell(3));ca.setMgmtReport0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(107).getCell(4));ca.setMgmtReport1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(107).getCell(5));ca.setMgmtReport2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(107).getCell(6));ca.setMgmtReport3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(107).getCell(7));ca.setMgmtReport4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(107).getCell(8));ca.setMgmtReport5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(108).getCell(3));ca.setMgmtPlan0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(108).getCell(4));ca.setMgmtPlan1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(108).getCell(5));ca.setMgmtPlan2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(108).getCell(6));ca.setMgmtPlan3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(108).getCell(7));ca.setMgmtPlan4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(108).getCell(8));ca.setMgmtPlan5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(109).getCell(3));ca.setMgmtMaintain0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(109).getCell(4));ca.setMgmtMaintain1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(109).getCell(5));ca.setMgmtMaintain2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(109).getCell(6));ca.setMgmtMaintain3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(109).getCell(7));ca.setMgmtMaintain4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(109).getCell(8));ca.setMgmtMaintain5(value);} catch (Exception e) {}

        try {String value = cp.getCellValue(sh.getRow(110).getCell(3));ca.setMgmtFace2Face0(value.length() > 0);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(110).getCell(4));ca.setMgmtFace2Face1(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(110).getCell(5));ca.setMgmtFace2Face2(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(110).getCell(6));ca.setMgmtFace2Face3(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(110).getCell(7));ca.setMgmtFace2Face4(value);} catch (Exception e) {}
        try {String value = cp.getCellValue(sh.getRow(110).getCell(8));ca.setMgmtFace2Face5(value);} catch (Exception e) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(114).getCell(2));ca.setClubSalesRatio((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(116).getCell(2));ca.setClubAchAppRatio((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(113).getCell(4));ca.setClubAch1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(113).getCell(5));ca.setClubAch2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(113).getCell(6));ca.setClubAch3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(113).getCell(7));ca.setClubAch4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(113).getCell(8));ca.setClubAch5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(113).getCell(9));ca.setClubAch6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(114).getCell(4));ca.setClubMm1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(114).getCell(5));ca.setClubMm2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(114).getCell(6));ca.setClubMm3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(114).getCell(7));ca.setClubMm4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(114).getCell(8));ca.setClubMm5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(114).getCell(9));ca.setClubMm6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(115).getCell(4));ca.setClubApp1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(115).getCell(5));ca.setClubApp2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(115).getCell(6));ca.setClubApp3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(115).getCell(7));ca.setClubApp4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(115).getCell(8));ca.setClubApp5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(115).getCell(9));ca.setClubApp6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(116).getCell(4));ca.setClubNs1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(116).getCell(5));ca.setClubNs2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(116).getCell(6));ca.setClubNs3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(116).getCell(7));ca.setClubNs4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(116).getCell(8));ca.setClubNs5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(116).getCell(9));ca.setClubNs6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(117).getCell(4));ca.setClubLx1((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(117).getCell(5));ca.setClubLx2((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(117).getCell(6));ca.setClubLx3((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(117).getCell(7));ca.setClubLx4((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(117).getCell(8));ca.setClubLx5((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(117).getCell(9));ca.setClubLx6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {ca.setStaff1Name(cp.getCellValue(sh.getRow(118).getCell(0)));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(119).getCell(2));ca.setStaff1SalesRatio((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(121).getCell(2));ca.setStaff1AchAppRatio((float)cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff1Ach1((int)sh.getRow(118).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ach2((int)sh.getRow(118).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ach3((int)sh.getRow(118).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ach4((int)sh.getRow(118).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ach5((int)sh.getRow(118).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(118).getCell(9));ca.setStaff1Ach6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff1Mm1((int)sh.getRow(119).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Mm2((int)sh.getRow(119).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Mm3((int)sh.getRow(119).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Mm4((int)sh.getRow(119).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Mm5((int)sh.getRow(119).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(119).getCell(9));ca.setStaff1Mm6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff1App1((int)sh.getRow(120).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1App2((int)sh.getRow(120).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1App3((int)sh.getRow(120).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1App4((int)sh.getRow(120).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1App5((int)sh.getRow(120).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(120).getCell(9));ca.setStaff1App6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff1Ns1((int)sh.getRow(121).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ns2((int)sh.getRow(121).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ns3((int)sh.getRow(121).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ns4((int)sh.getRow(121).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Ns5((int)sh.getRow(121).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(121).getCell(9));ca.setStaff1Ns6((int)cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff1Lx1((int)sh.getRow(122).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Lx2((int)sh.getRow(122).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Lx3((int)sh.getRow(122).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Lx4((int)sh.getRow(122).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff1Lx5((int)sh.getRow(122).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(122).getCell(9));ca.setStaff1Lx6((int)cellValue.getNumberValue());} catch (Exception e) {}

        try {ca.setStaff2Name(cp.getCellValue(sh.getRow(123).getCell(0)));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(124).getCell(2));ca.setStaff2SalesRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(126).getCell(2));ca.setStaff2AchAppRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff2Ach1((int) sh.getRow(123).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ach2((int) sh.getRow(123).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ach3((int) sh.getRow(123).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ach4((int) sh.getRow(123).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ach5((int) sh.getRow(123).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(123).getCell(9));ca.setStaff2Ach6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff2Mm1((int) sh.getRow(124).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Mm2((int) sh.getRow(124).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Mm3((int) sh.getRow(124).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Mm4((int) sh.getRow(124).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Mm5((int) sh.getRow(124).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(124).getCell(9));ca.setStaff2Mm6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff2App1((int) sh.getRow(125).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2App2((int) sh.getRow(125).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2App3((int) sh.getRow(125).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2App4((int) sh.getRow(125).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2App5((int) sh.getRow(125).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(125).getCell(9));ca.setStaff2App6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff2Ns1((int) sh.getRow(126).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ns2((int) sh.getRow(126).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ns3((int) sh.getRow(126).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ns4((int) sh.getRow(126).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Ns5((int) sh.getRow(126).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(126).getCell(9));ca.setStaff2Ns6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff2Lx1((int) sh.getRow(127).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Lx2((int) sh.getRow(127).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Lx3((int) sh.getRow(127).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Lx4((int) sh.getRow(127).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff2Lx5((int) sh.getRow(127).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(127).getCell(9));ca.setStaff2Lx6((int) cellValue.getNumberValue());} catch (Exception e) {}

        try {ca.setStaff3Name(cp.getCellValue(sh.getRow(128).getCell(0)));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(129).getCell(2));ca.setStaff3SalesRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(131).getCell(2));ca.setStaff3AchAppRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff3Ach1((int) sh.getRow(128).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ach2((int) sh.getRow(128).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ach3((int) sh.getRow(128).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ach4((int) sh.getRow(128).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ach5((int) sh.getRow(128).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(128).getCell(9));ca.setStaff3Ach6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff3Mm1((int) sh.getRow(129).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Mm2((int) sh.getRow(129).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Mm3((int) sh.getRow(129).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Mm4((int) sh.getRow(129).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Mm5((int) sh.getRow(129).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(129).getCell(9));ca.setStaff3Mm6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff3App1((int) sh.getRow(130).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3App2((int) sh.getRow(130).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3App3((int) sh.getRow(130).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3App4((int) sh.getRow(130).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3App5((int) sh.getRow(130).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(130).getCell(9));ca.setStaff3App6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff3Ns1((int) sh.getRow(131).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ns2((int) sh.getRow(131).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ns3((int) sh.getRow(131).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ns4((int) sh.getRow(131).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Ns5((int) sh.getRow(131).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(131).getCell(9));ca.setStaff3Ns6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff3Lx1((int) sh.getRow(132).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Lx2((int) sh.getRow(132).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Lx3((int) sh.getRow(132).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Lx4((int) sh.getRow(132).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff3Lx5((int) sh.getRow(132).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(132).getCell(9));ca.setStaff3Lx6((int) cellValue.getNumberValue());} catch (Exception e) {}

        try {ca.setStaff4Name(cp.getCellValue(sh.getRow(133).getCell(0)));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(134).getCell(2));ca.setStaff4SalesRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(136).getCell(2));ca.setStaff4AchAppRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff4Ach1((int) sh.getRow(133).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ach2((int) sh.getRow(133).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ach3((int) sh.getRow(133).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ach4((int) sh.getRow(133).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ach5((int) sh.getRow(133).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(133).getCell(9));ca.setStaff4Ach6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff4Mm1((int) sh.getRow(119).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Mm2((int) sh.getRow(119).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Mm3((int) sh.getRow(119).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Mm4((int) sh.getRow(119).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Mm5((int) sh.getRow(119).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(119).getCell(9));ca.setStaff4Mm6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff4App1((int) sh.getRow(135).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4App2((int) sh.getRow(135).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4App3((int) sh.getRow(135).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4App4((int) sh.getRow(135).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4App5((int) sh.getRow(135).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(135).getCell(9));ca.setStaff4App6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff4Ns1((int) sh.getRow(136).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ns2((int) sh.getRow(136).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ns3((int) sh.getRow(136).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ns4((int) sh.getRow(136).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Ns5((int) sh.getRow(136).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(136).getCell(9));ca.setStaff4Ns6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff4Lx1((int) sh.getRow(137).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Lx2((int) sh.getRow(137).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Lx3((int) sh.getRow(137).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Lx4((int) sh.getRow(137).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff4Lx5((int) sh.getRow(137).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(137).getCell(9));ca.setStaff4Lx6((int) cellValue.getNumberValue());} catch (Exception e) {}

        try {ca.setStaff5Name(cp.getCellValue(sh.getRow(138).getCell(0)));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(139).getCell(2));ca.setStaff5SalesRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(141).getCell(2));ca.setStaff5AchAppRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff5Ach1((int) sh.getRow(138).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ach2((int) sh.getRow(138).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ach3((int) sh.getRow(138).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ach4((int) sh.getRow(138).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ach5((int) sh.getRow(138).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(138).getCell(9));ca.setStaff5Ach6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff5Mm1((int) sh.getRow(119).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Mm2((int) sh.getRow(119).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Mm3((int) sh.getRow(119).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Mm4((int) sh.getRow(119).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Mm5((int) sh.getRow(119).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(119).getCell(9));ca.setStaff5Mm6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff5App1((int) sh.getRow(140).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5App2((int) sh.getRow(140).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5App3((int) sh.getRow(140).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5App4((int) sh.getRow(140).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5App5((int) sh.getRow(140).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(140).getCell(9));ca.setStaff5App6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff5Ns1((int) sh.getRow(141).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ns2((int) sh.getRow(141).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ns3((int) sh.getRow(141).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ns4((int) sh.getRow(141).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Ns5((int) sh.getRow(141).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(141).getCell(9));ca.setStaff5Ns6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff5Lx1((int) sh.getRow(142).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Lx2((int) sh.getRow(142).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Lx3((int) sh.getRow(142).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Lx4((int) sh.getRow(142).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff5Lx5((int) sh.getRow(142).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(142).getCell(9));ca.setStaff5Lx6((int) cellValue.getNumberValue());} catch (Exception e) {}

        try {ca.setStaff6Name(cp.getCellValue(sh.getRow(143).getCell(0)));} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(144).getCell(2));ca.setStaff6SalesRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(146).getCell(2));ca.setStaff6AchAppRatio((float) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff6Ach1((int) sh.getRow(143).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ach2((int) sh.getRow(143).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ach3((int) sh.getRow(143).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ach4((int) sh.getRow(143).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ach5((int) sh.getRow(143).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(143).getCell(9));ca.setStaff6Ach6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff6Mm1((int) sh.getRow(119).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Mm2((int) sh.getRow(119).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Mm3((int) sh.getRow(119).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Mm4((int) sh.getRow(119).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Mm5((int) sh.getRow(119).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(119).getCell(9));ca.setStaff6Mm6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff6App1((int) sh.getRow(145).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6App2((int) sh.getRow(145).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6App3((int) sh.getRow(145).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6App4((int) sh.getRow(145).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6App5((int) sh.getRow(145).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(145).getCell(9));ca.setStaff6App6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff6Ns1((int) sh.getRow(146).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ns2((int) sh.getRow(146).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ns3((int) sh.getRow(146).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ns4((int) sh.getRow(146).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Ns5((int) sh.getRow(146).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(146).getCell(9));ca.setStaff6Ns6((int) cellValue.getNumberValue());} catch (Exception e) {}
        try {ca.setStaff6Lx1((int) sh.getRow(147).getCell(4).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Lx2((int) sh.getRow(147).getCell(5).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Lx3((int) sh.getRow(147).getCell(6).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Lx4((int) sh.getRow(147).getCell(7).getNumericCellValue());} catch (Exception e) {}
        try {ca.setStaff6Lx5((int) sh.getRow(147).getCell(8).getNumericCellValue());} catch (Exception e) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(147).getCell(9));ca.setStaff6Lx6((int) cellValue.getNumberValue());} catch (Exception e) {}}
}
