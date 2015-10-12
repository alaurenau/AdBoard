package com.lavr.web;

import com.google.code.kaptcha.Constants;
import com.lavr.data.BoardDao;
import com.lavr.entity.Ad;
import com.lavr.entity.Comment;
import com.lavr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class MainController {
    private BoardDao boardDao;
    private HttpServletRequest request;

    @RequestMapping(value = "/", method = GET)
    public String main(Model model) {
        List<Ad> list = boardDao.getAds();

        if (!list.isEmpty()) {
            model.addAttribute("adList", boardDao.getAds());
        }
        return "index";
    }

    @RequestMapping(value = "/register", method = GET)
    public String register(Model model) {
        model.addAttribute(new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = POST)
    public String registerForm(@Valid User user, Errors errors, RedirectAttributes model) {
        String kaptchaExpected = (String) request.getSession()
                .getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String kaptchaReceived = request.getParameter("captcha");

        if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
            errors.rejectValue("captcha", "captcha.wrong");
        }
        if (errors.hasErrors()) {
            return "register";
        }
        //check for existing user
        if (boardDao.findUserByLogin(user.getLogin()) != null) {
            model.addFlashAttribute("error", "Пользователь с таким логином уже существует!");
            return "redirect:/";
        }
        boardDao.addUser(user);
        model.addFlashAttribute("message", "Вы успешно зарегистрировались теперь можете войти под своим логином!");
        return "redirect:/";
    }

    @RequestMapping(value = "/cabinet", method = GET)
    public String cabinet(Principal principal, Model model, SecurityContextHolderAwareRequestWrapper request) {
        List list;
        if (request.isUserInRole("ADMIN")) {
            list = boardDao.getAds();
        } else {
            User user = boardDao.findUserByLogin(principal.getName());
            list = boardDao.getUsersAds(user);
            List newComments = boardDao.getNewComments(user);
            model.addAttribute("newComments", newComments);
        }

        if (!list.isEmpty()) {
            model.addAttribute("adList", list);
        }

        return "cabinet";
    }

    @RequestMapping(value = "/editAd", method = GET)
    public String editAd(@RequestParam(value = "id", defaultValue = "0") int id,
            Model model, Principal principal,
            RedirectAttributes redirectAttrs,
            SecurityContextHolderAwareRequestWrapper request) {
        Ad ad;
        User user;
        if (id == 0) {
            ad = new Ad();
            user = boardDao.findUserByLogin(principal.getName());
            ad.setUser(user);
            model.addAttribute(ad);
            model.addAttribute("title", "Создаётся");
        } else {
            if (request.isUserInRole("ADMIN")) {
                ad = boardDao.findAdById(id);
            } else {
                ad = boardDao.findAdById(id);
                user = boardDao.findUserByLogin(principal.getName());

                if (ad.getUser().getUser_id() != user.getUser_id()) {
                    redirectAttrs.addFlashAttribute("error", "Это не ваше сообщение!");
                    return "redirect:/";
                }
            }
            model.addAttribute(ad);
            model.addAttribute("title", "Редактируется");
        }
        return "editAd";
    }

    @RequestMapping(value = "/ad")
    public String ad(@RequestParam(value = "id") int id, Model model) {

        Ad ad = boardDao.findAdById(id);
        List comments = boardDao.getCommentsByAd(ad);

        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", comments);
        model.addAttribute("id", id);
        model.addAttribute(ad);
        return "ad";
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setBoardDao(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
}