package com.program.website.electronic.domain.port.api;

import com.program.website.electronic.domain.data.AccountDTO;

import java.util.List;

public interface AccountServicePort {
    AccountDTO insertAccount(AccountDTO accountDTO) throws Exception;
    boolean updateAccount(long id, String data) throws Exception;
    boolean deleteAccount(long id) throws Exception;
    AccountDTO getAccount(long id) throws Exception;
    List<AccountDTO> getAccounts(List<Long> accountIds) throws Exception;
    AccountDTO loginAccount(String data);
    AccountDTO getAccountFromKey(String param);
}
