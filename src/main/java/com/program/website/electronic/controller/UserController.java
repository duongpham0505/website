package com.program.website.electronic.controller;

import com.program.website.electronic.domain.data.UserDTO;
import com.program.website.electronic.domain.port.api.UserServicePort;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    UserServicePort servicePort;

    @PostMapping("user")
    public ResponseEntity<Object> insertUser(@RequestBody String data) {
        try {
            UserDTO userDTO = servicePort.insertUser(data);
            if (userDTO != null) {
                return ResponseEntity.ok(userDTO);
            }
        }catch (Exception e){
            logger.error(e);
        }

       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable long id) {
        try {
            UserDTO userDTO = servicePort.getUser(id);
            if (userDTO != null) {
                return ResponseEntity.ok(userDTO);
            }
        }catch (Exception e) {
            logger.error(e);
            logger.error("user co " +id+ " khong ton tai");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id, @RequestBody String data) {
        try {
            if (servicePort.updateUser(id, data)) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }catch (Exception e) {
            logger.error(e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        try {
            if (servicePort.deleteUser(id)) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }catch (Exception e) {
            logger.error(e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
