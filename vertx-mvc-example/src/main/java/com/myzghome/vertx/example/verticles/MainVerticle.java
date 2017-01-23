package com.myzghome.vertx.example.verticles;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

/**
 * 作者：周广
 * 创建时间：2017/1/22 0022
 * 必要描述:
 */
public class MainVerticle {

    public static void main(String[] args) {
        DeploymentOptions dos = new DeploymentOptions().setInstances(1);
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(WebServer.class.getName(), dos, ar -> {
            if (ar.succeeded()) {
                System.out.println("部署成功" + ar.result());
            } else {
                System.out.println("部署失败" + ar.cause());
            }
        });
    }


}
