package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArchiveDao;
import com.cystrix.blog.entity.Article;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.BaseService;
import com.cystrix.blog.vo.BaseVo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public List<Article> listArticleWithPage(BaseVo vo) {
        executePage(vo);
        return archiveDao.listArticleWithPage();
    }

}
