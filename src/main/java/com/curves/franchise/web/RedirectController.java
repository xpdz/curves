package com.curves.franchise.web;

import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.ClubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class RedirectController {
    private Logger logger = LoggerFactory.getLogger(RedirectController.class);

    @Autowired
    ClubRepository clubRepo;

    @RequestMapping(value = "/loginSuccess", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginSuccess(@AuthenticationPrincipal UserDetails user) {
        logger.info("----YJY---- user: " + user.getUsername()+", pwd: " + user.getPassword());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority auth : authorities) {
            String role = auth.getAuthority();
            logger.info("----YJY---- role: " + role);
            if ("ROLE_ADMIN".equals(role)) {
                return "redirect:/management.htm";
            } else if ("ROLE_USER".equals(role)) {
                return "redirect:/PJ.htm";
            }
        }
        return "redirect:index.htm";
    }

    @RequestMapping("/rest/whoami")
    public @ResponseBody Club whoAmI(@AuthenticationPrincipal UserDetails user) {
        Club club = clubRepo.findOne(Integer.parseInt(user.getUsername()));
        return club;
    }

    @RequestMapping("/errorMsg")
    public String loginError() {
        logger.info("----YJY----error");
        return "error";
    }
}
