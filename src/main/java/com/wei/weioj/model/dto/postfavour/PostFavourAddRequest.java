package com.wei.weioj.model.dto.postfavour;

import lombok.Data;

import java.io.Serializable;

/**
 * 帖子收藏 / 取消收藏请求
 *
 *  @author <a href="https://github.com/7Ailun">艾伦</a>
 *   Allen
 */
@Data
public class PostFavourAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}