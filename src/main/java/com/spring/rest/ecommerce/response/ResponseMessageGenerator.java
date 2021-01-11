package com.spring.rest.ecommerce.response;

import org.springframework.stereotype.Service;

@Service
public class ResponseMessageGenerator {

    public ResponseMessage getResponseForSuccessPostMethod(long newResourceId){
        return new ResponseMessage(
                "New resource created with id: " + newResourceId,
                System.currentTimeMillis()
        );
    }

    public ResponseMessage getResponseForSuccessDeleteMethod(long deletedResourceId){
        return new ResponseMessage(
                "Resource with id: " + deletedResourceId + " deleted",
                System.currentTimeMillis()
        );
    }

    public ResponseMessage getResponseForSuccessPutMethod(long updatedResourceId){
        return new ResponseMessage(
                "Resource with id: " + updatedResourceId + " updated",
                System.currentTimeMillis()
        );
    }

    public ResponseMessage getResponseForSuccessBanMethod(long bannedUserId){
        return new ResponseMessage(
                "User with id: " + bannedUserId + " banned",
                System.currentTimeMillis()
        );
    }

    public ResponseMessage getMessageForSuccessUserRegistration(long id){
        return new ResponseMessage(
                "User with id: " + id + " registered!",
                System.currentTimeMillis()
        );
    }
}