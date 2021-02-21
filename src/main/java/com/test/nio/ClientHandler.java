package com.test.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ClientHandler {
    private final Selector selector;
    private final int bufferSize = 1024;

    public ClientHandler(Selector selector) {
        this.selector = selector;
    }


    /**
     * 客户端不会触发当前方法
     */
    public void handleAccept(SelectionKey selectionKey) {
        // 获取channel
        try {
            SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
            // 设置非阻塞
            socketChannel.configureBlocking(false);
            // 注册读取事件
            // socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
            socketChannel.register(selector, SelectionKey.OP_WRITE);
            System.out.println("客户端建立请求");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlerWrite(SelectionKey selectionKey, String message) {
        // 多路复用,拿到客户端的引用
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        try {
            // 写入数据
            socketChannel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
            // 继续注册读取事件
            // socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String handlerRead(SelectionKey selectionKey) {
        // 多路复用,拿到客户端的引用
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        // ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

        String receivedMsg = null;
        try {
            if (socketChannel.read(buffer) != -1) {
                // 讲buffer改为读取状态
                buffer.flip();
                receivedMsg = StandardCharsets.UTF_8.newDecoder().decode(buffer).toString();
                buffer.clear();
                // 注册写入事件
                socketChannel.register(selector, SelectionKey.OP_WRITE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receivedMsg;
    }

    /**
     * 处理连接就绪事件
     */
    public void handleConnect(SocketChannel socketChannel) throws IOException {
        // 判断是否正常连接，但是还没有完成，需要调用finishConnect完成连接。可以参考javadoc
        if (!socketChannel.isConnectionPending()) {
            return;
        }
        // 完成连接
        if (!socketChannel.finishConnect()) {
            System.out.println("failed to connect the server");
        }
        // 注册写事件，发送消息到服务端；channel默认是可写的，可以不用注册
        socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
    }
}
