package com.curves.franchise.web;

import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.ClubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClubController {
    private Logger logger = LoggerFactory.getLogger(PjSumController.class);

    @Autowired
    ClubRepository clubRepo;

    @RequestMapping(value = "/rest/clubs", method = RequestMethod.GET)
    public Iterable<Club> getClubs() {
        return clubRepo.findAll();
    }
}
