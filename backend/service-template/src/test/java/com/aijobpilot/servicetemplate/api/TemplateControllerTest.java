package com.aijobpilot.servicetemplate.api;

import com.aijobpilot.servicetemplate.config.CorrelationIdFilter;
import com.aijobpilot.servicetemplate.exception.ApiExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TemplateController.class)
@Import({ApiExceptionHandler.class, CorrelationIdFilter.class})
class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void echoesValidatedRequestAndReturnsCorrelationId() throws Exception {
        mockMvc.perform(post("/api/template/echo")
                        .header(CorrelationIdFilter.HEADER_NAME, "test-correlation-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"hello\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(CorrelationIdFilter.HEADER_NAME, "test-correlation-id"))
                .andExpect(jsonPath("$.message").value("hello"));
    }

    @Test
    void rejectsInvalidRequestWithStandardErrorPayload() throws Exception {
        mockMvc.perform(post("/api/template/echo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors.message").value("message is required"));
    }
}
