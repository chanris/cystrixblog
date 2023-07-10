package com.cystrix.blog.controller.admin;

import com.cystrix.blog.vo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/index")
    @ResponseBody
    public Response index() {
        return Response.ok();
    }
}
