package com.nilesh.logsentinel.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class FastFileReader {
    private final Map<String, Long> lastKnownPositions = new HashMap<>();

    public String readNewContent(String filePath) throws IOException {
        long lastPos = lastKnownPositions.getOrDefault(filePath, 0L);
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long currentLength = file.length();
            if (currentLength < lastPos) lastPos = 0; // Handle log rotation

            file.seek(lastPos);
            byte[] bytes = new byte[(int) (currentLength - lastPos)];
            file.readFully(bytes);
            
            lastKnownPositions.put(filePath, currentLength);
            return new String(bytes);
        }
    }
}