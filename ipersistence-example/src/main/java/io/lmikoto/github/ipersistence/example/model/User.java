package io.lmikoto.github.ipersistence.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author liuyang
 * 2020/9/26 3:32 下午
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;
}
