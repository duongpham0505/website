package com.program.website.electronic.domain.port.api;

import com.program.website.electronic.domain.data.UserDTO;

import java.util.List;

public interface UserServicePort {
    UserDTO insertUser(String data) throws Exception;
    boolean updateUser(long id, String data) throws Exception;
    boolean deleteUser(long id) throws Exception;
    UserDTO getUser(long id) throws Exception;
    List<UserDTO> getUsers(List<Long> UserIds) throws Exception;
    public UserDTO loginUser(String data);
}
