package com.cystrix.blog;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@SpringBootTest
public class AppTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    Environment environment;

    @Test
    public void testDBConnectionPoolType() {
        assert dataSource != null;
        System.out.println(dataSource.getClass().getCanonicalName());
    }

    @Test
    public void logActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        System.out.println("Active profiles:");
        for (String profile : activeProfiles) {
            System.out.println(profile);
        }
    }
}
