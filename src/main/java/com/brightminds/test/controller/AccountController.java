package com.brightminds.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brightminds.test.configuration.constants.AccountParamKeys;
import com.brightminds.test.controller.util.AccountParamsValidator;
import com.brightminds.test.controller.util.bean.HashedStatement;
import com.brightminds.test.controller.util.bean.ValidatorResult;
import com.brightminds.test.dao.bean.Statement;
import com.brightminds.test.service.AccountService;

@RestController
public class AccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/admin/statement/{accountId}")
    public ResponseEntity getStatements(@PathVariable String accountId, @RequestParam(name = "from_date", required = false) String fromDate, @RequestParam(name = "to_date", required = false) String toDate
        , @RequestParam(name = "from_amount", required = false) String fromAmount, @RequestParam(name = "to_amount", required = false) String toAmount) {

        Map<String, String> params = new HashMap<>();

        params.put(AccountParamKeys.FROM_DATE.getKey(), fromDate);
        params.put(AccountParamKeys.TO_DATE.getKey(), toDate);
        params.put(AccountParamKeys.FROM_AMOUNT.getKey(), fromAmount);
        params.put(AccountParamKeys.TO_AMOUNT.getKey(), toAmount);

        Map<String, String> sanitizedParams = sanitizeNullValues(params);

        ResponseEntity response;

        ValidatorResult result = AccountParamsValidator.validate(sanitizedParams);
        if (result.isValid()) {
            List<HashedStatement> statements;
            try {
                if (sanitizedParams.isEmpty()) {
                    statements = toHashedStatements(accountService.getLast3MonthsStatements(accountId));
                } else {
                    statements = toHashedStatements(accountService.getStatements(accountId, sanitizedParams));
                }
                response = new ResponseEntity(statements, HttpStatus.OK);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
                response = new ResponseEntity("Service not available.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response = new ResponseEntity(result.getErrors(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    private void validate(Map<String, String> params) {
    }

    @GetMapping("/statement/{accountId}")
    public ResponseEntity getLast3MonthsStatements(@PathVariable String accountId) {
        try {
            return new ResponseEntity(toHashedStatements(accountService.getLast3MonthsStatements(accountId)), HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity("Service not available.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, String> sanitizeNullValues(Map<String, String> map) {
        return map.entrySet()
            .stream()
            .filter(e -> e.getValue() != null)
            .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    private List<HashedStatement> toHashedStatements(List<Statement> statements) {
        return statements.stream()
            .map(HashedStatement::new)
            .collect(Collectors.toList());
    }

}
