package test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestJedis {

	@Test
	public void testJedis() throws Exception {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("test", "helloworld");
		String str = jedis.get("test");
		System.out.println(str);
		jedis.close();
	}
	
}
