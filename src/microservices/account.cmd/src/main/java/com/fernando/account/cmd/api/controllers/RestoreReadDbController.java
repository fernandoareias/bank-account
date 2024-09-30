package com.fernando.account.cmd.api.controllers;

import com.fernando.account.cmd.api.commands.OpenAccountCommand;
import com.fernando.account.cmd.api.commands.RestoreDbCommand;
import com.fernando.account.cmd.api.dtos.OpenAccountResponse;
import infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.BaseResponse;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restore")
public class RestoreReadDbController {

    private final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;


    @PostMapping
    public ResponseEntity<BaseResponse> restore()
    {
        try{
            commandDispatcher.send(new RestoreDbCommand());
            return new ResponseEntity<>(new BaseResponse("Bank account created"), HttpStatus.CREATED);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = "Erro while processing request to restore";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
