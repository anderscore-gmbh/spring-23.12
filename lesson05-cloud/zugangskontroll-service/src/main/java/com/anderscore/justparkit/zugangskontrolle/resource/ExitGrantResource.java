package com.anderscore.justparkit.zugangskontrolle.resource;

import com.anderscore.justparkit.zugangskontrolle.service.ExitGrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exitGrant")
public class ExitGrantResource {

    @Autowired
    private ExitGrantService service;

    @GetMapping
    public String findByTicketId(@RequestParam("ticketId") Long ticketId){
        return service.isExitGranted(ticketId) ? "EXIT GRANTED" : "EXIT DENIED";
    }
}