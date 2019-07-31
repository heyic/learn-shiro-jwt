package com.hyc.entity;

import com.hyc.entity.key.UserRoleKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserRoleKey.class)
@Table(name = "docs_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -2202440264324460669L;
    @Id
    @Column(nullable = false)
    private Long userId;

    @Id
    @Column(nullable = false)
    private Long roleId;

}
