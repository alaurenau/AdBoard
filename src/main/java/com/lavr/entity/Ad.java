package com.lavr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Ad")
@NamedQueries({
        @NamedQuery(name = "Ad.findAdById",
                query = "select c from Ad c where c.ad_id = :ad_id"),
        @NamedQuery(name = "Ad.findUsersAd",
                query = "select c from Ad c where c.user = :user"),
})
public class Ad implements Serializable{
    private int ad_id;

    @NotNull
    @Size(min=3, max=100, message="{subject.size}")
    private String subject;

    @NotNull
    @Size(min=3, max=500, message="{body.size}")
    private String body;

    private User user;
    private Date lastModified;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "AD_ID")
    public int getAd_id() {
        return ad_id;
    }

    public void setAd_id(int ad_id) {
        this.ad_id = ad_id;
    }

    @Column(name = "SUBJECT")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "BODY")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED")
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
