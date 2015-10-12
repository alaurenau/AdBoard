package com.lavr.web;

import com.lavr.data.BoardDao;
import com.lavr.entity.Ad;
import com.lavr.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AdControllerTest {
    private MockMvc mockAdController;
    @Mock
    private Principal mockPrincipal;
    @Mock
    private BoardDao mockBoardDao;
    @Mock
    private Ad mockAd;
    @Mock
    private User mockUser;

    @Before
    public void setUp() throws Exception {
        AdController controller = new AdController();
        controller.setBoardDao(mockBoardDao);
        mockAdController = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        when(mockBoardDao.findAdById(1)).thenReturn(mockAd);
    }

    @Test
    public void testEditAdNew() throws Exception {
        mockAdController.perform(post("/ad/edit")
                .principal(mockPrincipal)
                .param("body", "hello")
                .param("subject", "hello"))
                .andExpect(model().attributeHasNoErrors("ad"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/cabinet"));
        verify(mockBoardDao).findUserByLogin(mockPrincipal.getName());
        verify(mockBoardDao).saveAd(isA(Ad.class));
    }

    @Test
    public void testEditAdOld() throws Exception {
        mockAdController.perform(post("/ad/edit")
                .param("body", "hello")
                .param("subject", "hello")
                .param("ad_id", "1")
                .with(csrf()))
                .andExpect(model().attributeHasNoErrors("ad"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/cabinet"));
        verify(mockBoardDao).findAdById(1);
        verify(mockBoardDao).saveAd(isA(Ad.class));
    }

    @Test
    public void testEditAdError() throws Exception {
        mockAdController.perform(post("/ad/edit"))
                .andExpect(model().attributeHasErrors("ad"))
                .andExpect(status().is(200))
                .andExpect(view().name("editAd"));
        verifyZeroInteractions(mockBoardDao);
    }

    @Test
    public void testDeleteAd() throws Exception {
        mockAdController.perform(post("/ad/delete")
                .param("id", "1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/cabinet"));
        verify(mockBoardDao).deleteAd(mockAd);
    }

    @Test
    public void testDeleteAdFail() throws Exception {
        mockAdController.perform(post("/ad/delete"))
                .andExpect(mvcResult ->
                        assertEquals(mvcResult.getResponse().getErrorMessage(),
                                "Required int parameter 'id' is not present"))
                .andExpect(status().is(400));
        verifyZeroInteractions(mockBoardDao);
    }
}