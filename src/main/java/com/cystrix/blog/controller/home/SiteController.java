package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.SiteInfo;
import com.cystrix.blog.service.impl.SiteInfoServiceImpl;
import com.cystrix.blog.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Api(tags = "站点信息管理")
@RestController
@RequestMapping(value = "/home/siteInfo")
public class SiteController {

    @Resource
    private SiteInfoServiceImpl siteInfoService;

    @ApiOperation(value = "获取站点信息")
    @GetMapping(value = "")
    public Response getSiteInfo() {
        return Response.ok(siteInfoService.getSiteInfo());
    }

    @ApiOperation(value = "更新站点信息")
    @PostMapping(value = "/update")
    public Response updateSiteInfo(@ApiParam(value = "站点信息") @RequestBody SiteInfo siteInfo) {
        siteInfoService.updateSiteInfo(siteInfo);
        return Response.ok();
    }

    @ApiOperation(value = "获取随机格言")
    @GetMapping(value = "/randomMottos")
    public Response randomMottos(@ApiParam(value = "格言数量，默认3条") Integer num) {
        if(num == null) {
            num = 3;
        }
        return Response.ok(siteInfoService.randMottoList(num));
    }
}