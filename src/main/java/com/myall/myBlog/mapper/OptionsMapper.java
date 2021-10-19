package com.myall.myBlog.mapper;

import com.myall.myBlog.pojo.Options;
import org.apache.ibatis.annotations.Param;

public interface OptionsMapper {
     Options getOptionsById(@Param("optionId") Integer optionId);
     Options getOptions();
     int deleteOptionsById(@Param("optionId") Integer optionId);
     int insert(Options options);
     int updateOptions(Options options);
}
