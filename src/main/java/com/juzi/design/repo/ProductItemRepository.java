package com.juzi.design.repo;

import com.juzi.design.pojo.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author codejuzi
 */
@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
    ProductItem findByPidAndName(Integer pid, String name);

    @Modifying
    @Query(value = "INSERT INTO product_item(id, name, pid)" +
            "VALUES((SELECT MAX(id) + 1 FROM product_item), ?2, ?1)", nativeQuery = true)
    void addItem(Integer pid, String name);

    @Modifying
    @Query(value = "DELETE FROM product_item WHERE id = ?1 OR pid = ?1", nativeQuery = true)
    void delItem(Integer pid);
}
