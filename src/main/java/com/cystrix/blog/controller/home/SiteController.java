package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.SiteInfo;
import com.cystrix.blog.service.impl.SiteInfoServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@RestController
@RequestMapping(value = "/home/siteInfo")
public class SiteController {

    @Resource
    private SiteInfoServiceImpl siteInfoService;

    @GetMapping(value = "")
    public Response getSiteInfo() {
        return Response.ok(siteInfoService.getSiteInfo());
    }

    @PostMapping(value = "/update")
    public Response updateSiteInfo(@RequestBody SiteInfo siteInfo) {
        siteInfoService.updateSiteInfo(siteInfo);
        return Response.ok();
    }
}
