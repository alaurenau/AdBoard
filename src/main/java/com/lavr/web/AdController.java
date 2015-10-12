package com.lavr.web;

import com.lavr.data.BoardDao;
import com.lavr.entity.Ad;
import com.lavr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@Controller
public class AdController {
    private BoardDao boardDao;

    @RequestMapping(value = "/ad/edit", method = RequestMethod.POST)
    public String editAd(@Valid Ad ad, Errors errors, Principal principal) {
        User user;
        Ad oldAd;

        if (errors.hasErrors()) {
            return "editAd";
        }
        if (ad.getAd_id() != 0) {
            //editing old ad
            oldAd = boardDao.findAdById(ad.getAd_id());
            user = oldAd.getUser();
        } else {
            //creating new ad
            user = boardDao.findUserByLogin(principal.getName());
        }

        ad.setUser(user);
        ad.setLastModified(new Date());
        boardDao.saveAd(ad);
        return "redirect:/cabinet";
    }

    @RequestMapping(value = "/ad/delete", method = RequestMethod.POST)
    public String deleteAd(@RequestParam("id") int id) {
        Ad ad = boardDao.findAdById(id);
        boardDao.deleteAd(ad);
        return "redirect:/cabinet";
    }

    @Autowired
    public void setBoardDao(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
}
