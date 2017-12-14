package e3mall;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import e3mall.common.jedis.JedisClient;
import e3mall.common.jedis.JedisClientCluster;
import e3mall.common.jedis.JedisClientPool;

public class JedisClientTest {

	@Test
	public void testJedisClient() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("mytest", "testtesttest");
		String str = jedisClient.get("mytest");
		System.out.println(str);
	}
	
}
