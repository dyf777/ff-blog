package org.blog.mapper;

import org.blog.model.BlogTag;

public interface BlogTagMapper {
    int insert(BlogTag record);

    int insertSelective(BlogTag record);
}