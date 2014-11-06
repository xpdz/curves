package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.repository.CaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CaController {
    private Logger logger = LoggerFactory.getLogger(PjSumController.class);

    @Autowired
    CaRepository caRepo;

    @RequestMapping(value = "/rest/CA", method = RequestMethod.GET)
    public Ca findByUserAndCaYearAndCaMonth(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth,
                                           @AuthenticationPrincipal UserDetails user) {
        logger.info("=== user: " + user.getUsername() + " get CA - " + caYear + "." + (caMonth+1));
        Ca ca = caRepo.findByClubIdAndCaYearAndCaMonth(Integer.parseInt(user.getUsername()), caYear, caMonth);
        return ca;
    }

    @RequestMapping(value = "/rest/CA", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String saveCA(@RequestBody Ca ca) {
        logger.info("--SaveCA--ID:"+ca.getId()+", club: "+ca.getClubId()+" @ "+ca.getCaYear()+"-"+(ca.getCaMonth()+1));
        caRepo.save(ca);
        return "" + ca.getId();
    }
}
