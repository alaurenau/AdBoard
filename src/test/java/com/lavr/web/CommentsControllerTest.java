package com.lavr.web;

import com.lavr.data.BoardDao;
import com.lavr.entity.Ad;
import com.lavr.entity.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentsControllerTest {
    private MockMvc mockAdController;
    @Mock
    private Ad mockAd;
    @Mock
    private Comment mockComment;
    @Mock
    private BoardDao mockBoardDao;
    @Mock
    private Principal mockPrincipal;

    @Before
    public void setUp() throws Exception {
        CommentsController controller = new CommentsController();
        controller.setBoardDao(mockBoardDao);
        mockAdController = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void testCreateCommentOk() throws Exception {
        mockAdController.perform(post("/comment/create")
                .principal(mockPrincipal)
                .param("ad_id", "42")
                .param("text", "hello"))
                .andExpect(model().attributeHasNoErrors("comment"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/ad?id=42"));
        verify(mockBoardDao).saveComment(isA(Comment.class));
    }

    @Test
    public void testCreateCommentError() throws Exception {
        when(mockBoardDao.findAdById(1)).thenReturn(mockAd);
        mockAdController.perform(post("/comment/create")
                .param("ad_id", "1"))
                .andExpect(model().attributeHasErrors("comment"))
                .andExpect(status().is(200))
                .andExpect(view().name("ad"))
                .andExpect(model().attribute("ad", mockAd));
    }

    @Test
    public void testCreateCommentFail() throws Exception {
        mockAdController.perform(post("/comment/create"))
                .andExpect(status().is(400))
                .andExpect(mvcResult ->
                        assertEquals(mvcResult.getResponse().getErrorMessage(),
                                "Required int parameter 'ad_id' is not present"));
        verifyZeroInteractions(mockBoardDao);
    }

    @Test
    public void testDeleteComment() throws Exception {
        when(mockBoardDao.getCommentById(3)).thenReturn(mockComment);
        mockAdController.perform(post("/comment/delete")
                .param("ad_id", "42")
                .param("comment_id", "3"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/ad?id=42"));
        verify(mockBoardDao).deleteComment(mockComment);
    }

    @Test
    public void testDeleteCommentFail() throws Exception {
        mockAdController.perform(post("/comment/delete"))
                .andExpect(status().is(400))
                .andExpect(mvcResult ->
                        assertEquals(mvcResult.getResponse().getErrorMessage(),
                                "Required int parameter 'comment_id' is not present"));
        verifyZeroInteractions(mockBoardDao);
    }

    @Test
    public void testMarkAsRead() throws Exception {
        mockAdController.perform(post("/comment/markAsRead")
                .principal(mockPrincipal))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/cabinet"));
        verify(mockBoardDao).findUserByLogin(mockPrincipal.getName());
    }
}