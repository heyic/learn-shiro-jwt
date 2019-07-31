package com.hyc.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "docs_role")
public class Role extends BaseEntity<Long>{

    @Column(unique = true, nullable = false)
    private String roleName;
}
