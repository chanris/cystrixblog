package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.SiteHistoryDao;
import com.cystrix.blog.entity.SiteHistory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Service
public class SiteHistoryServiceImpl {

    @Resource
    private SiteHistoryDao siteHistoryDao;

    public void saveHistory(SiteHistory siteHistory) {
        siteHistoryDao.insert(siteHistory);
    }

}
