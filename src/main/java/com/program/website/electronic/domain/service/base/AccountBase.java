package com.program.website.electronic.domain.service.base;

import com.program.website.electronic.domain.data.AccountDTO;
import com.program.website.electronic.domain.port.spi.AccountPersistencePort;
import com.program.website.electronic.infrastruture.adapters.AccountAdapter;
import com.program.website.electronic.util.UtilBase;

public class AccountBase {
    private static final AccountBase accountBase = new AccountBase();
    public static AccountBase getInstance(){return accountBase;}
    private  static final AccountPersistencePort accountPersistence = new AccountAdapter();
    public boolean checkPassWord(AccountDTO accountDTO, String password) throws Exception {
        if (accountDTO == null) {
            return false;
        }
        if (accountDTO.getPassword() == null || accountDTO.getPassword().isEmpty()) {
            accountDTO.setPassword(UtilBase.getInstance().convertPassword(password));
            //update account
            accountPersistence.updateAccount(accountDTO);
        }
        UtilBase utilBase = UtilBase.getInstance();
        return utilBase.convertPassword(password).equals(utilBase.convertPassword(password));
    }

}
