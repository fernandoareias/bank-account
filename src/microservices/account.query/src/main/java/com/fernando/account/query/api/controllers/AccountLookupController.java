package com.fernando.account.query.api.controllers;

import com.fernando.account.query.api.dtos.AccountLookupResponse;
import com.fernando.account.query.api.dtos.EqualityType;
import com.fernando.account.query.api.queries.FindAccountByHolderQuery;
import com.fernando.account.query.api.queries.FindAccountByIdQuery;
import com.fernando.account.query.api.queries.FindAccountWithBalanceQuery;
import com.fernando.account.query.api.queries.FindAllAccountsQuery;
import com.fernando.account.query.domain.BankAccount;
import infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.BaseResponse;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountLookupController {
    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){

        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());

            if(accounts == null || accounts.size() < 0)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts", accounts.size()))
                    .build();

            return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = "Erro while processing request to open a new bank account for id.";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> findAccountById(@PathVariable(value = "id") String id){

        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));

            if(accounts == null || accounts.size() < 0)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank accounts")
                    .build();

            return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = MessageFormat.format("Erro while processing request to open a new bank account for id {0}.", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> findAccountByHolder(@PathVariable(value = "accountHolder") String accountHolder){

        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));

            if(accounts == null || accounts.size() < 0)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank accounts")
                    .build();

            return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = "Erro while processing request to open a new bank account for id.";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> findByBalance(
            @PathVariable(value = "equalityType") EqualityType equalityType,
            @PathVariable(value = "balance") double balance){

        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));

            if(accounts == null || accounts.size() < 0)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts", accounts.size()))
                    .build();

            return new ResponseEntity<AccountLookupResponse>(response, HttpStatus.OK);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = "Erro while processing request to open a new bank account for id.";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
