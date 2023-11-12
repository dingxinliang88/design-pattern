package com.juzi.design.pattern.iterator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * ConcreteAggregate具体容器
 *
 * @author codejuzi
 */
@Data
@JsonIgnoreProperties
public class EsSqlQuery implements EsSqlQueryInterface<EsQueryIterator> {

    private String query;

    private Long fetchSize;

    private String cursor;

    public EsSqlQuery(String cursor) {
        this.cursor = cursor;
    }

    public EsSqlQuery(String query, Long fetchSize) {
        this.query = query;
        this.fetchSize = fetchSize;
    }

    @Override
    public EsQueryIterator iterator() {
        return new EsQueryIterator(query, fetchSize);
    }
}
