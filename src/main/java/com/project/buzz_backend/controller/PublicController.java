package com.example.jounal.controller;

import com.example.jounal.entities.Users;
import com.example.jounal.services.UserEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService;
    @GetMapping("/ok")
    public String healthCheck (){
        return "Working properly";
    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody Users user){
        userEntryService.saveNewUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> getUserByUserName (@RequestBody Users user){
        Users response = userEntryService.findByUserName(user.getUserName());
        if(response != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
