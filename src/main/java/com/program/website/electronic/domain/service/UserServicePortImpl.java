package com.program.website.electronic.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.program.website.electronic.domain.data.UserDTO;
import com.program.website.electronic.domain.port.api.UserServicePort;
import com.program.website.electronic.infrastruture.repository.UserRepository;
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
public class UserServicePortImpl implements UserServicePort {
    private static final Logger logger = LogManager.getLogger(UserServicePortImpl.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO insertUser(String data) throws Exception {
        JSONObject jsonObject = new JSONObject(data);
        UserDTO userDTO = new ObjectMapper().readValue(data, UserDTO.class);
        long id = jsonObject.has("uid") ? jsonObject.getLong("uid") : 0L;
        if (id > 0) {
            userDTO.setUId(id);
        }
        try {
            userRepository.save(userDTO);
        }catch (Exception e) {
            logger.error("error insert user");
            return null;
        }
        return userRepository.getUser(userDTO.getEmail());
    }

    @Override
    public boolean updateUser(long id, String data) throws Exception {
        try {
            JSONObject jsonObject = new JSONObject(data);
            UserDTO userDTO = userRepository.getById(id);
            Map<String, Object> patchMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                patchMap.put(key, jsonObject.getString(key));
            }
            patchMap.forEach((k, value) -> {
                Field field = ReflectionUtils.findField(UserDTO.class, k);
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
                ReflectionUtils.setField(field, userDTO, value);
            });
        }catch (Exception e) {
            logger.error(e);
            System.out.println("user isExits !");
        }
        return false;
    }

    @Override
    public boolean deleteUser(long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserDTO getUser(long id) throws Exception {
        UserDTO userDTO = userRepository.getUserByUId(id);
        if (userDTO.getId() > 0) {
            return new UserDTO(userDTO.getId(), userDTO.getUId(), userDTO.getFirstName(), userDTO.getLastName(),
                    userDTO.getFullName(), userDTO.getEmail(), userDTO.getAvatar(), userDTO.getProfile(),
                    userDTO.getPhone(), userDTO.getDateOfBirth(), userDTO.getIdentify());
        }
        return null;
    }

    @Override
    public List<UserDTO> getUsers(List<Long> UserIds) throws Exception {
        return null;
    }

    @Override
    public UserDTO loginUser(String data) {
        return null;
    }
}
