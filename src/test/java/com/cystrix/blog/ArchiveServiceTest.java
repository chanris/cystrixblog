package com.cystrix.blog;

import com.cystrix.blog.dao.ArchiveDao;
import com.cystrix.blog.dao.ArticleDao;
import com.cystrix.blog.dao.TestDao;
import com.cystrix.blog.service.impl.ArticleServiceImpl;
import com.cystrix.blog.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/7/31
 * @description
 */
// ContextConfiguration 只会注册 类，而不是接口或者 抽象类 ，即使加了@Repository 、@Component
// 标记了这个接口 是一个 Spring bean ， Spring 也不会把 这个 接口 实现 并注册。
@ContextConfiguration(classes = {TestDao.class})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ArchiveServiceTest extends BlogTestBase{

    // 环境中没有 Autowired 默认会 爆错
    // @Autowired(required = false)
    private ArticleServiceImpl articleService;

    // @Autowired
    // private ArticleDao articleDao;

    @Test
    public void printAllBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames); // 可选：排序输出
        for (String name : beanNames) {
            System.out.println(name);
        }
        System.out.println(applicationContext);
        // assert articleService != null;
    }
    @Test
    public void test() {
        System.out.println(applicationContext);

    }
}
