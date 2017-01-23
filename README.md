# study
框架学习旅程

study-core 拥有简单的依赖注入和注解扩展（通过实现接口来被框架引入）
study-vertx-mvc vertx的辅助模块 现在只要继承AbstractImplVerticle 就可以让框架介入
引入很简单，加入jar(study-vertx-mvc)包后 将继承AbstractVerticle的改为继承AbstractImplVerticle
其他不变 注册路由就使用注解 具体使用可以查看 vertx-mvc-example