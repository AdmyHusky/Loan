package com.digitalacademy.loan.service;

import com.digitalacademy.loan.constants.LoanError;
import com.digitalacademy.loan.controller.LoanController;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private static final Logger log =
            LogManager.getLogger(LoanController.class.getName());

    public LoanInfoModel getLoanInfoById(Long id)throws  Exception{
        LoanInfoModel loanInfoModel = new LoanInfoModel();

        if(id.equals(1L)){
            log.info("Get loan info by id: {}", id);
            loanInfoModel.setId(1L);
            loanInfoModel.setStatus("OK");
            loanInfoModel.setAccountPayable("102-111-2233");
            loanInfoModel.setAccountReceivable("103-111-4455");
            loanInfoModel.setPrincipalAmount(3000.00);
        }else if (id.equals(2L)){
            log.error("Id: {}",id);
            throw new LoanException(
                    LoanError.GET_LOAN_INFO_NOT_FOUND,
                    HttpStatus.BAD_REQUEST
            );
        }else {
            log.error("Id: {}", id);
            throw  new Exception("Test throw new exception");
        }
        return loanInfoModel;

    }
}
