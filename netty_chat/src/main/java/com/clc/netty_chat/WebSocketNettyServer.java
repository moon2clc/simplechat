package com.clc.netty_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Create by knight
 * 2019/6/18 13:27
 * Description: can be null.
 */
public class WebSocketNettyServer {

    public static void main(String[] args) {

        // 创建两个线程池
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            // 创建netty服务器启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 初始化服务器启动对象
            serverBootstrap
                    // 指定使用上面的两个线程池
                    .group(mainGroup, subGroup)
                    // 指定netty通道类型
                    .channel(NioServerSocketChannel.class)
                    // 指定通道初始化器用来加载当Channel收到事件消息后,如何进行业务处理
                    .childHandler(new WebSocketChannelInitializer());

            // 绑定服务器端口,以同步的方式启动服务器
            ChannelFuture future = serverBootstrap.bind(9090).sync();
            // 等待服务器关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭服务器
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
