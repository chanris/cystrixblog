package com.cystrix.blog.conf.task;

import com.cystrix.blog.dao.ArticleDao;
import com.cystrix.blog.dao.SiteHistoryDao;
import com.cystrix.blog.dao.SiteInfoDao;
import com.cystrix.blog.entity.SiteHistory;
import com.cystrix.blog.entity.SiteInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Service
@Slf4j
public class SiteTaskService {

    @Resource
    private SiteInfoDao siteInfoDao;
    @Resource
    private SiteHistoryDao siteHistoryDao;
    @Resource
    private ArticleDao articleDao;

    /**
     * @Scheduled 必须要声明定时的任务策略 cron、fixedDelay、fixedRate 三选一
     * fixedDelay 它的间隔时间是根据上次的任务结束的时候开始计时的，两个轮次的间隔距离是固定的。
     * fixedRate
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoUpdateSiteInfo() {
        // 更新网站统计信息
        SiteInfo siteInfo = siteInfoDao.selectOne();
        siteInfo.setRunDays(siteInfo.getRunDays() + 1);
        SiteInfo articleStatisInfo = articleDao.articleStatisInfo();
        siteInfo.setArticleNum(articleStatisInfo.getArticleNum());
        siteInfo.setWordsNum(articleStatisInfo.getWordsNum());
        siteInfoDao.updateSelective(siteInfo);
    }

    public void updateSiteInfo(LocalDateTime updateTime) {
        // 更新网站统计信息
        SiteInfo siteInfo = siteInfoDao.selectOne();
        SiteInfo articleStatisInfo = articleDao.articleStatisInfo();
        siteInfo.setArticleNum(articleStatisInfo.getArticleNum());
        siteInfo.setWordsNum(articleStatisInfo.getWordsNum());
        siteInfo.setLatestUpdateTime(updateTime);
        siteInfoDao.updateSelective(siteInfo);
    }
}
