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
        logger.info("=== user: " + user.getUsername() + " get PJ - " + year + "." + month);
        return pjSumRepo.findByClubIdAndYearAndMonth(Integer.parseInt(user.getUsername()), year, month);
    }

    @RequestMapping(value = "/rest/PJ", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String savePJ(@RequestBody PjSum pjSum) {
        logger.info("---save---clubId: "+pjSum.getClubId()+", year-month: "+pjSum.getYear()+"-"+pjSum.getMonth());
        PjSum x = pjSumRepo.save(pjSum);
        return "" + x.getId();
    }
}
