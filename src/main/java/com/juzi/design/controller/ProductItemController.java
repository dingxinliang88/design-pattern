package com.juzi.design.controller;

import com.juzi.design.pattern.composite.ProductComposite;
import com.juzi.design.pojo.ProductItem;
import com.juzi.design.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author codejuzi
 */
@RestController
@RequestMapping("/product")
public class ProductItemController {

    @Autowired
    private ProductItemService productItemService;

    @GetMapping("/query/all")
    public ProductComposite queryAllItems() {
        return productItemService.queryAllItems();
    }

    @PostMapping("/add")
    public ProductComposite addItem(@RequestBody ProductItem item) {
        return productItemService.addItem(item);
    }

    @PostMapping("/del")
    public ProductComposite delItem(@RequestBody ProductItem item) {
        return productItemService.delItem(item);
    }

}
