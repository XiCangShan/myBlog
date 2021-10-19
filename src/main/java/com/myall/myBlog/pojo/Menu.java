package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 290557513082795558L;
    private Integer menuId;
    private String menuName;
    private String menuUrl;
    private Integer menuLevel;
    private String menuIcon;
    private Integer menuOrder;
}
