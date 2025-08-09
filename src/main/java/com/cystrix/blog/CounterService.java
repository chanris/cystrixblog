package com.cystrix.blog;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/7/31
 * @description
 */
@Service
public class CounterService {
    private int num = 0;

    public  int getNum() {
        return num;
    }

    public void increment() {
        ++num;
    }
}
