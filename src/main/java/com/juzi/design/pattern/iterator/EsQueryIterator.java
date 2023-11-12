package com.juzi.design.pattern.iterator;

import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ConcreteIterator具体迭代器
 *
 * @author codejuzi
 */
public class EsQueryIterator implements Iterator<Map<String, Object>> {

    /**
     * 记录当前分页
     */
    private String cursor;

    /**
     * 记录查询的columns，在构造器中进行初始化
     */
    private final List<String> columns;

    /**
     * 将ES SQL Rest API的返回值封装到List<Map> 中，以便处理返回值
     */
    Iterator<Map<String, Object>> iterator;

    RestTemplate restTemplate = new RestTemplate();

    public EsQueryIterator(String query, Long fetchSize) {
        EsRespData esRespData = restTemplate.postForObject("http://localhost:9200/_sql?formar=json",
                new EsSqlQuery(query, fetchSize), EsRespData.class);
        assert esRespData != null;
        this.cursor = esRespData.getCursor();
        this.columns = esRespData.getColumns().stream().map(x -> x.get("name")).collect(Collectors.toList());
        this.iterator = convert(columns, esRespData).iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext() || scrollNext();
    }

    @Override
    public Map<String, Object> next() {
        return iterator.next();
    }

    private boolean scrollNext() {
        if (iterator == null || this.cursor == null) {
            return false;
        }
        EsRespData esRespData = restTemplate.postForObject("http://localhost:9200/_sql?formar=json",
                new EsSqlQuery(cursor), EsRespData.class);
        assert esRespData != null;
        this.cursor = esRespData.getCursor();
        this.iterator = convert(columns, esRespData).iterator();
        return iterator.hasNext();
    }

    private List<Map<String, Object>> convert(List<String> columns, EsRespData esRespData) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (List<Object> row : esRespData.getRows()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                map.put(columns.get(i), row.get(i));
            }
            results.add(map);
        }
        return results;
    }

}
