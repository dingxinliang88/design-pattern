package com.juzi.design.repo;

import com.juzi.design.pojo.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author codejuzi
 */
@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {

    Products findByProductId(String productId);

}
