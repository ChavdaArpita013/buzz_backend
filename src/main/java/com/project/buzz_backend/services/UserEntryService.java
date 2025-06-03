package com.example.jounal.services;

import com.example.jounal.entities.Users;
import com.example.jounal.repositories.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(Users user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userEntryRepository.save(user);
        } catch (Exception e) {
            log.error("Error saving user" , e);
        }
    }

    public void saveAdmin(Users user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userEntryRepository.save(user);
        } catch (Exception e) {
            log.error("Error saving user" , e);
        }
    }

    public void saveUser(Users user){
        try {

            userEntryRepository.save(user);
        } catch (Exception e) {
            log.error("Error saving user" , e);
        }
    }

    public List<Users> getAll(){
        return userEntryRepository.findAll();
    }
    public List<String> getAllUserName(){
        return userEntryRepository.findAllUserName();
    }

    public Optional<Users> getUserById(ObjectId id){
        return userEntryRepository.findById(id);
    }

    public void deleteUserById(ObjectId id){
        userEntryRepository.deleteById(id);
    }

    public Optional<Users> updateUserById(ObjectId id){
        return userEntryRepository.findById(id);
    }

    public Users findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }
}
