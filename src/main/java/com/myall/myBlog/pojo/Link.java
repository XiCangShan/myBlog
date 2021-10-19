package com.myall.myBlog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Link  implements Serializable {

    private static final long serialVersionUID = 7401298112612382026L;
    private Integer linkId;
    private String linkUrl;
    private String linkName;
    private String linkImage;
    private String linkDescription;
    private String linkOwnerNickname;
    private String linkOwnerContact;
    private Date linkUpdateTime;
    private Date linkCreateTime;
    private Integer linkOrder;
    private Integer linkStatus;
}
