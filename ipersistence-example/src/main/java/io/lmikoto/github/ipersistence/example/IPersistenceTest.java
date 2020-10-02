package io.lmikoto.github.ipersistence.example;

import io.github.lmikoto.ipersistence.io.Resource;
import io.github.lmikoto.ipersistence.sql.SqlSession;
import io.github.lmikoto.ipersistence.sql.SqlSessionFactory;
import io.github.lmikoto.ipersistence.sql.SqlSessionFactoryBuilder;
import io.lmikoto.github.ipersistence.example.dao.UserDao;
import io.lmikoto.github.ipersistence.example.model.User;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuyang
 * 2020/9/26 7:36 下午
 */
public class IPersistenceTest {

    private SqlSession sqlSession;

    private UserDao userDao;

    @Before
    public void before(){
        InputStream in = Resource.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }



    @Test
    public void select() {
        User user = new User(1L,"张三");
        User result = userDao.findByCondition(user);
        List<User> userList = userDao.findAll();
        System.out.println(result);
        System.out.println(userList);
    }

    @Test
    public void create(){
        User user = new User(4L,"tom");
        userDao.insert(user);
    }

    @Test
    public void update(){
        User user = new User(4L,"jerry");
        userDao.update(user);
    }

    @Test
    public void delete(){
        User user = new User();
        user.setId(4L);
        userDao.delete(user);
    }
}
