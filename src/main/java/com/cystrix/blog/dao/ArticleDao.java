package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.entity.SiteInfo;
import com.cystrix.blog.view.ArticleView;
import com.cystrix.blog.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface ArticleDao {

    /*默认根据创建事件排序分页*/
    List<ArticleView> selectPageWithoutContent();

    ArticleView selectArticleViewById(Integer id);

    Article selectById(Integer id);

    void insert(Article article);

    void update(Article article);

    void deleteById(Integer id);

    SiteInfo articleStatisInfo();

    List<ArticleView> selectArticleWithPage(ArticleVo vo);

    void addLikeCount(Integer articleId);
}
