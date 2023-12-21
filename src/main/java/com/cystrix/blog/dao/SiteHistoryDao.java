package com.cystrix.blog.dao;

import com.cystrix.blog.entity.IpInfo;
import com.cystrix.blog.entity.SiteHistory;
import org.springframework.stereotype.Repository;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Repository
public interface SiteHistoryDao {

    void insert(SiteHistory siteHistory);

    void update(IpInfo ipInfo);
}
