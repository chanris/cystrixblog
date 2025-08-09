package com.cystrix.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/7/31
 * @description
 */

@ContextConfiguration(classes = {CounterService.class})
public class ATest extends BlogTestBase {

    @Autowired
    CounterService counterService;

    @Test
    void testA() {
        System.out.println(applicationContext);
        counterService.increment();
        assertEquals(1, counterService.getNum());
    }
}
