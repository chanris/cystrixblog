package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArticleCommentDao;
import com.cystrix.blog.dao.CommentDao;
import com.cystrix.blog.entity.ArticleComment;
import com.cystrix.blog.entity.Comment;
import com.cystrix.blog.entity.SiteHistory;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.util.NetUtils;
import com.cystrix.blog.view.CommentView;
import com.cystrix.blog.vo.CommentAddVo;
import com.sun.source.doctree.CommentTree;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/5/29
 * @description
 */
@Service
public class CommentServiceImpl {

    @Resource
    private CommentDao commentDao;
    @Resource
    private ArticleCommentDao articleCommentDao;
    @Resource
    private NetUtils netUtils;
    @Resource
    private ThreadPoolExecutor ipThreadPoolExecutor;


    /**
     * 创建评论
     * @param vo
     */
    public void addComment(CommentAddVo vo) {
        Comment comment = new Comment();
        // request中 获取ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        ipThreadPoolExecutor.execute(()->{
            SiteHistory siteHistory = netUtils.getSiteHistory(request);
            if (siteHistory != null) {
                comment.setLocal(siteHistory.getCity());
            }
            if (vo.getPid() != null) {
                comment.setPid(vo.getPid());
            }else {
                comment.setPid(-1);
            }
            comment.setContent(vo.getContent());
            comment.setUserIp(netUtils.getIpAddress(request));
            commentDao.insert(comment);



            // 关联文章
            ArticleComment articleComment = new ArticleComment();
            articleComment.setArticleId(vo.getArticleId());
            articleComment.setCommentId(comment.getId());
            articleCommentDao.insert(articleComment);
        });
    }

    /**
     * 获得文章评论
     * @param articleId
     * @return
     */
    public List<CommentView> commentTree(Integer articleId) {
        List<Comment> commentList = commentDao.commentListByArticleId(articleId);
        if (commentList.isEmpty()) {
            // throw new ParameterException(String.format("articleId: %s 不存在或找不到评论", articleId));
            return null;
        }

        Map<Integer, CommentView> viewMap = new HashMap<>();
        List<CommentView> rootList = new ArrayList<>();

        // 第一遍：构造 CommentView 并建立 ID 映射
        for (Comment comment : commentList) {
            CommentView view = new CommentView();
            view.setId(comment.getId());
            view.setContent(comment.getContent());
            view.setUserIp(comment.getUserIp());
            view.setLocal(comment.getLocal());
            view.setCreateTime(comment.getCreateTime());
            view.setChildren(new ArrayList<>());
            viewMap.put(comment.getId(), view);
        }

        // 第二遍：建立父子关系
        for (Comment comment : commentList) {
            CommentView currentView = viewMap.get(comment.getId());
            if (comment.getPid() == -1) {
                rootList.add(currentView);
            } else {
                CommentView parentView = viewMap.get(comment.getPid());
                if (parentView != null) {
                    parentView.getChildren().add(currentView);
                } else {
                    // 可选：处理父评论丢失的情况
                    // rootList.add(currentView); // 或者日志记录异常
                    throw new RuntimeException("评论数据异常：父级评论不存在");
                }
            }
        }

        return rootList;
    }

}
