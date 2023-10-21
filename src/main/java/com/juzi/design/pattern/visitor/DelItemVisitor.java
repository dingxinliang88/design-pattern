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
public class DelItemVisitor implements ItemVisitor<AbstractProductItem> {

    /**
     * Object Structure 数据提供者
     */
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        ProductComposite currentItem = (ProductComposite) redisCommonProcessor.get(PRODUCT_CACHE_KEY);
        // to be deleted item
        ProductComposite delItem = (ProductComposite) productItem;
        // 不能删除根节点
        if(delItem.getId().equals(currentItem.getId())) {
            throw new IllegalArgumentException("Cannot delete root node");
        }
        // 待删除节点的父结点就是当前节点，直接添加
        if (delItem.getPid().equals(currentItem.getId())) {
            currentItem.delProductItem(delItem);
            return currentItem;
        }
        delChild(delItem, currentItem);
        return currentItem;
    }

    private void delChild(ProductComposite delItem, ProductComposite currentItem) {
        for (AbstractProductItem itemChild : currentItem.getChildren()) {
            ProductComposite child = (ProductComposite) itemChild;
            if (child.getId().equals(delItem.getPid())) {
                child.delProductItem(delItem);
                break;
            } else {
                delChild(delItem, child);
            }
        }
    }
}
