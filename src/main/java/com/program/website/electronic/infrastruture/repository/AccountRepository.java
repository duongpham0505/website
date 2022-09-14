package com.program.website.electronic.infrastruture.repository;

import com.program.website.electronic.domain.data.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<AccountDTO, Long> {

    @Query(value = "SELECT a FROM AccountDTO a WHERE a.userName = ?1")
    AccountDTO getAccounts(String userName);
}
