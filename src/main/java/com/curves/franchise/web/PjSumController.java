package com.curves.franchise.web;

import com.curves.franchise.domain.PjSum;
import com.curves.franchise.repository.PjSumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        return pjSum;
    }

    @RequestMapping(value = "/rest/PJ", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String savePJ(@RequestBody PjSum pjSum) {
        logger.info("--SavePJ--ID:"+pjSum.getId()+", club: "+pjSum.getClubId()+"@"+pjSum.getYear()+"-"+(pjSum.getMonth()+1));
        pjSumRepo.save(pjSum);
        return "" + pjSum.getId();
    }
}
