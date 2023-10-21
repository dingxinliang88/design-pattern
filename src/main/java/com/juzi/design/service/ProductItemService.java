package com.juzi.design.service;

import com.juzi.design.pattern.composite.AbstractProductItem;
import com.juzi.design.pattern.composite.ProductComposite;
import com.juzi.design.pattern.visitor.AddItemVisitor;
import com.juzi.design.pattern.visitor.DelItemVisitor;
import com.juzi.design.pojo.ProductItem;
import com.juzi.design.repo.ProductItemRepository;
import com.juzi.design.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.juzi.design.constants.ProductConstant.PRODUCT_CACHE_KEY;

/**
 * @author codejuzi
 */
@Service
public class ProductItemService {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private AddItemVisitor addItemVisitor;

    @Autowired
    private DelItemVisitor delItemVisitor;

    public ProductComposite queryAllItems() {
        Object cacheItems = redisCommonProcessor.get(PRODUCT_CACHE_KEY);
        if (cacheItems != null) {
            return (ProductComposite) cacheItems;
        }

        List<ProductItem> productItemsFromDb = productItemRepository.findAll();
        ProductComposite composite = generateProductTree(productItemsFromDb);

        if (composite == null) {
            throw new RuntimeException("Product items should not be null!");
        }
        redisCommonProcessor.set(PRODUCT_CACHE_KEY, composite);
        return composite;
    }

    @Transactional
    public ProductComposite addItem(ProductItem item) {
        // 先更新数据库
        productItemRepository.addItem(item.getPid(), item.getName());
        // 通过访问者模式访问，添加新的商品类目
        ProductComposite addItem = ProductComposite.builder()
                .id(productItemRepository.findByPidAndName(item.getPid(), item.getName()).getId())
                .pid(item.getPid())
                .name(item.getName())
                .children(new ArrayList<>())
                .build();
        AbstractProductItem updatedItems = addItemVisitor.visitor(addItem);
        // 更新缓存（加入重试 + 人工介入的方式）
        redisCommonProcessor.set(PRODUCT_CACHE_KEY, updatedItems);
        return (ProductComposite) updatedItems;
    }

    @Transactional
    public ProductComposite delItem(ProductItem item) {
        // 先更新数据库
        productItemRepository.delItem(item.getId());
        // 通过访问者模式访问，删除商品类目
        ProductComposite delItem = ProductComposite.builder()
                .id(item.getId())
                .name(item.getName())
                .pid(item.getPid())
                .build();
        AbstractProductItem updatedItems = delItemVisitor.visitor(delItem);
        // 更新缓存
        redisCommonProcessor.set(PRODUCT_CACHE_KEY, updatedItems);
        return (ProductComposite) updatedItems;
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
