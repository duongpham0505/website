package com.program.website.electronic.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.program.website.electronic.domain.data.AccountDTO;
import com.program.website.electronic.domain.data.UserDTO;
import com.program.website.electronic.domain.port.api.AccountServicePort;
import com.program.website.electronic.domain.service.base.AccountBase;
import com.program.website.electronic.http.HttpHelper;
import com.program.website.electronic.infrastruture.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServicePortImpl implements AccountServicePort {
    private static final Logger logger = LogManager.getLogger(AccountServicePortImpl.class);

    @Autowired AccountRepository accountRepository;

    @Override
    public AccountDTO insertAccount(AccountDTO accountDTO) {
        AccountDTO account = null;
        try {
            accountRepository.save(accountDTO);
            account = accountRepository.getById(accountDTO.getId());
            UserDTO userDTO = new UserDTO(account.getId(), "", "", "", "", "",
                    "", "", "", account.getUserName());
            //insert user
            HttpHelper.getInstance().postRequest("http://127.0.0.1:7000/user", new ObjectMapper().writeValueAsString(userDTO));
        }catch (Exception e) {
            logger.error(e);
            return null;
        }
        return account;
    }

    @Override
    public boolean updateAccount(long id, String data) {
        try {
            AccountDTO accountDTO = accountRepository.getById(id);
            JSONObject jsonObject = new JSONObject(data);
            Map<String, Object> patchMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                patchMap.put(key, jsonObject.getString(key));
            }
            patchMap.forEach((k, value) -> {
                Field field = ReflectionUtils.findField(AccountDTO.class, k);
                if (field == null) {
                    return;
                }
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (int.class.isAssignableFrom(type)) {
                    value = Integer.parseInt((String) value);
                } else if (long.class.isAssignableFrom(type)) {
                    value = Long.parseLong((String) value);
                }
                ReflectionUtils.setField(field, accountDTO, value);
            });
        }catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAccount(long id) throws Exception {
        try {
            AccountDTO accountDTO = accountRepository.getById(id);
            accountRepository.delete(accountDTO);
            return true;
        }catch (Exception e) {
            logger.error(e);
            System.out.println("account no isExist !");
        }
        return false;
    }

    @Override
    public AccountDTO getAccount(long id) throws Exception {
        return accountRepository.getById(id);
    }

    @Override
    public List<AccountDTO> getAccounts(List<Long> accountIds) throws Exception {
        List<AccountDTO> accountDTOList = accountRepository.findAll();
        if (accountDTOList.size() > 0) {
            return accountDTOList;
        }
        return null;
    }

    public AccountDTO getAccount(String userName) throws Exception {
        return accountRepository.getAccounts(userName);
    }

    @Override
    public AccountDTO loginAccount(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            AccountDTO accountDTO = this.getAccount(userName);
            if (accountDTO != null) {
                // check password
                boolean checkPass = AccountBase.getInstance().checkPassWord(accountDTO, password);
                if (checkPass) {
                    String url = String.format("http://127.0.0.1:7000/user/%d", accountDTO.getId());
                    String userResponse = HttpHelper.getInstance().getRequest(url, null);
                    if (userResponse != null && !userResponse.isEmpty()) {
                        return accountDTO;
                    }
                    UserDTO userDTO = new UserDTO(accountDTO.getId(), "", "", "",
                            accountDTO.getEmail(), "", "", "", "", accountDTO.getUserName());

                    HttpHelper.getInstance().postRequest("http://127.0.0.1:7000/user", new ObjectMapper().writeValueAsString(userDTO));
                    return accountDTO;
                }
            }
        }catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public AccountDTO getAccountFromKey(String param) {
        return accountRepository.getAccounts(param);
    }
}
