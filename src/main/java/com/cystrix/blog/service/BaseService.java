package com.cystrix.blog.service;

import com.cystrix.blog.vo.BaseVo;
import com.github.pagehelper.PageHelper;

/**
 * @author chenyue7@foxmail.com
 * @date 20/12/2023
 * @description
 */
public abstract class BaseService {
    public BaseService(){}

    protected void executePage(BaseVo vo) {
        if(vo.getPageNum() != null && vo.getPageSize() != null && vo.getPageNum() > 0 & vo.getPageSize() > -1) {
            PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        }
    }
}
