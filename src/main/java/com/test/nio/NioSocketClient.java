package com.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NioSocketClient {

    public void start() {
        // 打开 socketChannel
        try (SocketChannel socketChannel = SocketChannel.open()) {
            // 设置为非阻塞模式
            socketChannel.configureBlocking(false);
            // 客户端指定端口和服务端通信
            socketChannel.bind(new InetSocketAddress("localhost", 7070));
            // 链接服务的socket
            socketChannel.connect(new InetSocketAddress("localhost", 8080));

            // 链接服务的socket
            socketChannel.connect(new InetSocketAddress(8080));

            // 为serverSocketChannel注册selector
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            // 完成连接
            if (!socketChannel.finishConnect()) {
                System.out.println("failed to connect the server");
                return;
            }

            ClientHandler clientHandler = new ClientHandler(selector);

            System.out.println("Client start work");

            try (Scanner scan = new Scanner(System.in)) {
                System.out.println("client handler request");
                String message = null;
                do {
                    int selectNums = selector.select(30 * 1000L);
                    if (selectNums == 0) {
                        continue;
                    }

                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey selectionKey = keyIterator.next();
                        // 移除需要处理的key
                        keyIterator.remove();

                        if (!selectionKey.isValid()) {
                            continue;
                        }
                        if (selectionKey.isAcceptable()) {
                            clientHandler.handleAccept(selectionKey);
                        } else if (selectionKey.isReadable()) {
                            System.out.println(clientHandler.handlerRead(selectionKey));
                        } else if (selectionKey.isWritable()) {
                            message = scan.nextLine();
                            clientHandler.handlerWrite(selectionKey, message);
                        }

                    }
                } while (!"bye".equals(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioSocketClient().start();
    }
}
