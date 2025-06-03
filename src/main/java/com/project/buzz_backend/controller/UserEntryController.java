package com.example.jounal.controller;


import com.example.jounal.entities.Users;
import com.example.jounal.entities.WeatherResponse;
import com.example.jounal.repositories.UserEntryRepository;
import com.example.jounal.services.UserEntryService;
import com.example.jounal.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserEntryRepository userEntryRepository;
    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<Users> getAllUsers(){
        return userEntryService.getAll();
    }

    @GetMapping("/all-userName")
    public List<String> getAllUserName(){return userEntryService.getAllUserName();}

    @GetMapping("/{userName}")
    public Users getUserByUserName(@PathVariable String userName){
        return userEntryService.findByUserName(userName);
    }

    @PostMapping("/login")
    public ResponseEntity<?> getUserByUserName (Users user){
        Users response = userEntryService.findByUserName(user.getUserName());
        if(response != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users isUser = userEntryService.findByUserName(userName);
        isUser.setUserName(user.getUserName());
        isUser.setPassword(user.getPassword());
        isUser.setEmail(user.getEmail());
        isUser.setBirthDate(user.getBirthDate());
        userEntryService.saveNewUser(isUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/weather")
    public ResponseEntity<?> callWeatherApi(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Rajkot");
        String msg = "";
        if(weatherResponse != null){
            msg = ", Temperature is "+ weatherResponse.getCurrent().getTemperature();
        }
        return new ResponseEntity<>("Har Har Mahadev " + authentication.getName() + msg,HttpStatus.OK);
    }
}
