package io.github.lmikoto.ipersistence.sql;

import io.github.lmikoto.ipersistence.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 * 2020/9/27 7:35 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BandSql {

    private String sqlText;

    private List<ParameterMapping> parameterMappingList = new ArrayList<>();
}
