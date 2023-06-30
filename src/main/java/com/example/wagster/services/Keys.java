package com.example.wagster.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Keys {

    @Value("${test.key}")
    private String TEST_KEY;
    @Value("${second.key}")
    private String SECOND_KEY;

    public String getTEST_KEY() {
        return TEST_KEY;
    }

    public String getSECOND_KEY() {
        return SECOND_KEY;
    }

    public void setTEST_KEY(String TEST_KEY) {
        this.TEST_KEY = TEST_KEY;
    }

    public void setSECOND_KEY(String SECOND_KEY) {
        this.SECOND_KEY = SECOND_KEY;
    }
}