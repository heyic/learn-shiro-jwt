package com.hyc.repositroy;

import com.hyc.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findAllByRoleId(Long roleId);

    List<UserRole> findAllByUserId(Long userId);
}
