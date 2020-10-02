package io.lmikoto.github.ipersistence.example.dao;

import io.lmikoto.github.ipersistence.example.model.User;

import java.util.List;

/**
 * @author liuyang
 * 2020/9/28 7:34 下午
 */
public interface UserDao {

    /**
     * 查询所有 user
     * @return
     */
    List<User> findAll();


    /**
     * 条件查询
     * @param user
     * @return
     */
    User findByCondition(User user);

    /**
     * 创建
     * @param user
     * @return
     */
    Integer insert(User user);

    /**
     * 更新
     * @param user
     * @return
     */
    Integer update(User user);


    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(User id);


}
