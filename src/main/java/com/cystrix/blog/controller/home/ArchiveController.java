package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.service.impl.ArchiveServiceImpl;
import com.cystrix.blog.view.ArchiveStatisInfoView;
import com.cystrix.blog.view.ArticleView;
import com.cystrix.blog.vo.ArchiveVo;
import com.cystrix.blog.vo.BaseVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 19/12/2023
 * @description
 */
@Api(tags = "归档管理")
@RestController
@RequestMapping(value = "/home/archive")
public class ArchiveController {

    @Resource
    private ArchiveServiceImpl archiveService;

    @ApiOperation(value = "获取归档文章列表")
    @PostMapping(value = "/listArticleWithPage")
    public Response listArticleWithPage(@ApiParam(value = "归档查询参数") @RequestBody ArchiveVo vo) {
        List<ArticleView> articles = archiveService.listArticleWithPage(vo);
        return Response.ok(new PageInfo<>(articles));
    }

    @ApiOperation(value = "获取归档统计信息列表")
    @PostMapping(value = "/listArchiveStatisInfoWithPage")
    public Response listArchiveStatisInfoWithPage(@ApiParam(value = "分页参数") @RequestBody BaseVo vo) { // @RequestBody 会创建一个BaseVo，相当于 new BaseVo()
        List<ArchiveStatisInfoView> result = archiveService.listArchiveStatisInfoWithPage(vo);
        return Response.ok(new PageInfo<>(result));
    }
}