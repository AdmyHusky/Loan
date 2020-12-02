package com.digitalacademy.loan.controller;


import com.digitalacademy.loan.constants.LoanError;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import com.digitalacademy.loan.service.LoanService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {

    @Mock
    LoanService loanService;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanService);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test get loan info by id equals 1 should return loan information")
    @Test
    void testGetLoanInfoByIdEquals1() throws Exception{
        Long reqParam = 1L;

        LoanInfoModel loanInfoModel = new LoanInfoModel();
        loanInfoModel.setId(1L);
        loanInfoModel.setStatus("OK");
        loanInfoModel.setAccountPayable("x02-222-2200");
        loanInfoModel.setAccountReceivable("x02-333-3333");
        loanInfoModel.setPrincipalAmount(40000.00);

        when(loanService.getLoanInfoById(reqParam)).thenReturn(loanInfoModel);

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));
        JSONObject data = new JSONObject(resp.getString("data"));

        //status
        assertEquals("0",status.get("code").toString());
        assertEquals("success",status.get("message").toString());

        //data
        assertEquals(1,data.get("id"));
        assertEquals("OK",data.get("status"));
        assertEquals("x02-222-2200",data.get("account_payable"));
        assertEquals("x02-333-3333",data.get("account_receivable"));
        assertEquals(40000,data.get("principal_amount"));

    }

    @DisplayName("Test get loan info by id equals 2 should throw Loan Exception loan info not found")
    @Test
    void testGetLoanInfoByIdEquals2() throws Exception{
        Long reqParam = 2L;
        when(loanService.getLoanInfoById(reqParam)).thenThrow(
                new LoanException(LoanError.GET_LOAN_INFO_NOT_FOUND, HttpStatus.BAD_REQUEST)
        );

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        assertEquals("LOAN4002", status.get("code"));
        assertEquals("Loan information not found", status.get("message"));
    }

    @DisplayName("Test get loan info by id equals 3 should throw Loan Exception: Test throw new exception")
    @Test
    void testGetLoanInfoByIdEquals3() throws Exception{
        Long reqParam = 3L;
        when(loanService.getLoanInfoById(reqParam)).thenThrow(
                new Exception("Test throw new exception")
        );

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqParam))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        assertEquals("LOAN4001", status.get("code"));
        assertEquals("Cannot get loan information", status.get("message"));
    }


}
