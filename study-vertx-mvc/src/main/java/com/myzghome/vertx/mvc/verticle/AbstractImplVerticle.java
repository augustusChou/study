package com.myzghome.vertx.mvc.verticle;

import com.alibaba.fastjson.JSON;
import com.myzghome.core.context.AbstractApplicationContext;
import com.myzghome.core.context.AnnotationApplicationContext;
import com.myzghome.vertx.mvc.bean.VertxBeanFactory;
import com.myzghome.vertx.mvc.exception.Result;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.io.InputStream;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

/**
 * 作者：周广
 * 创建时间：2017/1/22 0022
 * 必要描述:
 */
public abstract class AbstractImplVerticle extends AbstractVerticle {

    private static final String APPLICATION_JSON = "application/json";
    protected static AbstractApplicationContext applicationContext;
    protected static JsonObject config;
    protected Router mainRouter;

    protected static JsonObject getConfig(String jsonPath) throws Exception {
        InputStream in = AbstractImplVerticle.class.getResourceAsStream(jsonPath);
        byte[] content = new byte[in.available()];
        in.read(content);
        Buffer buf = Buffer.buffer(content);
        return buf.toJsonObject();
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        setMainRouter(vertx);

        if (applicationContext == null) {
            try {
                config = getConfig("/conf.json");
                applicationContext = new AnnotationApplicationContext(new String[]{config.getString("scanPackage")}, new VertxBeanFactory(vertx, mainRouter));
                applicationContext.refresh();
                //获取一个默认的配置
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setMainRouter(Vertx vertx) {
        mainRouter = Router.router(vertx);
        mainRouter.route().consumes(APPLICATION_JSON);
        mainRouter.route().produces(APPLICATION_JSON);
        mainRouter.route().handler(BodyHandler.create());
        //异常处理器
        mainRouter.route().failureHandler(routingContext -> {
            failureHandler(routingContext, routingContext.failure());
        });
        mainRouter.route().handler(routingContext -> {
            routingContext.response().headers().add(CONTENT_TYPE, APPLICATION_JSON);
            routingContext.next();
        });
    }

    protected void failureHandler(RoutingContext routingContext, Throwable throwable) {
        routingContext.response().setStatusCode(500).end(JSON.toJSONString(new Result(Result.FAIL, throwable.getCause().getMessage())));
    }

}
