package com.wontlost.cypher;

import com.wontlost.cypher.des.DESData;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class EncryptData {

    static final Map<String, DESData> desData = new HashMap<>();

    @Bean
    public static Map<String, DESData> desData() {
        return desData;
    }

}
