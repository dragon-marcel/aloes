package com.example.Client.EmailTest;

import com.example.Client.controller.VisitController;
import com.example.Client.domain.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@RunWith(MockitoJUnitRunner.class)
public class EmailTest {
    @Mock
    EmailSender emailSender;

    @InjectMocks
    VisitController visitController;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }
    @Test
   public void emialSendeTest() throws Exception {

       Mockito.when(emailSender.sendEmail(Matchers.anyString(), Matchers.anyString(),Matchers.anyString())).thenReturn(true);
   }

}
