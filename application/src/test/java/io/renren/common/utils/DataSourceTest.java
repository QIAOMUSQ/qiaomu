package io.renren.common.utils;


import com.andrService.dao.TestDao;

import com.andrService.entity.TestDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes ={TestDao.class})
public class DataSourceTest {

    @Autowired
    private TestDao testDao;

    @Test
    public void test(){
        //数据源1
        TestDataSource test = testDao.selectById(1l);
        System.out.println(test);
    }

}
