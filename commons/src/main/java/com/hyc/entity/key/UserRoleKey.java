package com.hyc.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleKey implements Serializable {

    private static final long serialVersionUID = -7140258016461996145L;

    private Long userId;

    private Long roleId;

}
