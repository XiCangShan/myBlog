package com.myall.myBlog.service;

import com.myall.myBlog.pojo.Options;
import org.springframework.stereotype.Service;


public interface OptionsService {
    public Options getOptions();
    public void insertOptions(Options options);
    public void updateOptions(Options options);
}
