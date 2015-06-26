package com.curves.franchise.web;

import com.curves.franchise.domain.Authorities;
import com.curves.franchise.domain.Club;
import com.curves.franchise.domain.User;
import com.curves.franchise.repository.AuthoritiesRepository;
import com.curves.franchise.repository.ClubRepository;
import com.curves.franchise.repository.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ClubController {
    private final Logger logger = LoggerFactory.getLogger(ClubController.class);

    @Autowired
    private ClubRepository clubRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthoritiesRepository authoritiesRepo;

    @RequestMapping(value = "/rest/clubs", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(@RequestParam Integer clubId, @RequestParam String name, @RequestParam Date openDate, @RequestParam String owner) {
        logger.info("---saveUser---clubId: "+clubId+", name: "+name
                +", openDate: "+openDate+", owner: "+owner);
        Club club = new Club();
        club.setClubId(clubId);
        club.setName(name);
        club.setOpenDate(openDate);
        club.setOwner(owner);

        User user = new User();
        user.setUsername(clubId.toString());
        user.setPassword(new BCryptPasswordEncoder(8).encode(clubId.toString()));
        user.setEnabled(true);

        Authorities authorities = new Authorities();
        authorities.setUsername(clubId.toString());
        authorities.setAuthority("ROLE_USER");

        try {
            clubRepo.save(club);
            userRepo.save(user);
            authoritiesRepo.save(authorities);
        } catch (Exception e) {
            return null;
        }
        return "save success";
    }

    @RequestMapping("/rest/clubs/{clubId}")
    @ResponseBody
    public Club getClub(@PathVariable int clubId, @AuthenticationPrincipal UserDetails user) {
        logger.info("---getClub---"+clubId+", user: "+user.getUsername());
        return clubRepo.findOne(clubId == -1 ? Integer.parseInt(user.getUsername()) : clubId);
    }

    @RequestMapping(value = "/rest/clubs", method = RequestMethod.GET)
    public Page<Club> findClubs(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "sort_by", required = false, defaultValue = "clubId") String sortBy,
                                @RequestParam(value = "sort_asc", required = false, defaultValue = "true") Boolean isAsc) {
        logger.info("---findClubs---page: "+page+", sort_by: "+sortBy+", sort_asc: "+isAsc);
        Pageable pageable = new PageRequest(page - 1, 10, isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return clubRepo.findAll(pageable);
    }

    @RequestMapping(value = "/rest/users/init_coaches")
    public void initCoaches() {
        List<Club> clubs = clubRepo.findAll();
        List<User> users = new ArrayList<>(clubs.size());
        List<Authorities> auths = new ArrayList<>(clubs.size());
        for (Club club : clubs) {
            String clubId = String.valueOf(club.getClubId());
            User u = new User();
            u.setUsername("0" + clubId.substring(1));
            u.setPassword(new BCryptPasswordEncoder(8).encode(u.getUsername()));
            u.setEnabled(true);
            users.add(u);
            Authorities authorities = new Authorities();
            authorities.setUsername(u.getUsername());
            authorities.setAuthority("ROLE_COACH");
            auths.add(authorities);
        }
        userRepo.save(users);
        authoritiesRepo.save(auths);
    }

    @RequestMapping(value = "/rest/club/check_and_update")
    public void updateClub() {
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
