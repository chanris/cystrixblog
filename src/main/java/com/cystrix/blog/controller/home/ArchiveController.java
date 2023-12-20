package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.service.impl.ArchiveServiceImpl;
import com.cystrix.blog.vo.BaseVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 19/12/2023
 * @description
 */
@RestController
@RequestMapping(value = "/home/archive")
public class ArchiveController {

    @Resource
    private ArchiveServiceImpl archiveService;

    @GetMapping(value = "/listArticleWithPage")
    public Response listArticleWithPage(@RequestBody BaseVo vo) {
        List<Article> articles = archiveService.listArticleWithPage(vo);
        return Response.ok(new PageInfo<>(articles));
    }
}
