package com.lavr.data;

import com.lavr.entity.Ad;
import com.lavr.entity.Comment;
import com.lavr.entity.User;

import java.util.List;

public interface BoardDao {

    List getUsers();
    User findUserById(int id);
    User findUserByLogin(String login);
    void addUser(User user);

    List getAds();
    List getUsersAds(User user);
    Ad findAdById(int id);
    Ad saveAd(Ad ad);
    Ad deleteAd(Ad ad);

    List getCommentsByAd(Ad ad);
    List getCommentsByUser(User user);
    List getNewComments(User user);
    Comment getCommentById(int id);
    Comment saveComment(Comment comment);
    Comment deleteComment(Comment comment);
}
