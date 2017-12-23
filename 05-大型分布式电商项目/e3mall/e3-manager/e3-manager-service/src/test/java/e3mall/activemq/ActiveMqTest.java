package e3mall.activemq;

import java.util.function.Consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ConsumerId;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class ActiveMqTest {

	//点到点形式发送消息
	@Test
	public void testQueueProducer() throws Exception {
		//1.创建连接工厂对象,连接activemq
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
		//2.使用工厂对象创建connection
		Connection connection = connectionFactory.createConnection();
		//3.开启连接,调用start方法
		connection.start();
		//4.创建session对象,参数分别为是否开启事务(一般不开,开启时第二个参数无意义),应答模式(自动应答和手动应答,一般用自动应答)
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用session创建destination,有queue和topic两种形式
		Queue queue = session.createQueue("test-queue");
		//6.使用session创建producer
		MessageProducer producer = session.createProducer(queue);
		//7.创建一个Message对象,可以使用TextMessage
		//TextMessage textMessage = new ActiveMQTextMessage();
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("hello activemq");
		//8.发送消息
		producer.send(textMessage);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	//点到点形式接收消息
	@Test
	public void testQueueConsumer() throws Exception {
		//1.创建连接工厂对象,连接activemq
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
		//2.使用工厂对象创建connection
		Connection connection = connectionFactory.createConnection();
		//3.开启连接,调用start方法
		connection.start();
		//4.创建session对象,参数分别为是否开启事务(一般不开,开启时第二个参数无意义),应答模式(自动应答和手动应答,一般用自动应答)
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用session创建destination,有queue和topic两种形式
		Queue queue = session.createQueue("spring-queue");
		//6.使用session创建consumer
		MessageConsumer consumer = session.createConsumer(queue);
		//7.打印消息
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println(text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//8.等待接收消息
		System.in.read();
		//9.关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	//广播形式发送消息
	@Test
	public void testTopicProducer() throws Exception {
		//1.创建连接工厂对象,连接activemq
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
		//2.使用工厂对象创建connection
		Connection connection = connectionFactory.createConnection();
		//3.开启连接,调用start方法
		connection.start();
		//4.创建session对象,参数分别为是否开启事务(一般不开,开启时第二个参数无意义),应答模式(自动应答和手动应答,一般用自动应答)
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用session创建destination,有queue和topic两种形式
		Topic topic = session.createTopic("test-topic");
		//6.使用session创建producer
		MessageProducer producer = session.createProducer(topic);
		//7.创建一个Message对象,可以使用TextMessage
		//TextMessage textMessage = new ActiveMQTextMessage();
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("topic message");
		//8.发送消息
		producer.send(textMessage);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	//广播形式接收消息
	@Test
	public void testTopicConsumer() throws Exception {
		//1.创建连接工厂对象,连接activemq
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
		//2.使用工厂对象创建connection
		Connection connection = connectionFactory.createConnection();
		//3.开启连接,调用start方法
		connection.start();
		//4.创建session对象,参数分别为是否开启事务(一般不开,开启时第二个参数无意义),应答模式(自动应答和手动应答,一般用自动应答)
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用session创建destination,有queue和topic两种形式
		Topic topic = session.createTopic("test-topic");
		//6.使用session创建consumer
		MessageConsumer consumer = session.createConsumer(topic);
		//7.打印消息
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println(text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("消费者2已经启动");
		//8.等待接收消息
		System.in.read();
		//9.关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
}
