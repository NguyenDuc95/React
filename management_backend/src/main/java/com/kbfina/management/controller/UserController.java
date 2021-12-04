package com.kbfina.management.controller;

import java.util.ArrayList;
import java.util.List;

import com.kbfina.management.dto.UserDTO;
import com.kbfina.management.entities.User;
import com.kbfina.management.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    
    private final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @CrossOrigin( origins = "http://localhost:3000", exposedHeaders = "X-Total-Count")
	@GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        logger.debug("REST handle getAllUsers");
		List<User> usersList = userService.getAllUser();
        logger.debug("usersList with size: " + usersList.size());
        List<UserDTO> responseUsers = new ArrayList<>();
        for (User user : usersList) {
            responseUsers.add(new UserDTO(user.getId(), user.getUsername(), user.getRole(), user.getPassword()));
        }
        return ResponseEntity.ok().header("X-Total-Count", "10").body(responseUsers);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable Long id){
        logger.debug("REST handle getUserByID: "+ id);
		User user = userService.getUserByID(id);
        UserDTO userDTO = new UserDTO();
        if(user != null){
            userDTO.setId(user.getId());
            userDTO.setName(user.getUsername());
            userDTO.setRole(user.getRole());
        }
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserInformation(@PathVariable Long id, @RequestBody UserDTO user){
        logger.error("put body " + user);
        User userUpdated = userService.updateUserInformation(id, user);
        if(userUpdated != null){
            return ResponseEntity.ok().body(userUpdated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @CrossOrigin( origins = "http://localhost:3000")
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody UserDTO userDTO){
        logger.error("UserDTO:" + userDTO);
        User user = userService.createNewUser(userDTO);
        if(user != null){
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserByID(@PathVariable Long id){
        logger.error("delete user "+ id);
        boolean result = userService.deleteUserByID(id);
        if(result){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
