package com.hyc.repositroy;

import com.hyc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    List<Role> findAllByIdIsIn(List<Long> roleIds);
}
