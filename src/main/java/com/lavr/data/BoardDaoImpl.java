package com.lavr.data;

import com.lavr.entity.Ad;
import com.lavr.entity.Comment;
import com.lavr.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Transactional
@Repository
public class BoardDaoImpl implements BoardDao {
    private SessionFactory sessionFactory;
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public User findUserById(int id) {
        return (User) sessionFactory.getCurrentSession().
                getNamedQuery("User.findUserById")
                .setParameter("user_id", id).uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByLogin(String login) {
        return (User) sessionFactory.getCurrentSession()
                .getNamedQuery("User.findUserByLogin")
                .setParameter("login", login)
                .uniqueResult();
    }

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List getUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User c").list();
    }

    @Override
    public Ad saveAd(Ad ad) {
        sessionFactory.getCurrentSession().saveOrUpdate(ad);
        return ad;
    }

    @Override
    public Ad deleteAd(Ad ad) {
        sessionFactory.getCurrentSession().delete(ad);
        return ad;
    }

    @Override
    @Transactional(readOnly = true)
    public List getAds() {
        return sessionFactory.getCurrentSession().createQuery("from Ad c").list();
    }

    @Override
    public List getUsersAds(User user) {
        return sessionFactory.getCurrentSession()
                .getNamedQuery("Ad.findUsersAd")
                .setParameter("user", user)
                .list();
    }

    @Override
    public Ad findAdById(int id) {
        return (Ad) sessionFactory.getCurrentSession()
                .getNamedQuery("Ad.findAdById")
                .setParameter("ad_id", id)
                .uniqueResult();
    }


    @Override
    public List getCommentsByAd(Ad ad) {
        return sessionFactory.getCurrentSession()
                .getNamedQuery("Comment.findAdComments")
                .setParameter("ad", ad)
                .list();
    }

    @Override
    public List getCommentsByUser(User user) {
        return sessionFactory.getCurrentSession()
                .getNamedQuery("Comment.findUserComments")
                .setParameter("user", user)
                .list();
    }

    @Override
    public List getNewComments(User user) {
        return sessionFactory.getCurrentSession()
                .getNamedQuery("Comment.findNewUserComments")
                .setParameter("user", user)
                .list();
    }

    @Override
    public Comment getCommentById(int id) {
        return (Comment) sessionFactory.getCurrentSession()
                .getNamedQuery("Comment.findCommentById")
                .setParameter("comment_id", id)
                .uniqueResult();
    }

    @Override
    public Comment saveComment(Comment comment) {
        sessionFactory.getCurrentSession().saveOrUpdate(comment);
        return comment;
    }

    @Override
    public Comment deleteComment(Comment comment) {
        sessionFactory.getCurrentSession().delete(comment);
        return comment;
    }


    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        this.jdbcTemplate = jdbcTemplate;
    }
}
