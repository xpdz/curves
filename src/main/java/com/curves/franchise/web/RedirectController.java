package com.curves.franchise.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class RedirectController {
    private Logger logger = LoggerFactory.getLogger(RedirectController.class);

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

    @RequestMapping("/errorMsg")
    public String loginError() {
        logger.info("----YJY----error");
        return "error";
    }
//    @RequestMapping("/error")
//    public Map<String, String> loginError() {
//        Map<String, String> map = new HashMap<String, String>(1);
//        map.put("error", "error");
//        return map;
//    }
}
