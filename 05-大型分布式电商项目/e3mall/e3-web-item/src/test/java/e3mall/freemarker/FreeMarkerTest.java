package e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {

	@Test
	public void testFreeMarker() throws Exception {
		/*
		 	创建模板使用步骤:
			1.创建模板文件
			2.创建configuration对象
			3.设置保存目录和编码格式(一般UTF-8)
			4.加载模板文件,创建模板对象
			5.创建数据集model,可以是pojo或map(推荐使用map)
			6.创建writer对象,指定输出文件路径和文件名
			7.生成静态页面
			8.关闭流
		 */
		Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("C:/Users/Administrator/workspace_e3mall/e3-web-item/src/main/webapp/WEB-INF/ftl"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("hello.ftl");
		Map model = new HashMap<>();
		model.put("hello", "hello freemarker!");
		Writer out = new FileWriter(new File("D:/temp/hello.txt"));
		template.process(model, out);
		out.close();
	}
	
}
