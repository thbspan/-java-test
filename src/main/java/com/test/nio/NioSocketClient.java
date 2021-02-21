package com.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class NioSocketClient {

    private final CountDownLatch connected = new CountDownLatch(1);

    public void start() {
        // 打开 socketChannel
        try {
            SocketChannel socketChannel = SocketChannel.open();
            // 设置为非阻塞模式
            socketChannel.configureBlocking(false);
            // 客户端指定一个端口和服务端通信。如果不指定，客户端会随机选择一个空闲的端口
            // socketChannel.bind(new InetSocketAddress("localhost", 7070));
            // 创建Selector
            Selector selector = Selector.open();
            // 为socketChannel注册selector
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            // 链接服务的socket
            socketChannel.connect(new InetSocketAddress(9999));
            new Thread(() -> {
                try {
                    handleKeys(socketChannel, selector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "client-handle-key").start();
            connected.await();
            System.out.println("Client start complete.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleKeys(SocketChannel socketChannel, Selector selector) throws IOException {
        ClientHandler clientHandler = new ClientHandler(selector);

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
                    int readyOps = selectionKey.readyOps();

                    if ((readyOps & SelectionKey.OP_CONNECT) != 0) {
                        // 客户端连接就绪事件处理
                        clientHandler.handleConnect((SocketChannel) selectionKey.channel());
                        connected.countDown();
                    }
                    if ((readyOps & SelectionKey.OP_ACCEPT) != 0) {
                        clientHandler.handleAccept(selectionKey);
                    }
                    if ((readyOps & SelectionKey.OP_READ) != 0) {
                        System.out.println(clientHandler.handlerRead(selectionKey));
                    }
                    if ((readyOps & SelectionKey.OP_WRITE) != 0) {
                        message = scan.nextLine();
                        if (message != null && message.length() > 0) {
                            clientHandler.handlerWrite(selectionKey, message);
                        }
                    }
                }
            } while (!"bye".equals(message));
            socketChannel.shutdownOutput();
            socketChannel.shutdownInput();
            socketChannel.close();
            System.out.println("client close connection.");
        }
    }

    public static void main(String[] args) {
        new NioSocketClient().start();
    }
}
