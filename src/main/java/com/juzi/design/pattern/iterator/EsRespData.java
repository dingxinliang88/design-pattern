package com.juzi.design.pattern.iterator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * ES SQL API返回值
 *
 * @author codejuzi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsRespData {

    private List<Map<String, String>> columns;

    private List<List<Object>> rows;

    private String cursor;
}
