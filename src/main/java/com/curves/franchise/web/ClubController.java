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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Club> findClubs(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "sort_by", required = false, defaultValue = "clubId") String sortBy,
                                @RequestParam(value = "sort_asc", required = false, defaultValue = "true") Boolean isAsc) {
        logger.info("---findClubs---page: "+page+", sort_by: "+sortBy+", sort_asc: "+isAsc);
        Pageable pageable = new PageRequest(page - 1, 10, isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return clubRepo.findAll(pageable);
    }

    @RequestMapping(value = "/rest/club/check_and_update")
    public void updateClub() throws Exception {
        Workbook wb;
        try {
            String home = System.getProperty("user.home");
            String file = home + File.separator + "curves_data" + File.separator + "Club_number_Names-1.xlsx";
            logger.info("file: " + file);
            wb = WorkbookFactory.create(new File(file));
            Sheet sh = wb.getSheetAt(0);
            for (int i = 1; i < 91; i++) {
                Row row = sh.getRow(i);
                String name = row.getCell(1).getStringCellValue();
                String owner = row.getCell(2).getStringCellValue();
                Date openDate = row.getCell(5).getDateCellValue();
                try {
                    int clubId = (int)row.getCell(4).getNumericCellValue();
                    logger.info("===row-["+i+"] in file, name: " + name + ", owner: " + owner + ", clubId: " + clubId + ", date: " + openDate);
                    Club club = clubRepo.findOne(clubId);
                    if (club == null) {
                        throw new Exception();
                    }

                    logger.info("===club in dB, name: " + club.getName() + ", owner: " + club.getOwner() + ", date: " + club.getOpenDate());

                    if (!name.equals(club.getName())) {
                        logger.info("===update club xzy=== name not equal");
                    }
                    if (!openDate.equals(club.getOpenDate())) {
                        logger.info("===update club xzy=== date not equal");
                    }
                    if (!owner.equals(club.getOwner())) {
                        logger.info("===update club xzy=== owner not equal");
                    }
                    club.setOpenDate(openDate);
                    club.setOwnerEn(owner);
                    clubRepo.save(club);
                } catch (Exception e) {
                    int clubId = (int)row.getCell(4).getNumericCellValue();
                    logger.info("==create new club==row-["+i+"], clubId: "+clubId+", name: " + name + ", owner: " + owner + ", date: " + openDate);
                    Club club = new Club();
                    club.setName(name);
                    club.setOwner(owner);
                    club.setOpenDate(openDate);
                    club.setClubId(clubId);
                    clubRepo.save(club);
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
