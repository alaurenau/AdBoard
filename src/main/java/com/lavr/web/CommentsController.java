package com.lavr.web;

import com.lavr.data.BoardDao;
import com.lavr.entity.Ad;
import com.lavr.entity.Comment;
import com.lavr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CommentsController {
    private BoardDao boardDao;

    @RequestMapping(value = "/comment/create", method = RequestMethod.POST)
    public String createComment(@Valid Comment comment, Errors errors, Model model,
                                @RequestParam("ad_id") int ad_id, Principal principal) {
        if (errors.hasErrors()) {
            model.addAttribute("id", ad_id);
            Ad ad = boardDao.findAdById(ad_id);
            List comments = boardDao.getCommentsByAd(ad);
            model.addAttribute("comments", comments);
            model.addAttribute(ad);
            return "ad";
        }
        User user = boardDao.findUserByLogin(principal.getName());
        Ad ad = boardDao.findAdById(ad_id);

        comment.setAd(ad);
        comment.setUser(user);
        comment.setIsNew(true);
        comment.setDate(new Date());
        boardDao.saveComment(comment);
        return "redirect:/ad?id=" + ad_id;
    }

    @RequestMapping(value = "/comment/delete", method = RequestMethod.POST)
    public String deleteComment(@RequestParam("comment_id") int comment_id,
                                @RequestParam("ad_id") int ad_id) {
        Comment comment = boardDao.getCommentById(comment_id);
        boardDao.deleteComment(comment);
        return "redirect:/ad?id=" + ad_id;
    }

    @RequestMapping(value = "/comment/markAsRead", method = RequestMethod.POST)
    public String markAsRead(Principal principal) {
        User user = boardDao.findUserByLogin(principal.getName());
        List<Comment> comments = boardDao.getNewComments(user);
        comments.forEach(comment -> {
            comment.setIsNew(false);
            boardDao.saveComment(comment);
        });
        return "redirect:/cabinet";
    }

    @Autowired
    public void setBoardDao(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
}
