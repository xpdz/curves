package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CaController {
    private final Logger logger = LoggerFactory.getLogger(CaController.class);

    @Autowired
    private CaRepository caRepo;

    @Autowired
    private ClubRepository clubRepo;

    @RequestMapping(value = "/rest/CA/lastMonth", method = RequestMethod.GET)
    public Map<String, String> findGoalByYearAndMonth(@RequestParam int clubId,
                                                      @RequestParam int caYear, @RequestParam int caMonth) {
        logger.info("==find last month goal== clubId: " + clubId + ", year-month: " + caYear + "." + (caMonth+1));
        Map<String, String> mapGoalValue = new HashMap<>(4);
        Ca ca = caRepo.findByClubIdAndCaYearAndCaMonth(clubId, caYear, caMonth);
        if (ca != null) {
            mapGoalValue.put("svcTm6", ""+ca.getSvcTm6());
            mapGoalValue.put("svcActive6", ""+ca.getSvcActive6());
            mapGoalValue.put("cmShowRatio6", Math.round(ca.getCmShowRatio6()*100)+"%");
            mapGoalValue.put("salesRatio6", Math.round(ca.getSalesRatio6()*100)+"%");
            mapGoalValue.put("nextPlan", ca.getNextPlan());
        }
        return mapGoalValue;
    }

    @RequestMapping(value = "/rest/CA", method = RequestMethod.GET)
    public Ca findCaByUserAndCaYearAndCaMonth(@RequestParam int clubId, @RequestParam int caYear, @RequestParam int caMonth) {
        logger.info("---findCA---clubId: " + clubId + ", caYear: " + caYear + ", caMonth: " + caMonth);
        Ca ca = caRepo.findByClubIdAndCaYearAndCaMonth(clubId, caYear, caMonth);
        if (ca == null) {
            logger.info("---findCA---CA not found!");
            ca = new Ca();
        }
        return ca;
    }

    @RequestMapping(value = "/rest/CA/export", produces="application/vnd.ms-excel")
    public FileSystemResource exportCa(@RequestParam int clubId, @RequestParam int yStart, @RequestParam int yEnd,
                                       @RequestParam int mStart, @RequestParam int mEnd) {
        logger.info("---exportCA---clubId; " + clubId + ", yStart: " + yStart
                + ", yEnd: " + yEnd+", mStart: " + mStart + ", mEnd: "+mEnd);
        Club club = clubRepo.findOne(clubId);
        List<Ca> cas = caRepo.findByClubIdAndCaYearBetweenAndCaMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
        Workbook wb;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "CA-template.xls");
        File target;
        try {
            target = File.createTempFile("CA-export", "xls");
            FileUtils.copyFile(template, target);
            wb = WorkbookFactory.create(new FileInputStream(target));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        ExportUtil.fillCaSheet(club, cas, wb);

        wb.removeSheetAt(2);
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

    @RequestMapping(value = "/rest/CA", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Long saveCA(@RequestBody Ca ca) {
        logger.info("--SaveCA--clubId: "+ca.getClubId()+" caYear: "+ca.getCaYear()+", caMonth: "+ca.getCaMonth());
        Ca cax = caRepo.findByClubIdAndCaYearAndCaMonth(ca.getClubId(), ca.getCaYear(), ca.getCaMonth());
        if (cax != null) {
            logger.info("---savePJ---update");
            ca.setId(cax.getId());
        }
        ca.setLastModified(new Date());
        caRepo.save(ca);
        logger.info("---saveCA---saved with ID: "+ca.getId());
        return ca.getId();
    }
}
