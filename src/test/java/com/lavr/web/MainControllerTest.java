package com.lavr.web;

import com.lavr.data.BoardDaoImpl;
import com.lavr.entity.Ad;
import com.lavr.entity.Comment;
import com.lavr.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {
    @Mock
    Model model;
    @Mock
    RedirectAttributes redirectAttrs;
    @Mock
    SecurityContextHolderAwareRequestWrapper request;
    @Mock
    BoardDaoImpl boardDao;
    @Mock
    Principal principal;
    @Mock
    User user;
    @Mock
    Ad ad;
    MainController controller;

    @Before
    public void setUp() throws NamingException, SQLException {
        controller = new MainController();
        controller.setBoardDao(boardDao);
    }

    @Test
    public void mainTest() {
        when(boardDao.getAds()).thenReturn(new ArrayList<>());
        String viewName = controller.main(model);
        assertTrue(model.asMap().isEmpty());
        assertEquals("index", viewName);
    }

    @Test
    public void mainAdTest() {
        List<Ad> list = new ArrayList<>();
        list.add(ad);
        when(boardDao.getAds()).thenReturn(list);
        String viewName = controller.main(model);
        verify(model).addAttribute("adList", boardDao.getAds());
        assertEquals("index", viewName);
    }

    @Test
    public void registerTest() {
        String viewName = controller.register(model);
        verify(model).addAttribute(isA(User.class));
        assertEquals("register", viewName);
    }

    @Test
    public void registerFormTest() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        controller.setRequest(req);
        HttpSession session = mock(HttpSession.class);
        Errors errors = mock(Errors.class);
        when(req.getSession()).thenReturn(session);
        when(errors.hasErrors()).thenReturn(false);
        String viewName = controller.registerForm(user, errors, redirectAttrs);
        verify(boardDao).addUser(user);
        verify(redirectAttrs).addFlashAttribute("message",
                "Вы успешно зарегистрировались теперь можете войти под своим логином!");
        assertEquals("redirect:/", viewName);
    }

    @Test
    public void registerFormFalseCaptchaTest() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        controller.setRequest(req);
        HttpSession session = mock(HttpSession.class);
        Errors errors = mock(Errors.class);
        when(req.getSession()).thenReturn(session);
        when(errors.hasErrors()).thenReturn(true);
        String viewName = controller.registerForm(user, errors, redirectAttrs);
        verify(req, atLeast(1)).getParameter("captcha");
        verify(session, atLeast(1)).getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        assertEquals("register", viewName);
    }

    @Test
    public void registerFormExistingLoginTest() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        controller.setRequest(req);
        HttpSession session = mock(HttpSession.class);
        Errors errors = mock(Errors.class);
        when(req.getSession()).thenReturn(session);
        when(errors.hasErrors()).thenReturn(false);
        when((boardDao.findUserByLogin(user.getLogin()))).thenReturn(user);
        String viewName = controller.registerForm(user, errors, redirectAttrs);
        verify(redirectAttrs).addFlashAttribute("error",
                "Пользователь с таким логином уже существует!");
        assertEquals("redirect:/", viewName);
    }

    @Test
    public void cabinetAdminTest() {
        when(request.isUserInRole("ADMIN")).thenReturn(true);
        String viewName = controller.cabinet(principal, model, request);
        verify(boardDao).getAds();
        assertEquals("cabinet", viewName);
    }

    @Test
    public void cabinetUserTest() {
        when(request.isUserInRole("ADMIN")).thenReturn(false);
        when(principal.getName()).thenReturn("User");
        when(boardDao.findUserByLogin(principal.getName())).thenReturn(user);
        String viewName = controller.cabinet(principal, model, request);
        verify(boardDao).getUsersAds(user);
        verify(model).addAttribute("newComments", boardDao.getNewComments(user));
        assertEquals("cabinet", viewName);
    }

    @Test
    public void editAdTest() throws Exception {
        when(boardDao.findUserByLogin(principal.getName())).thenReturn(user);
        String viewName = controller.editAd(0, model, principal, redirectAttrs, request);
        verify(model).addAttribute(isA(Ad.class));
        verify(model).addAttribute("title", "Создаётся");
        assertEquals("editAd", viewName);
    }

    @Test
    public void editAdAdminTest() throws Exception {
        when(request.isUserInRole("ADMIN")).thenReturn(true);
        when(boardDao.findAdById(anyInt())).thenReturn(ad);
        String viewName = controller.editAd(1, model, principal, redirectAttrs, request);
        verify(model).addAttribute(ad);
        verify(model).addAttribute("title", "Редактируется");
        assertEquals("editAd", viewName);
    }

    @Test
    public void editUserTest() throws Exception {
        when(request.isUserInRole("ADMIN")).thenReturn(false);
        when(boardDao.findAdById(anyInt())).thenReturn(ad);
        User user1 = new User();
        user1.setUser_id(1);
        when(ad.getUser()).thenReturn(user1);
        when(boardDao.findUserByLogin(principal.getName())).thenReturn(user);
        when(user.getUser_id()).thenReturn(1);
        String viewName = controller.editAd(1, model, principal, redirectAttrs, request);
        verify(model).addAttribute(ad);
        verify(model).addAttribute("title", "Редактируется");
        assertFalse(redirectAttrs.getFlashAttributes().containsKey("error"));
        assertEquals("editAd", viewName);
    }

    @Test
    public void editFalseUserTest() throws Exception {
        when(request.isUserInRole("ADMIN")).thenReturn(false);
        when(boardDao.findAdById(anyInt())).thenReturn(ad);
        User user1 = new User();
        user1.setUser_id(1);
        when(ad.getUser()).thenReturn(user1);
        when(boardDao.findUserByLogin(principal.getName())).thenReturn(user);
        when(user.getUser_id()).thenReturn(2);
        String viewName = controller.editAd(1, model, principal, redirectAttrs, request);
        assertTrue(model.asMap().isEmpty());
        verify(redirectAttrs).addFlashAttribute("error", "Это не ваше сообщение!");
        assertEquals("redirect:/", viewName);
    }

    @Test
    public void adTest() {
        when(boardDao.findAdById(anyInt())).thenReturn(ad);
        String viewName = controller.ad(anyInt(), model);
        assertEquals("ad", viewName);
        verify(model).addAttribute(eq("comment"), isA(Comment.class));
        verify(model).addAttribute("comments", boardDao.getCommentsByAd(ad));
        verify(model).addAttribute("id", eq(anyInt()));
        verify(model).addAttribute(ad);
    }
}