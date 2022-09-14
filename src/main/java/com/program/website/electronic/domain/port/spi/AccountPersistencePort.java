package com.program.website.electronic.domain.port.spi;

import com.program.website.electronic.domain.data.AccountDTO;

import java.util.List;

public interface AccountPersistencePort {
    void insertAccount(AccountDTO accountDTO) throws Exception;
    void updateAccount(AccountDTO accountDTO) throws Exception;
    void deleteAccount(long id) throws Exception;
    AccountDTO getAccount(long id) throws Exception;
    List<AccountDTO> getAccounts(List<Long> accountIds) throws Exception;
}
