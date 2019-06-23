package me.szp.study.springboot.redis;

import lombok.extern.slf4j.Slf4j;
import me.szp.study.springboot.redis.util.IdUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StudySpringbootRedisApplicationTests {

    @Autowired
    private IdUtils idUtils;


    @Test
    public void testGetId() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        idUtils.clear();

        for (int j = 0; j < 100; j++) {
            getId();
        }


    }

    public synchronized void getId() {

        idUtils.se
        int code = idUtils.getId();
        System.out.println("从redis获取的code: " + code);
        code = code + 1;
        String id = idUtils.generateId(code);
        System.out.println("生成的id:" + id);
        log.debug("生成的id:[{}]", id);
        idUtils.setId(code);
    }

}
