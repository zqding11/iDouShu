package com.example.demo_websocket.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationManager<T>{
    /**
     * 通用分页查询方法
     *
     * @param page 当前页
     * @param size 每页大小
     * @param mapper Mapper接口
     * @return 分页结果
     *  @author dzk
     */
    public IPage<T> paginate(int page, int size, com.baomidou.mybatisplus.core.mapper.BaseMapper<T> mapper) {
            Page<T> pageRequest = new Page<>(page, size);
            return mapper.selectPage(pageRequest, null);
        }
    }
