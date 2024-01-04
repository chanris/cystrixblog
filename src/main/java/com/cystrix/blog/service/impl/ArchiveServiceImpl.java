package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArchiveDao;
import com.cystrix.blog.entity.Article;
import com.cystrix.blog.service.BaseService;
import com.cystrix.blog.view.ArchiveStatisInfoView;
import com.cystrix.blog.vo.ArchiveVo;
import com.cystrix.blog.vo.BaseVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 19/12/2023
 * @description
 */
@Service
public class ArchiveServiceImpl extends BaseService {
    @Resource
    private ArchiveDao archiveDao;

    // 根据年，月获得文章信息列表
    public List<Article> listArticleWithPage(ArchiveVo vo) {
        executePage(vo);
        return archiveDao.listArticleWithPage(vo);
    }

    // 获得每个月的文章数量
    public List<ArchiveStatisInfoView> listArchiveStatisInfoWithPage(BaseVo vo) {
        executePage(vo);
        return archiveDao.listArchiveStatisInfoWithPage();
    }

}
