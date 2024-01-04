package com.cystrix.blog.dao;

import com.cystrix.blog.entity.SiteInfo;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author chenyue7@foxmail.com
 * @date 20/12/2023
 * @description
 */
@Repository
public interface SiteInfoDao {
    @Update("update site_info set run_days = 0 where id = 1")
    void resetRunDays();
    SiteInfo selectOne();
    void updateSelective(SiteInfo siteInfo);
    @Update("update site_info set visit_num = visit_num + 1 where id = 1")
    void addVisitNum();
}
