package com.curves.franchise.web;

import com.curves.franchise.domain.PjSum;
import com.curves.franchise.repository.PjSumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class PjSumController {
    private Logger logger = LoggerFactory.getLogger(PjSumController.class);

    @Autowired
    private PjSumRepository pjSumRepo;

    @RequestMapping(value = "/rest/PJ", method = RequestMethod.GET)
    public PjSum findByUserAndYearAndMonth(@RequestParam("year") int year, @RequestParam("month") int month,
                                           @AuthenticationPrincipal UserDetails user) {
        logger.info("=== user: " + user.getUsername() + " get PJ - " + year + "." + (month+1));
        PjSum pjSum = pjSumRepo.findByClubIdAndYearAndMonth(Integer.parseInt(user.getUsername()), year, month);
        if (pjSum == null) {
            pjSum = new PjSum();
        }
        return pjSum;
    }

    @RequestMapping(value = "/rest/PJ", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Long savePJ(@RequestBody PjSum pjSum) {
        logger.info("--SavePJ--clubId: "+pjSum.getClubId()+", year: "+pjSum.getYear()+", month: "+pjSum.getMonth());
        PjSum pjx = pjSumRepo.findByClubIdAndYearAndMonth(pjSum.getClubId(), pjSum.getYear(), pjSum.getMonth());
        if (pjx != null) {
            pjSum.setId(pjx.getId());
        }
        pjSum.setLastModified(new Date());
        pjSumRepo.save(pjSum);
        logger.info("---PJ saved with ID: " + pjSum.getId());
        return pjSum.getId();
    }
}
