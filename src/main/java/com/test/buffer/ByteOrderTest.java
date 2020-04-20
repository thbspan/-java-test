package com.test.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteOrderTest {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        System.out.println("default java endian" + buffer.order());
        buffer.putShort((short) 1);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println("Now:" + buffer.order().toString());
        buffer.putShort((short) 2);
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(buffer.get() & 0xFF);
        }
        System.out.println("My PC:" + ByteOrder.nativeOrder().toString());
    }
}
