package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.repository.CaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CaController {
    private Logger logger = LoggerFactory.getLogger(CaController.class);

    @Autowired
    CaRepository caRepo;

    @RequestMapping(value = "/rest/CA/lastMonth", method = RequestMethod.GET)
    public Map<String, String> findGoalByYearAndMonth(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth,
                                           @AuthenticationPrincipal UserDetails user) {
        logger.info("==find last month goal== user: " + user.getUsername() + " get CA - " + caYear + "." + (caMonth+1));
        Map<String, String> mapGoalValue = new HashMap<>(4);
        Ca ca = caRepo.findByClubIdAndCaYearAndCaMonth(Integer.parseInt(user.getUsername()), caYear, caMonth);
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
    public Ca findByUserAndCaYearAndCaMonth(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth,
                                            @AuthenticationPrincipal UserDetails user) {
        logger.info("=== user: " + user.getUsername() + " get CA - " + caYear + "." + (caMonth+1));
        Ca ca = caRepo.findByClubIdAndCaYearAndCaMonth(Integer.parseInt(user.getUsername()), caYear, caMonth);
        if (ca == null) {
            ca = new Ca();
        }
        return ca;
    }

    @RequestMapping(value = "/rest/CA", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Long saveCA(@RequestBody Ca ca) {
        logger.info("--SaveCA--ID:"+ca.getId()+", club: "+ca.getClubId()+" @ "+ca.getCaYear()+"-"+(ca.getCaMonth()+1)+", plan:"+ca.getThisPlan());
        caRepo.save(ca);
        return ca.getId();
    }
}
