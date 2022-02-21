package com.apigateway.mapper;

import java.util.Map;

public class MapToUserIdStringMapper {
    public static String map(Map<String, Object>map) {
        return (String) map.get("id");
    }
}
