package com.lavr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Comment")
@NamedQueries({
        @NamedQuery(name = "Comment.findCommentById",
                query = "select c from Comment c where c.comment_id = :comment_id"),
        @NamedQuery(name = "Comment.findAdComments",
                query = "select c from Comment c where c.ad = :ad"),
        @NamedQuery(name = "Comment.findUserComments",
                query = "select c from Comment c where c.user = :user"),
        @NamedQuery(name = "Comment.findNewUserComments",
                query = "select c from Comment c where c.ad.user = :user and c.isNew = true"),
})
public class Comment implements Serializable {
    private int comment_id;
    private User user;
    private Ad ad;
    private boolean isNew;
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull
    @Size(min = 3, message = "{comment.size}")
    private String text;

    @NotNull
    @Column(name = "IS_NEW")
    public boolean getIsNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "COMMENT_ID")
    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
    @OneToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "AD_ID")
    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    @Column(name = "TEXT")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
