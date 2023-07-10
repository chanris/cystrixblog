package com.cystrix.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class AppTests {

    @Autowired
    DataSource dataSource;

    @Test
    public void testDBConnectionPoolType() {
        assert dataSource != null;
        System.out.println(dataSource.getClass().getCanonicalName());
    }
}
