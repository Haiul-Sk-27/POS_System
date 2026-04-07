package com.StoreHub.StoreHub.pos.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping
    public String welCom(){
        return  "Welcome to the POS system...";
    }
}
