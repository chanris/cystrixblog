package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.SiteInfoDao;
import com.cystrix.blog.entity.SiteInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Service
public class SiteInfoServiceImpl {

    @Resource
    private SiteInfoDao siteInfoDao;

    public SiteInfo getSiteInfo() {
        return siteInfoDao.selectOne();
    }

    public void updateSiteInfo(SiteInfo record) {
        siteInfoDao.updateSelective(record);
    }
}
