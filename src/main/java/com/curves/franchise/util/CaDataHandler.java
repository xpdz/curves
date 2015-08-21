package com.curves.franchise.util;

import com.curves.franchise.domain.Ca;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

class CaDataHandler {
    private final Logger logger = LoggerFactory.getLogger(CaDataHandler.class);

    private final CurvesParser cp;
    private int version = -1;
    private FormulaEvaluator evaluator = null;

    public CaDataHandler(CurvesParser cp) {
        this.cp = cp;
    }

    public void processCA(Sheet sh, FormulaEvaluator evaluator, int clubId) {
        this.evaluator = evaluator;
        setVersion(sh);
        if (version == -1) {
            logger.error("Cannot determine CA version !!!");
        }

        Ca ca = new Ca();
        ca.setLastModified(new Date());
        ca.setClubId(clubId);
        ca.setCaYear(cp.year);
        ca.setCaMonth(cp.month);

        setupCa(sh, ca);

        Ca cax = cp.caRepo.findByClubIdAndCaYearAndCaMonth(ca.getClubId(), ca.getCaYear(), ca.getCaMonth());
        if (cax != null) {
            ca.setId(cax.getId());
        }
        cp.caRepo.save(ca);

        logger.info("<-- CA Saved. clubId: "+ca.getClubId()+", caYear: "+ca.getCaYear()+", caMonth: "+ca.getCaMonth());
        logger.info("");
    }

    private void setVersion(Sheet sh) {
        try {
            if ("Monthly".equals(sh.getRow(107).getCell(0).getStringCellValue())) {
                version = 2012;
            }
        } catch (Exception e) {
        }
        try {
            if ("Monthly".equals(sh.getRow(109).getCell(0).getStringCellValue())) {
                version = 2014;
            }
        } catch (Exception e) {
        }
        try {
            if ("Monthly".equals(sh.getRow(110).getCell(0).getStringCellValue())) {
                version = 2015;
            }
        } catch (Exception e) {
        }
    }

    private void setupCa(Sheet sh, Ca ca) {
        setupGoals(sh, ca);
        setupPlan(sh, ca);
        setupSvc(sh, ca);
        setupCm(sh, ca);
        setupSales(sh, ca);
        setupMgmt(sh, ca);
        setupClub(sh, ca);
        setupStaff1(sh, ca);
        setupStaff2(sh, ca);
        setupStaff3(sh, ca);
        setupStaff4(sh, ca);
        setupStaff5(sh, ca);
        setupStaff6(sh, ca);
    }

