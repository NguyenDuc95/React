package com.kbfina.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kbfina.management.dto.UserDTO;
import com.kbfina.management.entities.User;
import com.kbfina.management.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LogManager.getLogger(UserService.class);
    
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("load user by username " + username);
        boolean isExist = false;
        List<User> users = userRepository.findAll();
        if(users.size() == 0){
            throw new UsernameNotFoundException("User not found with username:" + username);
        }
        User result = null;
        for (User user : users) {
            if(user.getUsername().equals(username)){
                isExist = true;
                result = user;
                break;
            }
        }
        if(!isExist){
            throw new UsernameNotFoundException("User not found with username:" + username);
        }
        return result;
    }
    
    public List<User> getAllUser(){
        logger.debug("service get All users");
        List<User> usersList = userRepository.findAll();
        if(usersList == null){
            logger.error("can't get users in repository layer");
            return new ArrayList<User>();
        }
        logger.debug("find all user: " + usersList.size());
        return usersList;
    }

    public User getUserByID(Long id){
        logger.debug("service get user by id");
        User user = userRepository.getById(id);
        logger.debug("user: "+ user);
        return user;
    }

    public User createNewUser(UserDTO userDTO){
        logger.debug("Repository start add user to database: " + userDTO);
        User userCreated = null;
        if(userDTO == null){
            logger.error("Object to update is null ");
            return null;
        }
        try{
        //    User userCheck = userRepository.findByUserName(userDTO.getName());
        //    if(userCheck == null){
            userCreated = new User();
            userCreated.setUserName(userDTO.getName());
            userCreated.setPassWord(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            userCreated.setEnable(true);
            userCreated.setRole(userDTO.getRole());
            userCreated = userRepository.save(userCreated);
            logger.debug("Create New User to database: Success: "+ userCreated);
            // } else {
            //     logger.debug("There is existing user with same data:" + userDTO);
            // }
        } catch (Exception e) {
            logger.error("Add user to database error: " + e.toString());
        }
        return userCreated;
    }

    public User updateUserInformation(Long id, UserDTO userDTO){
        logger.debug("Repository start update user to database: " + userDTO);
        User userUpdated = null;
        if(userDTO == null){
            logger.error("Object to update is null ");
            return null;
        }
        try{
           Optional<User> userCheck = userRepository.findById(userDTO.getId());
           if(userCheck.isPresent()){
               userCheck.get().setRole(userDTO.getRole());
               userCheck.get().setUserName(userDTO.getName());
            userUpdated = userRepository.save(userCheck.get());
                logger.debug("Update user to database: Success: "+ userUpdated);
            } else {
                logger.debug("There is not existing user to update:" + userDTO);
            }
        } catch (Exception e) {
            logger.error("update user to database error: " + e.toString());
        }
        return userUpdated;
    }

    public boolean deleteUserByID(Long id){
        logger.debug("Repository delete user follow id " + id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("delete user :" + e.getMessage());
            return false;
        }
        return true;   
        
    }
    
}
