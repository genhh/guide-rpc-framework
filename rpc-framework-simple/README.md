# 涉及到的技术点

# 流程整理

## netty版本

- 启动NettyClientMain**客户端**，@RpcScan通过CustomScannerRegistrar实现spring对应接口ImportBeanDefinitionRegistrar进行注册
- 调用AnnotationConfigApplicationContext配置类，扫描github.javaguide下的包，获取helloController类，
- 调用controller.test()方法，
- test()调用helloService.hello接口方法,调用服务处标注@RpcReference,
- @rpcReference通过BeanPostProcessor接口使得spring容器中各类初始化后自定义逻辑--自动实现标注RpcReference类的动态代理
- 代理对象通过RpcClientProxy类实现*InvocationHandler接口*，包装RpcRequestTransport类，发送request类到**服务端**
- 序列化？协议？netty的传输过程?
- 启动NettyServiceMain**服务端**，找到接口对应实现类，然后包装成response返回**客户端**

实现类在example-server文件夹下,helloServiceImpl标注@RpcService
使得可以@rpcscan扫描rpc系列包自动注册，
@rpcService/@rpcReference标注对应使用rpc的实现类和接口，


# 疑问
- 客户端注入接口时在包装实现类代理对象前如何实现类暂时性实例的创建？接口会创建默认实例
- extensionLoader是什么? spi优化实现，包含了对已缓存的实现类快速调用，指定实现类的加载而非全部加载
- spi机制理解实现? 
- 注册中心服务结果缓存是如何做到的? 好像还没做，本地-全局-分布式缓存，在远程调用前利用拦截判断是否有缓存结果，缓存策略有lru,fifo,soft.weak,ttl，缓存更新
- 客户端异步调用是如何实现的? 
- 停止时是如何结束未完成任务的?
- NettyRpcServerHandler?netty机制?

# 待优化点
- [ ] **增加可配置比如序列化方式、注册中心的实现方式,避免硬编码** ：1定义配置接口 2创建配置类 3配置文件 4依赖注入配置类 5添加注解扫描
- [ ] **服务监控中心（类似dubbo admin）**
- [ ] 结果缓存