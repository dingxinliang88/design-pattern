package com.juzi.design.repo;

import com.juzi.design.pojo.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author codejuzi
 */
@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
}
