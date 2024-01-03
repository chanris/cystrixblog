package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.MottoDao;
import com.cystrix.blog.dao.SiteInfoDao;
import com.cystrix.blog.entity.Motto;
import com.cystrix.blog.entity.SiteInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Service
public class SiteInfoServiceImpl {

    @Resource
    private SiteInfoDao siteInfoDao;

    @Resource
    private MottoDao mottoDao;

    public SiteInfo getSiteInfo() {
        return siteInfoDao.selectOne();
    }

    public void updateSiteInfo(SiteInfo record) {
        siteInfoDao.updateSelective(record);
    }

    public List<Motto> randMottoList(Integer num) {
        return mottoDao.getMottoListByRand(num);
    }
}
