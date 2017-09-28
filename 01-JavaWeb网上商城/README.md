# JavaWeb网上商城

<br><br>

## 简单需求分析
![img](https://github.com/luguanxing/JavaWeb/blob/master/01-JavaWeb%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E/pictures/01-%E7%AE%80%E5%8D%95%E9%9C%80%E6%B1%82%E5%88%86%E6%9E%90.jpg?raw=true)

##### 面向用户部分功能
```
  (1) 登录功能。如果是网上商城的用户，购买前需填写用户名、密码和验证码即可享受商城服务。
  (2) 注册功能。顾客首先要注册为网上商城的用户。注册时只要填写登录用户名、密码等信息并激活。
  (3) 选择产品功能。顾客浏览网上商城，将自己需求的产品放入到购物车中，可连续添加商品。
  (4) 管理购物车。顾客选择完商品后可进入购物车页面，查看自己要购买的商品，可修改商品数量、取消某商品和清空购物车。
  (5) 付款功能。顾客在订单被销售方确认后，要选择付款方式，并付款给销售方，然后才可以收到货。 
```

##### 后台管理部分功能
```
  (1) 管理订单功能。管理员可以查询订单，根据用户是否确认、是否付款、是否 发货、是否归档将订单分类并进行管理。
  (2) 管理商品功能。管理员可以添加、查询、修改、删除商品。
  (3) 订单管理功能。管理员可以查看管理员和用户订单信息并修改。
```

<br><br>

## 实体分析
![img](https://github.com/luguanxing/JavaWeb/blob/master/01-JavaWeb%E7%BD%91%E4%B8%8A%E5%95%86%E5%9F%8E/pictures/02-%E5%AE%9E%E4%BD%93%E5%88%86%E6%9E%90.jpg?raw=true)
```
  用户和订单:一对多
  商品和订单:多对多
  分类和商品:一对多	
```

<br><br>

## 开发日志
```
01-需求和实体分析、搭建开发环境、整合资源、分析抽取servlet
    servlet抽取:
      之前编写的servlet的问题:
        1.doget每次请求都会执行--- 重写service
        2.用了大量 if else if 判断执行的是那个方法让方法执行
          Method method = this.getClass().getMethod(mt, HttpServletRequest.class,HttpServletResponse.class);
          method.invoke(this, request,response);
        3.每个方法执行的结果无非就是请求转发或者重定向或者打印数据
          让所有的方法都返回一个字符串
            若最后的结果需要请求转发,就把转发的路径给返回
            若最后的结果不需要请求转发,就返回一个null
            String path=method.invoke(this, request,response);
            if(path != null){
              request.getx(path).forward(...)
            }
        4.所有servlet的service中的代码都一样
          向上继续抽取
          编写一个BaseServlet,将之前service方法中的代码复制过来即可,
          然所有的servlet都继承baseservlet即可
          不同模块各自的方法均通过反射调用
        5.统一的错误页面

02-完成用户模块(servlet部分+注册+检验+登录+退出功能)
      把所有的关于xxx的操作,封装xxxservlet中
      web请求->servlet获取数据并拼装成对象->调用service方法,返回或显示结果->service可能调用dao完成数据库相关操作

03-完成前台商品模块(servlet部分+商品分类+商品列表+商品详情+分页)
      ajax获取请求显示分类
      转发到商品列表
      转发到商品详情
      静态包含显示商品信息(使库只包含一次)
      使用pageBean化简分页操作
      
04-解耦(工厂模式+XML+反射)和购物车模块(购物车、购物项+相关操作)订单提交模块(订单、订单项+事务+相关操作)
      购物车设计
        添加到购物车
        从购物车移除一个商品
        清空购物车
      订单实体
        user对象
        List<OrderItem>
      订单项实体
        Order对象
        Product对象


```
