package com.hyc.repositroy;

import com.hyc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);


}
