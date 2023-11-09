package com.juzi.design.repo;

import com.juzi.design.pojo.BusinessLaunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author codejuzi
 */
@Repository
public interface BusinessLaunchRepository extends JpaRepository<BusinessLaunch, Integer> {
}
