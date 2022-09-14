package com.program.website.electronic.infrastruture.adapters;

import com.program.website.electronic.domain.data.AccountDTO;
import com.program.website.electronic.domain.port.spi.AccountPersistencePort;

import java.util.List;

public class AccountAdapter implements AccountPersistencePort {
    private static final AccountAdapter accountAdapter = new AccountAdapter();
    public static AccountAdapter getInstance(){return accountAdapter;}

    @Override
    public void insertAccount(AccountDTO accountDTO) throws Exception {

    }

    @Override
    public void updateAccount(AccountDTO accountDTO) throws Exception {

    }

    @Override
    public void deleteAccount(long id) throws Exception {

    }

    @Override
    public AccountDTO getAccount(long id) throws Exception {
        return null;
    }

    @Override
    public List<AccountDTO> getAccounts(List<Long> accountIds) throws Exception {
        return null;
    }

}
