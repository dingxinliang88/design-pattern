package com.juzi.design.service;

import com.juzi.design.pattern.composite.AbstractProductItem;
import com.juzi.design.pattern.composite.ProductComposite;
import com.juzi.design.pojo.ProductItem;
import com.juzi.design.repo.ProductItemRepository;
import com.juzi.design.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author codejuzi
 */
@Service
public class ProductItemService {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private ProductItemRepository productItemRepository;

    public ProductComposite queryAllItems() {
        Object cacheItems = redisCommonProcessor.get("product:items");
        if (cacheItems != null) {
            return (ProductComposite) cacheItems;
        }

        List<ProductItem> productItemsFromDb = productItemRepository.findAll();
        ProductComposite composite = generateProductTree(productItemsFromDb);

        if (composite == null) {
            throw new RuntimeException("Product items should not be null!");
        }
        redisCommonProcessor.set("product:items", composite);
        return composite;
    }

    private ProductComposite generateProductTree(List<ProductItem> productItems) {
        // convert ProductItem to ProductComposite
        List<ProductComposite> composites = new ArrayList<>(productItems.size());
        productItems.forEach(productItem -> composites.add(ProductComposite.builder()
                .id(productItem.getId())
                .pid(productItem.getPid())
                .name(productItem.getName())
                .build()));

        // Group By pid
        Map<Integer, List<ProductComposite>> compositeGroups
                = composites.stream().collect(Collectors.groupingBy(ProductComposite::getPid));
        composites.forEach(item -> {
            List<ProductComposite> compositeList = compositeGroups.get(item.getId());
            item.setChildren(
                    compositeList == null ?
                    new ArrayList<>() :
                    compositeList.stream()
                            .map(composite -> (AbstractProductItem) composite)
                            .collect(Collectors.toList())
            );
        });
        return composites.size() == 0 ? null : composites.get(0);
    }


}
