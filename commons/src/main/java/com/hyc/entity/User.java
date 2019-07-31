package com.hyc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "docs_user")
public class User extends BaseEntity<Long>{

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(length = 36, nullable = false)
    private String salt;

    @Column(length = 64, nullable = false)
    private String password;

    @Transient
    private List<Role> roles = new ArrayList<>();

    public String getCredential() {
        return username + salt;
    }

}
