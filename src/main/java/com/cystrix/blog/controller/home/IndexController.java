package com.cystrix.blog.controller.home;

import com.cystrix.blog.vo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenyue7@foxmail.com
 * @date: 6/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/home")
public class IndexController {
    @RequestMapping(value = "/index")
    @ResponseBody
    public Response index() {
        return Response.ok();
    }
}
