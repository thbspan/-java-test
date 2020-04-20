package com.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class NioSocketServer {

    private volatile boolean flag;

    public void start() {
        // 创建一个ServerSocketChannel对象
        try (ServerSocketChannel serverSocketChannel = SelectorProvider.provider().openServerSocketChannel()) {
            // 绑定 8080端口
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            // 设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);

            // 为serverSocketChannel注册selector
            Selector selector = Selector.open();
            // 注册 OP_ACCEPT 事件；若传入0，表示未注册任何事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            ServerHandler serverHandler = new ServerHandler(selector);
            System.out.println("Server start work");
            while (flag) {
                System.out.println("server handler request");
                int selectNums = selector.select();
                if (selectNums == 0) {
                    continue;
                }
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    // 移除需要处理的KEY
                    keyIterator.remove();
                    if (!selectionKey.isValid()) {
                        continue;
                    }

                    // 注意：Channel 大多数情况下是可写的，所以不需要专门去注册 SelectionKey.OP_WRITE 事件
                    // 当写入失败时，可以尝试注册SelectionKey.OP_WRITE 事件
                    if (selectionKey.isAcceptable()) {
                        serverHandler.handleAccept(selectionKey);
                    } else if (selectionKey.isReadable()) {
                        serverHandler.handlerRead(selectionKey);
                    } else if (selectionKey.isWritable()) {
                        serverHandler.handlerWrite(selectionKey);
                    }
                }
            }
            System.out.println("Server end work");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NioSocketServer nioSocketServer = new NioSocketServer();
        nioSocketServer.setFlag(true);
        nioSocketServer.start();
    }
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
