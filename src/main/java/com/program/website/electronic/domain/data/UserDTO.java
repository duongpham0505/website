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
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long uId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String avatar;
    private String profile;
    private String phone;
    private String dateOfBirth;
    private String identify;

    public UserDTO(long uId, String firstName, String lastName, String fullName, String email, String avatar, String profile, String phone, String dateOfBirth, String identify) {
        this.uId = uId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.avatar = avatar;
        this.profile = profile;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.identify = identify;
    }
}
