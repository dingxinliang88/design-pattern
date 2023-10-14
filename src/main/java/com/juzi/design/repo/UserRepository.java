package com.juzi.design.repo;

import com.juzi.design.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author codejuzi
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findByUsername(String username);

    UserInfo findByUsernameAndUserPassword(String account, String password);
}
