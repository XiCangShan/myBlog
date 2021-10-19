package com.myall.myBlog.service.impl;

import com.myall.myBlog.mapper.ArticleTagRefMapper;
import com.myall.myBlog.mapper.TagMapper;
import com.myall.myBlog.pojo.Tag;
import com.myall.myBlog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleTagRefMapper articleTagRefMapper;
    
    @Override
    public Integer countTag() {
        Integer count=0;
        try{
            count=tagMapper.countTag();
        }catch (Exception e){
            e.printStackTrace();
            log.error("统计tag错误，cause:{}",e);
        }
        return count;
    }

    @Override
    public List<Tag> getTagList() {
        List<Tag> tagList=null;
        try{
            tagList=tagMapper.getTagList();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取tagList错误，cause:{}",e);
        }
        return tagList;
    }

    @Override
    public List<Tag> getTagListByArticleId(Integer articleId) {
        List<Tag> tagList=null;
        try{
            tagList=articleTagRefMapper.getTagListByArticleId(articleId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取tagList错误，articleId:{}，cause:{}",articleId,e);
        }
        return tagList;
    }

    @Override
    public List<Tag> getTagListWithCount() {
        List<Tag> tagList=null;
        try{
            tagList=tagMapper.getTagList();
            for (int i = 0; i < tagList.size(); i++) {
                tagList.get(i).setArticleCount(articleTagRefMapper.countArticleByTagId( tagList.get(i).getTagId()));
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取tagListWithCount错误，cause:{}",e);
        }
        return tagList;
    }

    @Override
    public Tag getTagById(Integer id) {
        Tag tag=null;
        try{
            tag=tagMapper.getTagById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取tag错误，id:{}，cause:{}",id,e);
        }
        return tag;
    }

    @Override
    public Tag getTagByName(String name) {
        Tag tag=null;
        try{
            tag=tagMapper.getTagByName(name);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取tag错误，name:{}，cause:{}",name,e);
        }
        return tag;
    }

    @Override
    public void insert(Tag tag) {
        try{
           tagMapper.insert(tag);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加tag错误，tag:{}，cause:{}",tag,e);
        }
    }

    @Override
    public void delete(Integer id) {
        try{
            tagMapper.deleteById(id);
            articleTagRefMapper.deleteByTagId(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除tag错误，id:{}，cause:{}",id,e);
        }
    }

    @Override
    public void update(Tag tag) {
        try{
            tagMapper.update(tag);
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改tag错误，tag:{}，cause:{}",tag,e);
        }
    }
}

