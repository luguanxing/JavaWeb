#SSM���˲��ͣ�����, redis, solr��

###������־

```
day01-���Ŀ��ܡ���ɵ�¼�����롢�ǰ̨
	�SSM���
	ʵ����ʹ��mybatis���򹤳�����
	ʹ��shiro��ɵ�¼��֤
	�ǰ̨ҳ��͵�����ʽ
	
day02-��ӻ��棬����ʵ����
	��ɲ��͡�������Ϣ���������ӡ�����������ݿ������Լ���ȡ����ʾ
	����redis����һ���ֻ������ݷ�������(blogger��blogtype��links��priorityblogs)
	��redis���ϣ����ȴ�redis��ȡ����(��Service�и�)����������ݿ�ȡ�ٷ��뻺��
	ע��ÿ���������Ӷ�Ҫ��finally���ͷ�����jedis.close();��������ڴ�й©->ҳ�濨������Ӧ
	
day03-���Ʋ�����ʾ
	��ҳ���ʹ��bootstrap-paginator
	ʹ��mybatis�������ķ�ҳ���mybatis-pagehelper
	
day04-��ɲ������
	�����������Է�ҳ��ʽ��ʾ������µ����в���
	rest���/{typeId}/{page}
	������ͷ�ҳ������(��׳��)
	
day05-������ʾ����ҳ��顢����ǽ
	���һƪ�����ܹ���ʾ��������ҳ�������
	��ҳ��ʾ�����û�������
	�ܹ��������Բ��ڼ���ӵ�����ǽ��

day06-��������
	�solr������
		1.war����/usr/local/solr/��tomcat��webappĿ¼��
		2.��ѹ��ɾ��war��
		3.��������jar��(/example/lib/ext��)��webapp/WEB-INF/lib��
		4.��solrhome�ŵ�/usr/local/solr/solrhome��
			(ע����ʱ/usr/local/solr��tomcat��solrhome�����ļ���)
		5.�޸������ļ�webapp/WEB-INF/web.xml->ɾ��ע�ͣ�����<env-entry>·��
		6.����FieldType��ҵ���򣬷ִ���
			(1)��IKAnalyzer2012FF_u1.jar�ŵ�webapp/WEB-INF/lib��
			(2)�����ķִ�������չ�ʵ�������ļ��ŵ�webapp/WEB-INF/classes��(û��Ŀ¼�򴴽�)
			(3)��solrhome\collection1\conf\schemal.xml�����fieldType��ʹ��fieldType��ҵ����(id���ö���)
				<!-- IKAnalyzer-->
				<fieldType name="text_ik" class="solr.TextField">
				  <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
				</fieldType>

				<field name="blog_title" type="text_ik" indexed="true" stored="true"/>
				<field name="blog_image" type="string" indexed="false" stored="true" />
				<field name="blog_summary" type="text_ik" indexed="true" stored="true"/>
				<field name="blog_releaseDate" type="string" indexed="false" stored="true" />
				<field name="blog_content" type="text_ik" indexed="true" stored="true"/>

				<field name="blog_keyword" type="text_ik" indexed="true" stored="false" multiValued="true"/>
				<copyField source="blog_title" dest="blog_keyword"/>
				<copyField source="blog_summary" dest="blog_keyword"/>
				<copyField source="blog_content" dest="blog_keyword"/>
		7.����tomcat��ʹ��solrj��������
	ʹ��solrj��������(������)
	��������͸�������

day07-��ɺ�̨����ҳ��
	��ɺ�̨����ģ��
	ʹ�ø��ı��༭��ueditor
	���blogger�޸Ĺ��ܣ�ע��ÿ�θ��¶�Ҫͬ������(ɾ������)

	
```