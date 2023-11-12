package com.juzi.design.service;

import com.juzi.design.pattern.iterator.EsQueryIterator;
import com.juzi.design.pattern.iterator.EsSqlQuery;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author codejuzi
 */
@Service
public class EsQueryService {

    public Object queryEsBySql(EsSqlQuery esSqlQuery) {
        EsQueryIterator iterator = esSqlQuery.iterator();
        Stream<Map<String, Object>> resultStream
                = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
        return resultStream.collect(Collectors.toList());
    }
}
