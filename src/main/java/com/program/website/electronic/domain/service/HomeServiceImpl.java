package com.program.website.electronic.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.program.website.electronic.domain.data.AccountDTO;
import com.program.website.electronic.domain.data.UserDTO;
import com.program.website.electronic.domain.port.api.HomeServicePort;
import com.program.website.electronic.domain.service.base.AccountBase;
import com.program.website.electronic.http.HttpHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeServicePort {
    private static final Logger log = LogManager.getLogger(HomeServiceImpl.class);

    @Override
    public UserDTO loginAccount(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            Map<String, String> params = new HashMap<>();
            params.put("userName", userName);
            String accountData = HttpHelper.getInstance().getRequest("http://127.0.0.1:7000/api/login/account", params);
            AccountDTO accountDTO = new ObjectMapper().readValue(accountData, AccountDTO.class);
            if (accountDTO != null) {
                // check password
                boolean checkPass = AccountBase.getInstance().checkPassWord(accountDTO, password);
                if (checkPass) {
                    String response = HttpHelper.getInstance().getRequest(String.format("http://127.0.0.1:7000/user/%d", accountDTO.getId()), null);
                    UserDTO userDTO = new ObjectMapper().readValue(response, UserDTO.class);
                    if (userDTO != null) {
                        return userDTO;
                    }else {
                        UserDTO user = new UserDTO(accountDTO.getId(), "", "", "",
                                accountDTO.getEmail(), "", "", "", "", accountDTO.getUserName());

                        HttpHelper.getInstance().postRequest("http://127.0.0.1:7000/user", new ObjectMapper().writeValueAsString(user));
                        String responseMap = HttpHelper.getInstance().getRequest(String.format("http://127.0.0.1:7000/user/%d", accountDTO.getId()), null);
                        return new ObjectMapper().readValue(responseMap, UserDTO.class);
                    }
                }
            }
        }catch (Exception e) {
            log.error(e);
        }

        return null;
    }
}
