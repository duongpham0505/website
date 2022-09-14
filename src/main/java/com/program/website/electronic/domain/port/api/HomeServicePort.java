package com.program.website.electronic.domain.port.api;

import com.program.website.electronic.domain.data.UserDTO;

public interface HomeServicePort {
    UserDTO loginAccount(String data);
}
