package com.juzi.design.pattern.composite;

import com.juzi.design.pojo.ProductItem;
import lombok.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author codejuzi
 */
@EqualsAndHashCode(callSuper = true)
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
    public void addProductItem(AbstractProductItem item) {
        this.children.add(item);
    }

    @Override
    public void delProductItem(AbstractProductItem item) {
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