    private void setupGoals(Sheet sh, Ca ca) {
        try {ca.setGoalsTm((int)sh.getRow(4).getCell(9).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setGoalsLastTm((int)sh.getRow(5).getCell(9).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setGoalsLastActive((int)sh.getRow(6).getCell(9).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setGoalsLastShowRatio((float)sh.getRow(7).getCell(9).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setGoalsLastSalesRatio((float)sh.getRow(8).getCell(9).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setGoalsExitsRatio((float)sh.getRow(9).getCell(9).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(10).getCell(9));ca.setGoalsNewSales((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(11).getCell(9));ca.setGoalsAppoints((int)cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupStaff6(Sheet sh, Ca ca) {
        int base = 143;
        if (version == 2015) {
            base = 146;
        } else if (version == 2014) {
            base = 145;
        }
        try {ca.setStaff6Name(cp.getCellValue(sh.getRow(base).getCell(0)));} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(2));ca.setStaff6SalesRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(2));ca.setStaff6AchAppRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ach1((int) sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ach2((int) sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ach3((int) sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ach4((int) sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ach5((int) sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setStaff6Ach6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff6Mm1((int) sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Mm2((int) sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Mm3((int) sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Mm4((int) sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Mm5((int) sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setStaff6Mm6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff6App1((int) sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6App2((int) sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6App3((int) sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6App4((int) sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6App5((int) sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setStaff6App6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ns1((int) sh.getRow(base+3).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ns2((int) sh.getRow(base+3).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ns3((int) sh.getRow(base+3).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ns4((int) sh.getRow(base+3).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Ns5((int) sh.getRow(base+3).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setStaff6Ns6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff6Lx1((int) sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Lx2((int) sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Lx3((int) sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Lx4((int) sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff6Lx5((int) sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setStaff6Lx6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupStaff5(Sheet sh, Ca ca) {
        int base = 138;
        if (version == 2015) {
            base = 141;
        } else if (version == 2014) {
            base = 140;
        }
        try {ca.setStaff5Name(cp.getCellValue(sh.getRow(base).getCell(0)));} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(2));ca.setStaff5SalesRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(2));ca.setStaff5AchAppRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ach1((int) sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ach2((int) sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ach3((int) sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ach4((int) sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ach5((int) sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setStaff5Ach6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff5Mm1((int) sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Mm2((int) sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Mm3((int) sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Mm4((int) sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Mm5((int) sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setStaff5Mm6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff5App1((int) sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5App2((int) sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5App3((int) sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5App4((int) sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5App5((int) sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setStaff5App6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ns1((int) sh.getRow(base+3).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ns2((int) sh.getRow(base+3).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ns3((int) sh.getRow(base+3).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ns4((int) sh.getRow(base+3).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Ns5((int) sh.getRow(base+3).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setStaff5Ns6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff5Lx1((int) sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Lx2((int) sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Lx3((int) sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Lx4((int) sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff5Lx5((int) sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setStaff5Lx6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupStaff4(Sheet sh, Ca ca) {
        int base = 133;
        if (version == 2015) {
            base = 136;
        } else if (version == 2014) {
            base = 135;
        }
        try {ca.setStaff4Name(cp.getCellValue(sh.getRow(base).getCell(0)));} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(2));ca.setStaff4SalesRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(2));ca.setStaff4AchAppRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ach1((int) sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ach2((int) sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ach3((int) sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ach4((int) sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ach5((int) sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setStaff4Ach6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff4Mm1((int) sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Mm2((int) sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Mm3((int) sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Mm4((int) sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Mm5((int) sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setStaff4Mm6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff4App1((int) sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4App2((int) sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4App3((int) sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4App4((int) sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4App5((int) sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setStaff4App6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ns1((int) sh.getRow(base+3).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ns2((int) sh.getRow(base+3).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ns3((int) sh.getRow(base+3).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ns4((int) sh.getRow(base+3).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Ns5((int) sh.getRow(base+3).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setStaff4Ns6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff4Lx1((int) sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Lx2((int) sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Lx3((int) sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Lx4((int) sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff4Lx5((int) sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setStaff4Lx6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupStaff3(Sheet sh, Ca ca) {
        int base = 128;
        if (version == 2015) {
            base = 131;
        } else if (version == 2014) {
            base = 130;
        }
        try {ca.setStaff3Name(cp.getCellValue(sh.getRow(base).getCell(0)));} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(2));ca.setStaff3SalesRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(2));ca.setStaff3AchAppRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ach1((int) sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ach2((int) sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ach3((int) sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ach4((int) sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ach5((int) sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setStaff3Ach6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff3Mm1((int) sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Mm2((int) sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Mm3((int) sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Mm4((int) sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Mm5((int) sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setStaff3Mm6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff3App1((int) sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3App2((int) sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3App3((int) sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3App4((int) sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3App5((int) sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setStaff3App6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ns1((int) sh.getRow(base+3).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ns2((int) sh.getRow(base+3).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ns3((int) sh.getRow(base+3).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ns4((int) sh.getRow(base+3).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Ns5((int) sh.getRow(base+3).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setStaff3Ns6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff3Lx1((int) sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Lx2((int) sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Lx3((int) sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Lx4((int) sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff3Lx5((int) sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setStaff3Lx6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupStaff2(Sheet sh, Ca ca) {
        int base = 123;
        if (version == 2015) {
            base = 126;
        } else if (version == 2014) {
            base = 125;
        }
        try {ca.setStaff2Name(cp.getCellValue(sh.getRow(base).getCell(0)));} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(2));ca.setStaff2SalesRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(2));ca.setStaff2AchAppRatio((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ach1((int) sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ach2((int) sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ach3((int) sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ach4((int) sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ach5((int) sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setStaff2Ach6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff2Mm1((int) sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Mm2((int) sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Mm3((int) sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Mm4((int) sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Mm5((int) sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setStaff2Mm6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff2App1((int) sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2App2((int) sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2App3((int) sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2App4((int) sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2App5((int) sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setStaff2App6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ns1((int) sh.getRow(base+3).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ns2((int) sh.getRow(base+3).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ns3((int) sh.getRow(base+3).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ns4((int) sh.getRow(base+3).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Ns5((int) sh.getRow(base+3).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setStaff2Ns6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff2Lx1((int) sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Lx2((int) sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Lx3((int) sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Lx4((int) sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff2Lx5((int) sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setStaff2Lx6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupStaff1(Sheet sh, Ca ca) {
        int base = 118;
        if (version == 2015) {
            base = 121;
        } else if (version == 2014) {
            base = 120;
        }
        try {ca.setStaff1Name(cp.getCellValue(sh.getRow(base).getCell(0)));} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(2));ca.setStaff1SalesRatio((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(2));ca.setStaff1AchAppRatio((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ach1((int)sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ach2((int)sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ach3((int)sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ach4((int)sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ach5((int)sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setStaff1Ach6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff1Mm1((int)sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Mm2((int)sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Mm3((int)sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Mm4((int)sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Mm5((int)sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setStaff1Mm6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff1App1((int)sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1App2((int)sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1App3((int)sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1App4((int)sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1App5((int)sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setStaff1App6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ns1((int)sh.getRow(base+3).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ns2((int)sh.getRow(base+3).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ns3((int)sh.getRow(base+3).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ns4((int)sh.getRow(base+3).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Ns5((int)sh.getRow(base+3).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setStaff1Ns6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {ca.setStaff1Lx1((int)sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Lx2((int)sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Lx3((int)sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Lx4((int)sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setStaff1Lx5((int)sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setStaff1Lx6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupClub(Sheet sh, Ca ca) {
        int base = 113;
        if (version == 2015) {
            base = 116;
        } else if (version == 2014) {
            base = 115;
        }
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(2));ca.setClubSalesRatio((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(2));ca.setClubAchAppRatio((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(4));ca.setClubAch1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(5));ca.setClubAch2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(6));ca.setClubAch3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(7));ca.setClubAch4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(8));ca.setClubAch5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setClubAch6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(4));ca.setClubMm1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(5));ca.setClubMm2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(6));ca.setClubMm3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(7));ca.setClubMm4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(8));ca.setClubMm5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setClubMm6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(4));ca.setClubApp1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(5));ca.setClubApp2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(6));ca.setClubApp3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(7));ca.setClubApp4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(8));ca.setClubApp5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setClubApp6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(4));ca.setClubNs1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(5));ca.setClubNs2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(6));ca.setClubNs3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(7));ca.setClubNs4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(8));ca.setClubNs5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setClubNs6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(4));ca.setClubLx1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(5));ca.setClubLx2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(6));ca.setClubLx3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(7));ca.setClubLx4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(8));ca.setClubLx5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setClubLx6((int)cellValue.getNumberValue());} catch (Exception ignored) {}
    }

    private void setupMgmt(Sheet sh, Ca ca) {
        int base = 96;
        if (version == 2015) {
            base = 99;
        } else if (version == 2014) {
            base = 98;
        }
        try {String value = cp.getCellValue(sh.getRow(base).getCell(4));ca.setMgmtMeeting1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base).getCell(5));ca.setMgmtMeeting2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base).getCell(6));ca.setMgmtMeeting3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base).getCell(7));ca.setMgmtMeeting4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base).getCell(8));ca.setMgmtMeeting5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+1).getCell(4));ca.setMgmtCa1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+1).getCell(5));ca.setMgmtCa2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+1).getCell(6));ca.setMgmtCa3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+1).getCell(7));ca.setMgmtCa4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+1).getCell(8));ca.setMgmtCa5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+2).getCell(4));ca.setMgmtGp1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+2).getCell(5));ca.setMgmtGp2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+2).getCell(6));ca.setMgmtGp3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+2).getCell(7));ca.setMgmtGp4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+2).getCell(8));ca.setMgmtGp5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+3).getCell(4));ca.setMgmtLearn1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+3).getCell(5));ca.setMgmtLearn2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+3).getCell(6));ca.setMgmtLearn3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+3).getCell(7));ca.setMgmtLearn4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+3).getCell(8));ca.setMgmtLearn5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+4).getCell(4));ca.setMgmtSheet1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+4).getCell(5));ca.setMgmtSheet2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+4).getCell(6));ca.setMgmtSheet3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+4).getCell(7));ca.setMgmtSheet4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+4).getCell(8));ca.setMgmtSheet5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+5).getCell(4));ca.setMgmtPolicy1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+5).getCell(5));ca.setMgmtPolicy2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+5).getCell(6));ca.setMgmtPolicy3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+5).getCell(7));ca.setMgmtPolicy4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+5).getCell(8));ca.setMgmtPolicy5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(4));ca.setMgmtCompiantSales1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(5));ca.setMgmtCompiantSales2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(6));ca.setMgmtCompiantSales3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(7));ca.setMgmtCompiantSales4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(8));ca.setMgmtCompiantSales5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(4));ca.setMgmtCompiantMethod1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(5));ca.setMgmtCompiantMethod2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(6));ca.setMgmtCompiantMethod3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(7));ca.setMgmtCompiantMethod4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(8));ca.setMgmtCompiantMethod5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(4));ca.setMgmtCompiantProduct1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(5));ca.setMgmtCompiantProduct2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(6));ca.setMgmtCompiantProduct3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(7));ca.setMgmtCompiantProduct4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(8));ca.setMgmtCompiantProduct5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(4));ca.setMgmtCompiantAd1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(5));ca.setMgmtCompiantAd2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(6));ca.setMgmtCompiantAd3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(7));ca.setMgmtCompiantAd4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(8));ca.setMgmtCompiantAd5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(4));ca.setMgmtTraining1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(5));ca.setMgmtTraining2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(6));ca.setMgmtTraining3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(7));ca.setMgmtTraining4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(8));ca.setMgmtTraining5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(4));ca.setMgmtReport1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(5));ca.setMgmtReport2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(6));ca.setMgmtReport3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(7));ca.setMgmtReport4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(8));ca.setMgmtReport5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+12).getCell(4));ca.setMgmtPlan1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+12).getCell(5));ca.setMgmtPlan2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+12).getCell(6));ca.setMgmtPlan3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+12).getCell(7));ca.setMgmtPlan4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+12).getCell(8));ca.setMgmtPlan5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+13).getCell(4));ca.setMgmtMaintain1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+13).getCell(5));ca.setMgmtMaintain2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+13).getCell(6));ca.setMgmtMaintain3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+13).getCell(7));ca.setMgmtMaintain4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+13).getCell(8));ca.setMgmtMaintain5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+14).getCell(4));ca.setMgmtFace2Face1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+14).getCell(5));ca.setMgmtFace2Face2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+14).getCell(6));ca.setMgmtFace2Face3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+14).getCell(7));ca.setMgmtFace2Face4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+14).getCell(8));ca.setMgmtFace2Face5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
    }

    private void setupSales(Sheet sh, Ca ca) {
        int base = 83;
        if (version == 2015) {
            base = 86;
        } else if (version == 2014) {
            base = 85;
        }
        try {ca.setSalesAch1((int)sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAch2((int)sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAch3((int)sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAch4((int)sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAch5((int)sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setSalesAch6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSalesMonthly1((int)sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesMonthly2((int)sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesMonthly3((int)sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesMonthly4((int)sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesMonthly5((int)sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setSalesMonthly6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSalesAllPrepay1((int)sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAllPrepay2((int)sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAllPrepay3((int)sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAllPrepay4((int)sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSalesAllPrepay5((int)sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setSalesAllPrepay6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(4));ca.setSalesTotal1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(5));ca.setSalesTotal2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(6));ca.setSalesTotal3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(7));ca.setSalesTotal4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(8));ca.setSalesTotal5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setSalesTotal6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(4));ca.setSalesRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(5));ca.setSalesRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(6));ca.setSalesRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(7));ca.setSalesRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(8));ca.setSalesRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setSalesRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(4));ca.setSalesAchAppRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(5));ca.setSalesAchAppRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(6));ca.setSalesAchAppRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(7));ca.setSalesAchAppRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(8));ca.setSalesAchAppRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(9));ca.setSalesAchAppRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(4));ca.setSalesFaReview1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(5));ca.setSalesFaReview2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(6));ca.setSalesFaReview3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(7));ca.setSalesFaReview4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+6).getCell(8));ca.setSalesFaReview5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(4));ca.setSalesPriceReview1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(5));ca.setSalesPriceReview2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(6));ca.setSalesPriceReview3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(7));ca.setSalesPriceReview4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+7).getCell(8));ca.setSalesPriceReview5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(4));ca.setSalesAck1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(5));ca.setSalesAck2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(6));ca.setSalesAck3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(7));ca.setSalesAck4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+8).getCell(8));ca.setSalesAck5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(4));ca.setSalesTarget1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(5));ca.setSalesTarget2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(6));ca.setSalesTarget3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(7));ca.setSalesTarget4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+9).getCell(8));ca.setSalesTarget5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(4));ca.setSalesMotivation1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(5));ca.setSalesMotivation2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(6));ca.setSalesMotivation3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(7));ca.setSalesMotivation4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+10).getCell(8));ca.setSalesMotivation5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(4));ca.setSalesObstacle1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(5));ca.setSalesObstacle2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(6));ca.setSalesObstacle3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(7));ca.setSalesObstacle4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+11).getCell(8));ca.setSalesObstacle5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
    }

    private void setupCm(Sheet sh, Ca ca) {
        int base = 39;
        if (version == 2015) {
            base = 41;
        } else if (version == 2014) {
            base = 40;
        }
        try {ca.setCmPostFlyer1((int) sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyer2((int) sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyer3((int) sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyer4((int) sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyer5((int) sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base).getCell(9));ca.setCmPostFlyer6((int) cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmHandFlyerHours1((float) sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerHours2((float) sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerHours3((float) sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerHours4((float) sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerHours5((float) sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+1).getCell(9));ca.setCmHandFlyerHours6((float) cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmOutGp1((int)sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGp2((int)sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGp3((int)sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGp4((int)sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGp5((int)sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+2).getCell(9));ca.setCmOutGp6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmCpBox1((int)sh.getRow(base+3).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpBox2((int)sh.getRow(base+3).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpBox3((int)sh.getRow(base+3).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpBox4((int)sh.getRow(base+3).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpBox5((int)sh.getRow(base+3).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setCmCpBox6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmOutGot1((int)sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGot2((int)sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGot3((int)sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGot4((int)sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGot5((int)sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setCmOutGot6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmInGot1((int)sh.getRow(base+5).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGot2((int)sh.getRow(base+5).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGot3((int)sh.getRow(base+5).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGot4((int)sh.getRow(base+5).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGot5((int)sh.getRow(base+5).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(9));ca.setCmInGot6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmBlogGot1((int)sh.getRow(base+6).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGot2((int)sh.getRow(base+6).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGot3((int)sh.getRow(base+6).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGot4((int)sh.getRow(base+6).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGot5((int)sh.getRow(base+6).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+6).getCell(9));ca.setCmBlogGot6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmBagGot1((int)sh.getRow(base+7).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGot2((int)sh.getRow(base+7).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGot3((int)sh.getRow(base+7).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGot4((int)sh.getRow(base+7).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGot5((int)sh.getRow(base+7).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+7).getCell(9));ca.setCmBagGot6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(4));ca.setCmTotalGot1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(5));ca.setCmTotalGot2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(6));ca.setCmTotalGot3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(7));ca.setCmTotalGot4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(8));ca.setCmTotalGot5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(9));ca.setCmTotalGot6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmCallIn1((int)sh.getRow(base+10).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCallIn2((int)sh.getRow(base+10).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCallIn3((int)sh.getRow(base+10).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCallIn4((int)sh.getRow(base+10).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCallIn5((int)sh.getRow(base+10).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+10).getCell(9));ca.setCmCallIn6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmOutGotCall1((int)sh.getRow(base+11).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGotCall2((int)sh.getRow(base+11).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGotCall3((int)sh.getRow(base+11).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGotCall4((int)sh.getRow(base+11).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutGotCall5((int)sh.getRow(base+11).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+11).getCell(9));ca.setCmOutGotCall6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmInGotCall1((int)sh.getRow(base+12).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGotCall2((int)sh.getRow(base+12).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGotCall3((int)sh.getRow(base+12).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGotCall4((int)sh.getRow(base+12).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInGotCall5((int)sh.getRow(base+12).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+12).getCell(9));ca.setCmInGotCall6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmBlogGotCall1((int)sh.getRow(base+13).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGotCall2((int)sh.getRow(base+13).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGotCall3((int)sh.getRow(base+13).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGotCall4((int)sh.getRow(base+13).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogGotCall5((int)sh.getRow(base+13).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+13).getCell(9));ca.setCmBlogGotCall6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmBagGotCall1((int)sh.getRow(base+14).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGotCall2((int)sh.getRow(base+14).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGotCall3((int)sh.getRow(base+14).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGotCall4((int)sh.getRow(base+14).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagGotCall5((int)sh.getRow(base+14).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+14).getCell(9));ca.setCmBagGotCall6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmOwnRefs1((int)sh.getRow(base+16).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOwnRefs2((int)sh.getRow(base+16).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOwnRefs3((int)sh.getRow(base+16).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOwnRefs4((int)sh.getRow(base+16).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOwnRefs5((int)sh.getRow(base+16).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+16).getCell(9));ca.setCmOwnRefs6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        if (version == 2015 || version == 2014) {
            try {ca.setCmOtherRefs1((int) sh.getRow(base + 17).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setCmOtherRefs2((int) sh.getRow(base + 17).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setCmOtherRefs3((int) sh.getRow(base + 17).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setCmOtherRefs4((int) sh.getRow(base + 17).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setCmOtherRefs5((int) sh.getRow(base + 17).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
            try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+17).getCell(9));ca.setCmOtherRefs6((int) cellValue.getNumberValue());} catch (Exception ignored) {}

            base += 1;
        }

        try {ca.setCmNewspaper1((int)sh.getRow(base+17).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmNewspaper2((int)sh.getRow(base+17).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmNewspaper3((int)sh.getRow(base+17).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmNewspaper4((int)sh.getRow(base+17).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmNewspaper5((int)sh.getRow(base+17).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+17).getCell(9));ca.setCmNewspaper6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmTv1((int)sh.getRow(base+18).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmTv2((int)sh.getRow(base+18).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmTv3((int)sh.getRow(base+18).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmTv4((int)sh.getRow(base+18).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmTv5((int)sh.getRow(base+18).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+18).getCell(9));ca.setCmTv6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmInternet1((int)sh.getRow(58).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInternet2((int)sh.getRow(58).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInternet3((int)sh.getRow(58).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInternet4((int)sh.getRow(58).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInternet5((int)sh.getRow(58).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(58).getCell(9));ca.setCmInternet6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmSign1((int)sh.getRow(base+20).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmSign2((int)sh.getRow(base+20).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmSign3((int)sh.getRow(base+20).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmSign4((int)sh.getRow(base+20).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmSign5((int)sh.getRow(base+20).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+20).getCell(9));ca.setCmSign6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmMate1((int)sh.getRow(base+21).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMate2((int)sh.getRow(base+21).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMate3((int)sh.getRow(base+21).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMate4((int)sh.getRow(base+21).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMate5((int)sh.getRow(base+21).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+21).getCell(9));ca.setCmMate6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmOthers1((int)sh.getRow(base+22).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOthers2((int)sh.getRow(base+22).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOthers3((int)sh.getRow(base+22).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOthers4((int)sh.getRow(base+22).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOthers5((int)sh.getRow(base+22).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+22).getCell(9));ca.setCmOthers6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmMailAgpIn1((int)sh.getRow(base+23).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMailAgpIn2((int)sh.getRow(base+23).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMailAgpIn3((int)sh.getRow(base+23).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMailAgpIn4((int)sh.getRow(base+23).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmMailAgpIn5((int)sh.getRow(base+23).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+23).getCell(9));ca.setCmMailAgpIn6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmPostFlyerAgpIn1((int)sh.getRow(base+24).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyerAgpIn2((int)sh.getRow(base+24).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyerAgpIn3((int)sh.getRow(base+24).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyerAgpIn4((int)sh.getRow(base+24).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmPostFlyerAgpIn5((int)sh.getRow(base+24).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+24).getCell(9));ca.setCmPostFlyerAgpIn6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmHandFlyerAgpIn1((int)sh.getRow(base+25).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerAgpIn2((int)sh.getRow(base+25).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerAgpIn3((int)sh.getRow(base+25).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerAgpIn4((int)sh.getRow(base+25).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmHandFlyerAgpIn5((int)sh.getRow(base+25).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+25).getCell(9));ca.setCmHandFlyerAgpIn6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmCpAgpIn1((int)sh.getRow(base+26).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpAgpIn2((int)sh.getRow(base+26).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpAgpIn3((int)sh.getRow(base+26).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpAgpIn4((int)sh.getRow(base+26).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmCpAgpIn5((int)sh.getRow(base+26).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+26).getCell(9));ca.setCmCpAgpIn6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmOutAgpOut1((int)sh.getRow(base+27).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutAgpOut2((int)sh.getRow(base+27).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutAgpOut3((int)sh.getRow(base+27).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutAgpOut4((int)sh.getRow(base+27).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmOutAgpOut5((int)sh.getRow(base+27).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+27).getCell(9));ca.setCmOutAgpOut6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmInAgpOut1((int)sh.getRow(base+28).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInAgpOut2((int)sh.getRow(base+28).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInAgpOut3((int)sh.getRow(base+28).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInAgpOut4((int)sh.getRow(base+28).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmInAgpOut5((int)sh.getRow(base+28).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+28).getCell(9));ca.setCmInAgpOut6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmBlogAgpOut1((int)sh.getRow(base+29).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogAgpOut2((int)sh.getRow(base+29).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogAgpOut3((int)sh.getRow(base+29).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogAgpOut4((int)sh.getRow(base+29).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBlogAgpOut5((int)sh.getRow(base+29).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+29).getCell(9));ca.setCmBlogAgpOut6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmBagAgpOut1((int)sh.getRow(base+30).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagAgpOut2((int)sh.getRow(base+30).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagAgpOut3((int)sh.getRow(base+30).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagAgpOut4((int)sh.getRow(base+30).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmBagAgpOut5((int)sh.getRow(base+30).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+30).getCell(9));ca.setCmBagAgpOut6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+31).getCell(4));ca.setCmApoTotal1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+31).getCell(5));ca.setCmApoTotal2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+31).getCell(6));ca.setCmApoTotal3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+31).getCell(7));ca.setCmApoTotal4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+31).getCell(8));ca.setCmApoTotal5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+31).getCell(9));ca.setCmApoTotal6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+32).getCell(4));ca.setCmInApptRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+32).getCell(5));ca.setCmInApptRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+32).getCell(6));ca.setCmInApptRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+32).getCell(7));ca.setCmInApptRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+32).getCell(8));ca.setCmInApptRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+32).getCell(9));ca.setCmInApptRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+33).getCell(4));ca.setCmOutApptRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+33).getCell(5));ca.setCmOutApptRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+33).getCell(6));ca.setCmOutApptRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+33).getCell(7));ca.setCmOutApptRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+33).getCell(8));ca.setCmOutApptRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+33).getCell(9));ca.setCmOutApptRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+34).getCell(9));ca.setCmPostPerApo6((int) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+35).getCell(9));ca.setCmHandHoursPerApo6((float) cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+36).getCell(9));ca.setCmOutGpHoursPerApo6((float) cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+37).getCell(4));ca.setCmBrAgpRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+37).getCell(5));ca.setCmBrAgpRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+37).getCell(6));ca.setCmBrAgpRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+37).getCell(7));ca.setCmBrAgpRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+37).getCell(8));ca.setCmBrAgpRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+37).getCell(9));ca.setCmBrAgpRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setCmFaSum1((int)sh.getRow(base+38).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmFaSum2((int)sh.getRow(base+38).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmFaSum3((int)sh.getRow(base+38).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmFaSum4((int)sh.getRow(base+38).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setCmFaSum5((int)sh.getRow(base+38).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+38).getCell(9));ca.setCmFaSum6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+39).getCell(4));ca.setCmShowRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+39).getCell(5));ca.setCmShowRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+39).getCell(6));ca.setCmShowRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+39).getCell(7));ca.setCmShowRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+39).getCell(8));ca.setCmShowRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+39).getCell(9));ca.setCmShowRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+40).getCell(4));ca.setCmTraining1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+40).getCell(5));ca.setCmTraining2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+40).getCell(6));ca.setCmTraining3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+40).getCell(7));ca.setCmTraining4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+40).getCell(8));ca.setCmTraining5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+41).getCell(4));ca.setCmGot3_1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+41).getCell(5));ca.setCmGot3_2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+41).getCell(6));ca.setCmGot3_3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+41).getCell(7));ca.setCmGot3_4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+41).getCell(8));ca.setCmGot3_5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+42).getCell(4));ca.setCmInvitation1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+42).getCell(5));ca.setCmInvitation2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+42).getCell(6));ca.setCmInvitation3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+42).getCell(7));ca.setCmInvitation4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+42).getCell(8));ca.setCmInvitation5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
    }

    private void setupSvc(Sheet sh, Ca ca) {
        int base = 13;
        try {ca.setSvcTm1((int)sh.getRow(base).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTm2((int)sh.getRow(base).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTm3((int)sh.getRow(base).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTm4((int)sh.getRow(base).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTm5((int)sh.getRow(base).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTm6((int)sh.getRow(base).getCell(9).getNumericCellValue());} catch (Exception ignored) {}

        if (version == 2015) {
            try {ca.setSvcShiftOut1((int) sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftOut2((int) sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftOut3((int) sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftOut4((int) sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftOut5((int) sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftOut6((int)sh.getRow(base+1).getCell(9).getNumericCellValue());} catch (Exception ignored) {}

            try {ca.setSvcShiftIn1((int) sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftIn2((int) sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftIn3((int) sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftIn4((int) sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftIn5((int) sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
            try {ca.setSvcShiftIn6((int) sh.getRow(base+2).getCell(9).getNumericCellValue());} catch (Exception ignored) {}

            base += 2;
        } else if (version == 2014) {
            try {int v = (int) sh.getRow(base+2).getCell(4).getNumericCellValue();if(v > 0) ca.setSvcShiftIn1(v); else ca.setSvcShiftOut1(v);} catch (Exception ignored) {}
            try {int v = (int) sh.getRow(base+2).getCell(5).getNumericCellValue();if(v > 0) ca.setSvcShiftIn1(v); else ca.setSvcShiftOut1(v);} catch (Exception ignored) {}
            try {int v = (int) sh.getRow(base+2).getCell(6).getNumericCellValue();if(v > 0) ca.setSvcShiftIn1(v); else ca.setSvcShiftOut1(v);} catch (Exception ignored) {}
            try {int v = (int) sh.getRow(base+2).getCell(7).getNumericCellValue();if(v > 0) ca.setSvcShiftIn1(v); else ca.setSvcShiftOut1(v);} catch (Exception ignored) {}
            try {int v = (int) sh.getRow(base+2).getCell(8).getNumericCellValue();if(v > 0) ca.setSvcShiftIn1(v); else ca.setSvcShiftOut1(v);} catch (Exception ignored) {}
            try {int v = (int) sh.getRow(base+2).getCell(9).getNumericCellValue();if(v > 0) ca.setSvcShiftIn1(v); else ca.setSvcShiftOut1(v);} catch (Exception ignored) {}

            base += 1;
        }

        try {ca.setSvcHold1((int)sh.getRow(base+1).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcHold2((int)sh.getRow(base+1).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcHold3((int)sh.getRow(base+1).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcHold4((int)sh.getRow(base+1).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcHold5((int)sh.getRow(base+1).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcHold6((int)sh.getRow(base+1).getCell(9).getNumericCellValue());} catch (Exception ignored) {}

        try {ca.setSvcActive1((int)sh.getRow(base+2).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcActive2((int)sh.getRow(base+2).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcActive3((int)sh.getRow(base+2).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcActive4((int)sh.getRow(base+2).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcActive5((int)sh.getRow(base+2).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcActive6((int)sh.getRow(base+2).getCell(9).getNumericCellValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(4));ca.setSvcHoldRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(5));ca.setSvcHoldRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(6));ca.setSvcHoldRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(7));ca.setSvcHoldRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(8));ca.setSvcHoldRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+3).getCell(9));ca.setSvcHoldRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvcTotalWo1((int)sh.getRow(base+4).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTotalWo2((int)sh.getRow(base+4).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTotalWo3((int)sh.getRow(base+4).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTotalWo4((int)sh.getRow(base+4).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcTotalWo5((int)sh.getRow(base+4).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+4).getCell(9));ca.setSvcTotalWo6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(4));ca.setSvcAvgWo1((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(5));ca.setSvcAvgWo2((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(6));ca.setSvcAvgWo3((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(7));ca.setSvcAvgWo4((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(8));ca.setSvcAvgWo5((int)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+5).getCell(9));ca.setSvcAvgWo6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvcMaxWo1((int)sh.getRow(base+6).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMaxWo2((int)sh.getRow(base+6).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMaxWo3((int)sh.getRow(base+6).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMaxWo4((int)sh.getRow(base+6).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMaxWo5((int)sh.getRow(base+6).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+6).getCell(9));ca.setSvcMaxWo6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvcExits1((int)sh.getRow(base+7).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcExits2((int)sh.getRow(base+7).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcExits3((int)sh.getRow(base+7).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcExits4((int)sh.getRow(base+7).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcExits5((int)sh.getRow(base+7).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+7).getCell(9));ca.setSvcExits6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(4));ca.setSvcExitsRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(5));ca.setSvcExitsRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(6));ca.setSvcExitsRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(7));ca.setSvcExitsRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(8));ca.setSvcExitsRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+8).getCell(9));ca.setSvcExitsRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvcMeasure1((int)sh.getRow(base+9).getCell(4).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMeasure2((int)sh.getRow(base+9).getCell(5).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMeasure3((int)sh.getRow(base+9).getCell(6).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMeasure4((int)sh.getRow(base+9).getCell(7).getNumericCellValue());} catch (Exception ignored) {}
        try {ca.setSvcMeasure5((int)sh.getRow(base+9).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+9).getCell(9));ca.setSvcMeasure6((int)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+10).getCell(4));ca.setSvcMeasureRatio1((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+10).getCell(5));ca.setSvcMeasureRatio2((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+10).getCell(6));ca.setSvcMeasureRatio3((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+10).getCell(7));ca.setSvcMeasureRatio4((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+10).getCell(8));ca.setSvcMeasureRatio5((float)cellValue.getNumberValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+10).getCell(9));ca.setSvcMeasureRatio6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvc12_5((int)sh.getRow(base+11).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+11).getCell(9));ca.setSvc12_6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvc8to11_5((int)sh.getRow(base+12).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+12).getCell(9));ca.setSvc8to11_6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvc4to7_5((int)sh.getRow(base+13).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+13).getCell(9));ca.setSvc4to7_6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvc1to3_5((int)sh.getRow(base+14).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+14).getCell(9));ca.setSvc1to3_6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {ca.setSvc0_5((int)sh.getRow(base+15).getCell(8).getNumericCellValue());} catch (Exception ignored) {}
        try {CellValue cellValue = evaluator.evaluate(sh.getRow(base+15).getCell(9));ca.setSvc0_6((float)cellValue.getNumberValue());} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+16).getCell(4));ca.setSvc3More1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+16).getCell(5));ca.setSvc3More2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+16).getCell(6));ca.setSvc3More3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+16).getCell(7));ca.setSvc3More4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+16).getCell(8));ca.setSvc3More5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+17).getCell(4));ca.setSvcInactive1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+17).getCell(5));ca.setSvcInactive2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+17).getCell(6));ca.setSvcInactive3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+17).getCell(7));ca.setSvcInactive4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+17).getCell(8));ca.setSvcInactive5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+18).getCell(4));ca.setSvcFwoReview1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+18).getCell(5));ca.setSvcFwoReview2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+18).getCell(6));ca.setSvcFwoReview3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+18).getCell(7));ca.setSvcFwoReview4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+18).getCell(8));ca.setSvcFwoReview5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+19).getCell(4));ca.setSvcInterview1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+19).getCell(5));ca.setSvcInterview2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+19).getCell(6));ca.setSvcInterview3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+19).getCell(7));ca.setSvcInterview4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+19).getCell(8));ca.setSvcInterview5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+20).getCell(4));ca.setSvcThanks1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+20).getCell(5));ca.setSvcThanks2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+20).getCell(6));ca.setSvcThanks3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+20).getCell(7));ca.setSvcThanks4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+20).getCell(8));ca.setSvcThanks5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+21).getCell(4));ca.setSvc3C1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+21).getCell(5));ca.setSvc3C2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+21).getCell(6));ca.setSvc3C3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+21).getCell(7));ca.setSvc3C4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+21).getCell(8));ca.setSvc3C5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+22).getCell(4));ca.setSvcReward1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+22).getCell(5));ca.setSvcReward2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+22).getCell(6));ca.setSvcReward3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+22).getCell(7));ca.setSvcReward4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+22).getCell(8));ca.setSvcReward5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}

        try {String value = cp.getCellValue(sh.getRow(base+23).getCell(4));ca.setSvcLoyal1((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+23).getCell(5));ca.setSvcLoyal2((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+23).getCell(6));ca.setSvcLoyal3((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+23).getCell(7));ca.setSvcLoyal4((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
        try {String value = cp.getCellValue(sh.getRow(base+23).getCell(8));ca.setSvcLoyal5((value != null && value.length() > 0) ? "v" : "");} catch (Exception ignored) {}
    }

    private void setupPlan(Sheet sh, Ca ca) {
        try {
            String content = "";
            for (int i = 4; i < 40; i++) {
                content += cp.getCellValue(sh.getRow(i).getCell(10));
            }
            ca.setThisPlan(content);
        } catch (Exception ignored) {}

        try {
            String content = "";
            for (int i = 95; i < 115; i++) {
                content += cp.getCellValue(sh.getRow(i).getCell(10));
            }
            ca.setNextPlan(content);
        } catch (Exception ignored) {}
    }
}
