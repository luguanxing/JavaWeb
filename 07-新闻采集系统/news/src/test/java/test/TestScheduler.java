package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//junit启动spring容器注解，不用手动创建容器了
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration   ({"/spring/spring-*.xml"}) //加载配置文件  
public class TestScheduler {

    @Test
    public void testTimer() throws Exception {
    	while (true) {
    		Thread.sleep(1000);
    	}
    }
    
}
