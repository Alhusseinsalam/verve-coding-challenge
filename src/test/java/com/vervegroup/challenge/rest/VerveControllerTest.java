package com.vervegroup.challenge.rest;

import com.vervegroup.challenge.service.VerveService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@MockBean(VerveService.class)
public class VerveControllerTest {
    private final MockMvc mockMvc;
    private final VerveService verveService;

    @Test
    void sendInteger_withoutEndpoint() throws Exception {
        mockMvc.perform(get("/api/verve/accept")
                        .param("id", "1"))
                .andExpect(status().isOk());

        verify(verveService, times(1)).handleRequest(1);
        verify(verveService, times(0)).sendUniqueRequestCount(null);
    }

    @Test
    void sendInteger_withEndpoint() throws Exception {
        String endpoint = "http://example.com";
        mockMvc.perform(get("/api/verve/accept")
                        .param("id", "1")
                        .param("endpoint", endpoint))
                .andExpect(status().isOk());

        verify(verveService, times(1)).handleRequest(1);
        verify(verveService, times(1)).sendUniqueRequestCount(endpoint);
    }
}
