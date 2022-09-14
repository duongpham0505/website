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
public class RoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String role;
    private String permission;
}
