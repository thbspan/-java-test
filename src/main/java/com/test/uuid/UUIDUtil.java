package com.test.uuid;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class UUIDUtil {

    public static String toBase64() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());

        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }
}
