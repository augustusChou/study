package com.myzghome.vertx.mvc.verticle;

import com.myzghome.core.context.AbstractApplicationContext;
import com.myzghome.core.context.AnnotationApplicationContext;
import com.myzghome.vertx.mvc.bean.VertxBeanFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.io.InputStream;

/**
 * 作者：周广
 * 创建时间：2017/1/22 0022
 * 必要描述:
 */
public abstract class AbstractImplVerticle extends AbstractVerticle {

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
        mainRouter = Router.router(vertx);
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

}
