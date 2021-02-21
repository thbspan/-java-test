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
            // 设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);

            // 创建Selector
            Selector selector = Selector.open();
            // 为serverSocketChannel注册selector，注册 OP_ACCEPT 事件；若传入0，表示未注册任何事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // 绑定 8080端口
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));

            System.out.println("Server start work");
            handleKeys(selector);
            System.out.println("Server end work");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleKeys(Selector selector) throws IOException {
        ServerHandler serverHandler = new ServerHandler(selector);
        System.out.println("server handler request");
        while (flag) {
            // 通过selector选择channel；每30s阻塞等待就绪的IO事件
            int selectNums = selector.select(30 * 1000L);
            if (selectNums == 0) {
                continue;
            }
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                // 移除需要处理的KEY
                keyIterator.remove();
                if (!selectionKey.isValid()) {
                    // 忽略无效的selectionKey
                    continue;
                }

                int readyOps = selectionKey.readyOps();
                // 注意：Channel 大多数情况下是可写的，所以不需要专门去注册 SelectionKey.OP_WRITE 事件
                // 当写入失败时，可以尝试注册 SelectionKey.OP_WRITE 事件
                if ((readyOps & SelectionKey.OP_ACCEPT) != 0) {
                    // 连接就绪事件，也就是客户端请求建立连接
                    serverHandler.handleAccept(selectionKey);
                }
                if ((readyOps & SelectionKey.OP_READ) != 0) {
                    serverHandler.handlerRead(selectionKey);
                }
                if ((readyOps & SelectionKey.OP_WRITE) != 0) {
                    serverHandler.handlerWrite(selectionKey);
                }
            }
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
