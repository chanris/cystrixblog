package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.service.impl.ArchiveServiceImpl;
import com.cystrix.blog.view.ArchiveStatisInfoView;
import com.cystrix.blog.vo.ArchiveVo;
import com.cystrix.blog.vo.BaseVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/listArticleWithPage")
    public Response listArticleWithPage(@RequestBody ArchiveVo vo) {
        List<Article> articles = archiveService.listArticleWithPage(vo);
        return Response.ok(new PageInfo<>(articles));
    }

    @PostMapping(value = "/listArchiveStatisInfoWithPage")
    public Response listArchiveStatisInfoWithPage(@RequestBody BaseVo vo) { // @RequestBody 会创建一个BaseVo，相当于 new BaseVo()
        List<ArchiveStatisInfoView> result = archiveService.listArchiveStatisInfoWithPage(vo);
        return Response.ok(new PageInfo<>(result));
    }
}
