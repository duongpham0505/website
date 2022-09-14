package com.program.website.electronic.infrastruture.repository;

import com.program.website.electronic.domain.data.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserDTO, Long> {
    @Query(value = "SELECT u FROM UserDTO u WHERE u.email = ?1")
    UserDTO getUser(String email);

    @Query(value = "select u from UserDTO u where u.uId = ?1")
    UserDTO getUserByUId(long uId);
}
