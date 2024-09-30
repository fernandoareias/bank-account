package com.fernando.account.cmd.api.controllers;

import com.fernando.account.cmd.api.commands.DepositFundsCommand;
import com.fernando.account.cmd.api.commands.WithdrawFundsCommand;
import infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shared.BaseResponse;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/funds")
public class FundsController {

    private final Logger logger = Logger.getLogger(FundsController.class.getName());


    @Autowired
    private CommandDispatcher commandDispatcher;


    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String  id,
                                                     @RequestBody DepositFundsCommand command){
        command.setId(id);

        try{
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Deposit funds request completed successfully!"), HttpStatus.OK);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = MessageFormat.format("Erro while processing request to open a new bank account for id {0}.", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/withdraw/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String  id,
                                                     @RequestBody WithdrawFundsCommand command){
        command.setId(id);

        try{
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Withdraw funds request completed successfully!"), HttpStatus.OK);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = MessageFormat.format("Erro while processing request to open a new bank account for id {0}.", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
