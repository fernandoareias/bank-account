package com.fernando.account.cmd.api.controllers;

import com.fernando.account.cmd.api.commands.OpenAccountCommand;
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
@RequestMapping(path = "/api/v1/accounts")
public class OpenAccountController {

    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;


    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command)
    {
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try{
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account created", id), HttpStatus.CREATED);
        }catch (IllegalStateException ex)
        {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request {0}.", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e)
        {
            var safeErrorMessage = MessageFormat.format("Erro while processing request to open a new bank account for id {0}.", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
