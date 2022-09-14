package com.program.website.electronic.domain.port.spi;

import com.program.website.electronic.domain.data.AccountDTO;

public interface HomePersistencePort {

    AccountDTO getAccount(String userName);
}
