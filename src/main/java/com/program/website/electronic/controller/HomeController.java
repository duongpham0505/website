package com.program.website.electronic.controller;

import com.program.website.electronic.domain.data.UserDTO;
import com.program.website.electronic.domain.port.api.HomeServicePort;
import com.program.website.electronic.domain.service.HomeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class HomeController {
    private static final HomeServicePort homeServicePort = new HomeServiceImpl();
    @GetMapping("home")
    public ResponseEntity<Object> getData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") != null) {
            session.setMaxInactiveInterval(2);
        }
        return ResponseEntity.badRequest().body("can't get");
    }

    @PostMapping("login")
    public ResponseEntity<Object> loginUser(HttpServletRequest request, @RequestBody String data) {
        HttpSession session = request.getSession();
        UserDTO userDTO = homeServicePort.loginAccount(data);
        if (userDTO != null) {
            session.setAttribute("userLogin", userDTO);
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.accepted().build();
    }
}
