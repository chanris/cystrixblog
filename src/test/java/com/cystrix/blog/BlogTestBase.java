package com.cystrix.blog;

import com.cystrix.blog.dao.ArchiveDao;
import com.cystrix.blog.service.impl.ArchiveServiceImpl;
import com.cystrix.blog.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@ActiveProfiles("test")
@SpringBootTest(classes = {})
@ContextConfiguration(classes = {ArchiveServiceImpl.class})
public abstract class BlogTestBase {

    @MockBean
    public JwtUtils jwtUtils;

    @MockBean
    public ArchiveDao archiveDao;

    @Autowired
    public ApplicationContext applicationContext;

//    @Test
//    void logActiveProfiles() {
//        String[] activeProfiles = environment.getActiveProfiles();
//    }
//

}
