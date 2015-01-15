package com.curves.franchise.web;

import com.curves.franchise.domain.Pj;
import com.curves.franchise.domain.PjSum;
import com.curves.franchise.repository.PjSumRepository;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class PjSumController {
    private Logger logger = LoggerFactory.getLogger(PjSumController.class);

    @Autowired
    private PjSumRepository pjSumRepo;

    @RequestMapping(value = "/rest/PJ", method = RequestMethod.GET)
    public PjSum findPJByUserAndYearAndMonth(@RequestParam("year") int year, @RequestParam("month") int month,
                                           @AuthenticationPrincipal UserDetails user) {
        logger.info("---findPJ---user: " + user.getUsername() + ", year: " + year + ", month: " + month);
        PjSum pjSum = pjSumRepo.findByClubIdAndYearAndMonth(Integer.parseInt(user.getUsername()), year, month);
        if (pjSum == null) {
            logger.info("---findPJ---PJ not found!");
            pjSum = new PjSum();
        }
        return pjSum;
    }

    @RequestMapping(value = "/rest/PJ/export", produces="application/vnd.ms-excel")
    public FileSystemResource exportPJ(@RequestParam("yStart") int yStart, @RequestParam("yEnd") int yEnd,
                                       @RequestParam("mStart") int mStart, @RequestParam("mEnd") int mEnd,
                                       @AuthenticationPrincipal UserDetails user) {
        logger.info("---exportPJ---user: " + user.getUsername() + ", yStart: " + yStart + ", yEnd: " + yEnd+", mStart: " + mStart + ", mEnd: "+mEnd);
        List<PjSum> pjSums = pjSumRepo.findByClubIdAndYearBetweenAndMonthBetween(Integer.parseInt(user.getUsername()),
                yStart, yEnd, mStart, mEnd);
        Workbook wb = null;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "PJ-template.xls");
        File target = null;
        try {
            target = File.createTempFile("PJ-export", "xls");
            FileUtils.copyFile(template, target);
            wb = WorkbookFactory.create(new FileInputStream(target));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        String[] dow = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        SimpleDateFormat sdf = new SimpleDateFormat("M-dd");
        Calendar calendar = Calendar.getInstance();

        for (PjSum pjSum : pjSums) {
            Sheet sh = wb.cloneSheet(1);
            wb.setSheetName(wb.getNumberOfSheets()-1, pjSum.getYear() + "-" + (pjSum.getMonth() + 1));
            Row row = sh.getRow(1);
            row.getCell(0).setCellValue(pjSum.getClubId());
            row.getCell(1).setCellValue(pjSum.getYear());
            row.getCell(2).setCellValue(pjSum.getMonth()+1);
            row.getCell(4).setCellValue(pjSum.getNewSales());
            row.getCell(5).setCellValue(pjSum.getExits());
            row.getCell(6).setCellValue(pjSum.getShiftIn());
            row.getCell(7).setCellValue(pjSum.getShiftOut());
            row.getCell(8).setCellValue(pjSum.getIncrement());
            row = sh.getRow(3);
            row.getCell(1).setCellValue(pjSum.getEnrolled());
            row.getCell(2).setCellValue(pjSum.getLeaves());
            row.getCell(3).setCellValue(pjSum.getValids());
            List<Pj> pjs = pjSum.getPjSet();
            if (pjs.size() < 31) {
                for (int j = 36; j > pjs.size() + 5; j--) {
                    sh.removeRow(sh.getRow(j));
                }
            }
            for (int i = 0; i < pjs.size(); i++) {
                Pj pj = pjs.get(i);
                row = sh.getRow(6+i);
                row.getCell(0).setCellValue(sdf.format(pj.getPjDate()));
                calendar.setTime(pj.getPjDate());
                row.getCell(1).setCellValue(dow[calendar.get(Calendar.DAY_OF_WEEK)]);
                row.getCell(2).setCellValue(i+1);
                row.getCell(3).setCellValue(pj.getWorkingDays());
                row.getCell(4).setCellValue(pj.getWorkOuts());
                row.getCell(5).setCellValue(pj.getNewSalesRevenue());
                row.getCell(6).setCellValue(pj.getDuesDraftsRevenue());
                row.getCell(7).setCellValue(pj.getProductsRevenue());
                row.getCell(8).setCellValue(pj.getWheyProteinRevenue());
                row.getCell(9).setCellValue(pj.getOtherRevenue());
                row.getCell(10).setCellValue(pj.getIncomingCalls());
                row.getCell(11).setCellValue(pj.getIncomingApo());
                row.getCell(12).setCellValue(pj.getOutgoingCalls());
                row.getCell(13).setCellValue(pj.getOutgoingApo());
                row.getCell(14).setCellValue(pj.getBrOwnRef());
                row.getCell(15).setCellValue(pj.getBrOthersRef());
                row.getCell(16).setCellValue(pj.getBrandedNewspaper());
                row.getCell(17).setCellValue(pj.getBrandedTv());
                row.getCell(18).setCellValue(pj.getBrandedInternet());
                row.getCell(19).setCellValue(pj.getBrandedSign());
                row.getCell(20).setCellValue(pj.getBrandedMate());
                row.getCell(21).setCellValue(pj.getBrandedOthers());
                row.getCell(22).setCellValue(pj.getAgpInDirectMail());
                row.getCell(23).setCellValue(pj.getAgpInMailFlyer());
                row.getCell(24).setCellValue(pj.getAgpInHandFlyer());
                row.getCell(25).setCellValue(pj.getAgpInCp());
                row.getCell(26).setCellValue(pj.getAgpOutApoOut());
                row.getCell(27).setCellValue(pj.getAgpOutApoIn());
                row.getCell(28).setCellValue(pj.getAgpOutApoBlog());
                row.getCell(29).setCellValue(pj.getAgpOutApoBag());
                row.getCell(31).setCellValue(pj.getEnrollAch());
                row.getCell(32).setCellValue(pj.getEnrollMonthly());
                row.getCell(33).setCellValue(pj.getEnrollAllPrepay());
                row.getCell(34).setCellValue(pj.getExits());
            }
        }
        wb.removeSheetAt(1);
        wb.getCreationHelper().createFormulaEvaluator().evaluateAll();

        try {
            OutputStream fos = new FileOutputStream(target);
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            logger.error("", e);
        }
        return new FileSystemResource(target);
    }

    @RequestMapping(value = "/rest/PJ", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Long savePJ(@RequestBody PjSum pjSum) {
        logger.info("---savePJ---clubId: "+pjSum.getClubId()+", year: "+pjSum.getYear()+", month: "+pjSum.getMonth());
        PjSum pjx = pjSumRepo.findByClubIdAndYearAndMonth(pjSum.getClubId(), pjSum.getYear(), pjSum.getMonth());
        if (pjx != null) {
            logger.info("---savePJ---update");
            pjSum.setId(pjx.getId());
        }
        pjSum.setLastModified(new Date());
        pjSumRepo.save(pjSum);
        logger.info("---savePJ---saved with ID: " + pjSum.getId());
        return pjSum.getId();
    }
}
