package com.myzghome.vertx.example.verticles;

import com.myzghome.vertx.mvc.verticle.AbstractImplVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * 作者：周广
 * 创建时间：2017/1/18 0018
 * 必要描述:
 */
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

}
