package io.github.lmikoto.ipersistence.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 * 2020/9/27 7:48 下午
 */
@Data
public class ParameterMappingTokenHandler implements TokenHandler {
    private List<ParameterMapping> parameterMappings = new ArrayList<>();
    @Override
    public String handlerToken(String content) {
        parameterMappings.add(new ParameterMapping(content));
        return "?";
    }
}
