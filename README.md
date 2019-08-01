# 快速搭建Springboot2.x + Shiro + JWT + Redis + Swagger脚手架

------
### 1. 技术栈
> * Springboot2.x
> * JWT
> * Shiro
> * Redis
> * Spring-Data-JPA
> * Swagger2.0
> * Druid数据库连接池


### 2. 项目描述
本项目主要用来学习Springboot整合Shiro+JWT做用户权限的控制，是一个maven多模块项目，创建了俩个模块，commons模块与server模块。

**项目模块介绍**

     - commons：公共模块
        -- config：  包含Reids、JWT、Shiro、Druid配置
        -- constant： 定义常量
        -- dto：封装了与前端交互的数据
        -- entity：封装的数据库实体
        -- exception：自定义异常类
        -- repository：对于数据库的操作（使用了jpa）
        -- result： 请求结果类与状态码类
        -- utils： 工具类
     - server：服务模块
        -- controller: 接口的入口
        -- exceptionHandle: 全局异常处理
        -- service： 服务类
        -- Application.java：启动类

### 3. 项目启动
    1). 将resources/ych.sql文件导入到数据库中。
    2). 修改application.yml中mysql与redis配置
    3). 运行Application.java的main()方法
    4). 在页面中输入http://localhost:8888/swagger-ui.html测试接口



**鸣谢**
非常感谢YupaiTS这位大神，我的这个项目由他的项目整合而来的， 他的GitHub网址是：https://github.com/YupaiTS