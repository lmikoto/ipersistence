package io.github.lmikoto.ipersistence.model;

import lombok.Data;

/**
 * @author liuyang
 * 2020/9/26 5:01 下午
 */
@Data
public class MappedStatement {

    private String id;

    private String parameterType;

    private String resultType;

    private String sql;
}
