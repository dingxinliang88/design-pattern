package com.juzi.design.controller;

import com.juzi.design.pattern.iterator.EsSqlQuery;
import com.juzi.design.service.EsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codejuzi
 */
@RestController
public class EsQueryController {

    @Autowired
    private EsQueryService esQueryService;

    @PostMapping("/query-es-by-sql")
    public Object queryEsBySql(@RequestBody EsSqlQuery esSqlQuery) {
        return esQueryService.queryEsBySql(esSqlQuery);
    }

}
