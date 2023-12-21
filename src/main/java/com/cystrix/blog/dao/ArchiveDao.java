package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.view.ArchiveStatisInfoView;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 19/12/2023
 * @description
 */
@Repository
public interface ArchiveDao {

    // 根据年、月获得文章信息列表
    List<Article> listArticleWithPage();

    // 获得归档统计信息
    List<ArchiveStatisInfoView> listArchiveStatisInfoWithPage();

}
