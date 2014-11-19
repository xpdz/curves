package com.curves.franchise.web;

import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import com.curves.franchise.repository.PjSumRepository;
import com.curves.franchise.util.CurvesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class RedirectController {
    private Logger logger = LoggerFactory.getLogger(RedirectController.class);

    @Autowired
    ClubRepository clubRepo;

    @Autowired
    private CaRepository caRepo;

    @Autowired
    private PjSumRepository pjSumRepo;

    @RequestMapping(value = "/loginSuccess", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginSuccess(@AuthenticationPrincipal UserDetails user) {
        logger.info("User: " + user.getUsername() + " logged in.");
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority auth : authorities) {
            String role = auth.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                return "redirect:/management.htm";
            } else if ("ROLE_USER".equals(role)) {
                return "redirect:/PJ.htm";
            }
        }
        return "redirect:index.htm";
    }

    @RequestMapping(value = "/showTrends")
    public String showTrends(@RequestParam("clubId") int clubId) {
        return "redirect:trends.htm?clubId=" + clubId;
    }

    @RequestMapping("/rest/whoami")
    public @ResponseBody Club whoAmI(@AuthenticationPrincipal UserDetails user) {
        return clubRepo.findOne(Integer.parseInt(user.getUsername()));
    }

    @RequestMapping("/errorMsg")
    public String loginError() {
        logger.info("----YJY----error");
        return "error";
    }

    @RequestMapping(value = "/rest/data", method = RequestMethod.GET)
    public void processData(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("dir") String dir) throws Exception {
        new CurvesParser(pjSumRepo, caRepo, year, month, dir).processAll();
    }
}
