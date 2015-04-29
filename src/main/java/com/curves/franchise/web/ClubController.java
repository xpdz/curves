package com.curves.franchise.web;

import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;

@RestController
public class ClubController {
    private Logger logger = LoggerFactory.getLogger(ClubController.class);

    @Autowired
    ClubRepository clubRepo;

    @Autowired
    CaRepository caRepo;

    @RequestMapping("/rest/clubs/{clubId}")
    @ResponseBody
    public Club getClub(@PathVariable int clubId, @AuthenticationPrincipal UserDetails user) {
        logger.info("---getClub---"+clubId+", user: "+user.getUsername());
        return clubRepo.findOne(clubId == -1 ? Integer.parseInt(user.getUsername()) : clubId);
    }

    @RequestMapping(value = "/rest/clubs")
    public Iterable<Club> findAllClubs() {
        logger.info("---findAllClubs---");
        return clubRepo.findAll();
    }

    @RequestMapping(value = "/rest/club/date/update")
    public void updateClub() throws Exception {
        Workbook wb = null;
        try {
            Iterable<Club> clubs = clubRepo.findAll();
            String home = System.getProperty("user.home");
            String file = home + File.separator + "curves_data" + File.separator + "Workbook3.xlsx";
            logger.info("file: " + file);
            wb = WorkbookFactory.create(new File(file));
            Sheet sh = wb.getSheetAt(0);
            for (int i = 2; i < 89; i++) {
                Row row = sh.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                String owner = row.getCell(1).getStringCellValue();
                Date date = row.getCell(2).getDateCellValue();
                while (clubs.iterator().hasNext()) {
                    Club club = clubs.iterator().next();
                    if (club.getName().equals(name)) {
                        club.setOpenDate(date);
                        club.setOwnerEn(owner);
                        clubRepo.save(club);
                        break;
                    }
                }
            }
            try {
                wb.close();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
