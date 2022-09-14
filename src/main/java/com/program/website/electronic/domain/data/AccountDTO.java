package com.program.website.electronic.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class AccountDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String password;
    private String email;
    private boolean isActive;
    private long lastLogin;

    public AccountDTO(String userName, String password, String email, boolean isActive, long lastLogin) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.lastLogin = lastLogin;
    }
}
