package com.lavr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "User")
@NamedQueries({
    @NamedQuery(name = "User.findUserById",
            query = "select c from User c where c.user_id = :user_id"),
    @NamedQuery(name = "User.findUserByLogin",
            query = "select c from User c where c.login = :login"),})

public class User implements Serializable {
    private int user_id;

    @NotNull
    @Size(min = 3, max = 30, message="{login.size}")
    private String login;

    @NotNull
    @Size(min = 2, max = 30, message = "{name.size}")
    private String name;

    @NotNull
    @Size(min = 2, max = 30, message = "{password.size}")
    private String password;

    @NotNull
    @Email(message = "{email.valid}")
    private String email;

    //private String code;
    private Set<Ad> ads = new HashSet<>();

    private String captcha;

    @Transient
    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID")
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Column(name = "LOGIN")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Ad> getAds() {
        return ads;
    }

    public void setAds(Set<Ad> ads) {
        this.ads = ads;
    }

    public static class UserExistsException extends Exception {
    }
}