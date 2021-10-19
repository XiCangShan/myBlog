package com.myall.myBlog.service.impl;

import com.myall.myBlog.mapper.OptionsMapper;
import com.myall.myBlog.pojo.Options;
import com.myall.myBlog.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OptionsServiceImpl implements OptionsService {
    @Autowired
    OptionsMapper optionsMapper;
    @Override
//    使用缓存，如果执行方法时参数一样，则从名为value的缓存中查找key的结构
    @Cacheable(value = "default",key = "'options'")
    public Options getOptions() {
        return optionsMapper.getOptions();
    }

    @Override
//    代表执行当前方法会清除缓存
    @CacheEvict(value = "default",key = "'options'")
    public void insertOptions(Options options) {
        optionsMapper.insert(options);
    }

    @Override
    @CacheEvict(value = "default",key = "'options'")
    public void updateOptions(Options options) {
        optionsMapper.updateOptions(options);
    }
}
