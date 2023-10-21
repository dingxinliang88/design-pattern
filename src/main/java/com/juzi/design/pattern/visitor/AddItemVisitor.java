package com.juzi.design.pattern.visitor;

import com.juzi.design.pattern.composite.AbstractProductItem;
import com.juzi.design.pattern.composite.ProductComposite;
import com.juzi.design.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.juzi.design.constants.ProductConstant.PRODUCT_CACHE_KEY;

/**
 * Concrete Visitor具体访问者
 *
 * @author codejuzi
 */
@Component
public class AddItemVisitor implements ItemVisitor<AbstractProductItem> {

    /**
     * Object Structure 数据提供者
     */
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        ProductComposite currentItem = (ProductComposite) redisCommonProcessor.get(PRODUCT_CACHE_KEY);
        // to be added item
        ProductComposite newItem = (ProductComposite) productItem;
        // 新增节点的父结点就是当前节点，直接添加
        if (newItem.getPid().equals(currentItem.getId())) {
            currentItem.addProductItem(newItem);
            return currentItem;
        }
        addChild(newItem, currentItem);
        return currentItem;
    }

    private void addChild(ProductComposite newItem, ProductComposite currentItem) {
        for (AbstractProductItem itemChild : currentItem.getChildren()) {
            ProductComposite child = (ProductComposite) itemChild;
            if (child.getId().equals(newItem.getPid())) {
                child.addProductItem(newItem);
                break;
            } else {
                addChild(newItem, child);
            }
        }
    }
}
