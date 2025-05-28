package com.cystrix.blog;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Disabled
@SpringBootTest
class AppTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    Environment environment;

    @Test
    void testDBConnectionPoolType() {
        assert dataSource != null;
        System.out.println(dataSource.getClass().getCanonicalName());
    }

    @Test
    void logActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        System.out.println("Active profiles:");
        for (String profile : activeProfiles) {
            System.out.println(profile);
        }
    }
}
