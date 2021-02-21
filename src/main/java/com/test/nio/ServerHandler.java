package com.test.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ServerHandler {

    private final Selector selector;
    private final int bufferSize = 1024;

    public ServerHandler(Selector selector) {
        this.selector = selector;
    }


    public void handleAccept(SelectionKey selectionKey) {
        try {
            // 获取channel
            SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
            // 设置非阻塞
            socketChannel.configureBlocking(false);
            // 注册读取事件
            // socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("server handle client connect event");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String handlerRead(SelectionKey selectionKey) {
        // 多路复用,拿到客户端的引用
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        // ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        System.out.println("handle read event");
        String receivedMsg = null;
        try {
            if (socketChannel.read(buffer) == -1) {
                // 连接已经断开了，重新连接
                System.out.println("断开 Channel");
                socketChannel.register(selector, 0);
            } else {
                // 将buffer改为读取状态
                buffer.flip();
                receivedMsg = StandardCharsets.UTF_8.newDecoder().decode(buffer).toString();
                if ("bye".equals(receivedMsg)) {
                    // 没有读到内容
                    socketChannel.shutdownOutput();
                    socketChannel.shutdownInput();
                    socketChannel.close();
                    System.out.println("断开连接");
                } else {
                    buffer.clear();
                    buffer.put(("received string:" + receivedMsg).getBytes(StandardCharsets.UTF_8));
                    // 读取模式
                    buffer.flip();
                    // 注册写入事件
                    SelectionKey key = socketChannel.register(selector, SelectionKey.OP_WRITE);
                    // 在selector上附加要写入的内容，下次写入
                    // System.out.println("key equals:" + (key == selectionKey)); //true
                    key.attach(buffer);
                    // 或者 socketChannel.register(selector, SelectionKey.OP_WRITE, buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receivedMsg;
    }

    public void handlerWrite(SelectionKey selectionKey) {
        // 多路复用,拿到客户端的引用
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        // 拿到附件
        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        try {
            // 写入数据
            socketChannel.write(buffer);
            // 继续注册读取事件
            // socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
