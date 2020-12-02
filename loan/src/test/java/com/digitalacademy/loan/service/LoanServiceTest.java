package com.digitalacademy.loan.service;

import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import com.digitalacademy.loan.service.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanServiceTest {

    @InjectMocks
    LoanService loanService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService();
    }

    @DisplayName("Test get loan info by id equals 1 should return loan information")
    @Test
    void testGetLoanInfoByIdEquals1() throws Exception{
        LoanInfoModel resp = loanService.getLoanInfoById(1L);

        assertEquals(1,1);
        assertEquals("1", resp.getId().toString());
        assertEquals("OK", resp.getStatus());
        assertEquals("102-111-2233", resp.getAccountPayable());
        assertEquals("103-111-4455", resp.getAccountReceivable());
        assertEquals(3000.00, resp.getPrincipalAmount());
    }

    @DisplayName("Test get loan info by id equals 2 should throw Loan Exception loan info not found")
    @Test
    void testGetLoanInfoByIdEquals2() throws Exception{
        Long reqParam = 2L;
        LoanException thrown = assertThrows(LoanException.class,
                () -> loanService.getLoanInfoById(reqParam),
                "Expected loanInfoById(reqParam to throw, but it didn't");
        assertEquals(400, thrown.getHttpStatus().value());
        Assertions.assertEquals("LOAN4002", thrown.getLoanError().getCode());
        Assertions.assertEquals("Loan information not found", thrown.getLoanError().getMessage());
    }

    @DisplayName("Test get loan info by id equals 3 should throw Exception: Test throw new exception")
    @Test
    void testGetLoanInfoByIdEqual3() throws Exception{
        Long reqParam = 3L;
        Exception thrown = assertThrows(Exception.class,
                () -> loanService.getLoanInfoById(reqParam),
                "Expected loanInfoById(reqParam) to throw, but it didn't");
        assertEquals("Test throw new exception", thrown.getMessage());
    }
}
