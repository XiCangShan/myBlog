package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Notice implements Serializable {
    private static final long serialVersionUID = -3272442103797684059L;
    private Integer noticeId;

    private String noticeTitle;

    private String noticeContent;

    private Date noticeCreateTime;

    private Date noticeUpdateTime;
    //状态(显示/隐藏)
    private Integer noticeStatus;
    //滚动顺序
    private Integer noticeOrder;
}
