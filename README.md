# study
框架学习旅程

study-core 拥有简单的依赖注入和注解扩展（通过实现接口来被框架引入）
study-vertx-mvc vertx的辅助模块 现在只要继承AbstractImplVerticle 就可以让框架介入
例子：



public class WebServer extends AbstractImplVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        mainRouter.route("/").handler(StaticHandler.create("static").setIndexPage("index.html"));

        vertx.createHttpServer().
                requestHandler(mainRouter::accept).
                listen(config().getInteger("http.port", 8080), httpServerAsyncResult -> {
                    if (httpServerAsyncResult.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(httpServerAsyncResult.cause());
                    }
                });
    
}
