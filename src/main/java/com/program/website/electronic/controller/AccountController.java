package com.program.website.electronic.controller;

import com.program.website.electronic.domain.data.AccountDTO;
import com.program.website.electronic.domain.port.api.AccountServicePort;
import com.program.website.electronic.util.UtilBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/login")
public class AccountController {
    @Autowired
    AccountServicePort accountServicePort;

    @PostMapping("account")
    public ResponseEntity<Object> insertAccount(HttpServletRequest request, @RequestBody String data) throws Exception {
        JSONObject jsonObject = new JSONObject(data);
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        AccountDTO accountDTO = new AccountDTO(userName, password, email, true, new Date().getTime());
        accountDTO.setPassword(UtilBase.getInstance().convertPassword(accountDTO.getPassword()));
        AccountDTO account = accountServicePort.insertAccount(accountDTO);
        if (account != null) {
            return ResponseEntity.ok(accountDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("account")
    public ResponseEntity<Object> getAccounts(@PathParam("userName") String userName) throws Exception {
        AccountDTO accountDTO = accountServicePort.getAccountFromKey(userName);
        if (accountDTO != null) {
            return ResponseEntity.ok(accountDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("account/{id}")
    public ResponseEntity<Object> getAccount(HttpServletRequest request, @PathVariable long id) {
        try {
            AccountDTO accountDTO = accountServicePort.getAccount(id);
            return ResponseEntity.ok(accountDTO);
        }catch (Exception e) {
            logger.error(e);
            System.out.println("account no isExits");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("account/{id}")
    public ResponseEntity<Object> updateAccount(HttpServletRequest request, @PathVariable long id, String data) {
        try {
            if (accountServicePort.updateAccount(id, data)) {
                return ResponseEntity.ok().build();
            }
        }catch (Exception e) {
            logger.error(e);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("account/{id}")
    public ResponseEntity<Object> deleteAccount(HttpServletRequest request, @PathVariable long id) throws Exception {
        if (accountServicePort.deleteAccount(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("login")
    public ResponseEntity<Object> loginUser(HttpServletRequest request, @RequestBody String data) {
        HttpSession session = request.getSession();
        AccountDTO accountDTO = accountServicePort.loginAccount(data);
        if (accountDTO != null) {
            session.setAttribute("userLogin", accountDTO);
            return ResponseEntity.ok(accountDTO);
        }
        return ResponseEntity.accepted().build();
    }

    private static final Logger logger = LogManager.getLogger(AccountController.class);
}
