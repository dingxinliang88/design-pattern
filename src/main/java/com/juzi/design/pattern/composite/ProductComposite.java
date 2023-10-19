package com.juzi.design.pattern.composite;

import com.juzi.design.pojo.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author codejuzi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComposite extends AbstractProductItem {

    private Integer id;
    private Integer pid;
    private String name;
    private List<AbstractProductItem> children = new ArrayList<>();

    @Override
    protected void addProductItem(AbstractProductItem item) {
        this.children.add(item);
    }

    @Override
    protected void delProductItem(AbstractProductItem item) {
        ProductComposite removeItem = (ProductComposite) item;
        Iterator<AbstractProductItem> iterator = children.iterator();
        while (iterator.hasNext()) {
            ProductComposite composite = (ProductComposite) iterator.next();
            if (composite.getId().equals(removeItem.id)) {
                iterator.remove();
                break;
            }
        }
    }
}
