package io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

/**
 * 计算文件的32位循环冗余校验和（CRC32）
 *
 * @author zt1994 2019/5/20 22:39
 */
public class MemoryMapTest {

    /**
     * 校验输入sum
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static long checksumInputStream(Path filename) throws IOException {
        try (InputStream in = Files.newInputStream(filename)) {
            CRC32 crc32 = new CRC32();

            int c;
            while ((c = in.read()) != -1) {
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }


    /**
     * 校验Buffered输入sum
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static long checksumBufferedInputStream(Path filename) throws IOException {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(filename))) {
            CRC32 crc32 = new CRC32();

            int c;
            while ((c = in.read()) != -1) {
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }


    /**
     * 校验RandomAccessFile sum
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static long checksumRandomAccessFile(Path filename) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filename.toFile(), "r")) {
            long length = file.length();
            CRC32 crc32 = new CRC32();

            for (long p = 0; p < length; p++) {
                file.seek(p);
                int c = file.readByte();
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }


    /**
     * 校验MappedFile sum
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static long checksumMappedFile(Path filename) throws IOException {
        try (FileChannel channel = FileChannel.open(filename)) {
            CRC32 crc32 = new CRC32();
            int length = (int) channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

            for (int p = 0; p < length; p++) {
                int c = buffer.get(p);
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }


    /**
     * 主方法
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Input Stream:");
        long start = System.currentTimeMillis();
        Path filename = Paths.get("F:\\develop\\javacore\\src\\io\\test");
        long crcValue = checksumInputStream(filename);
        long end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");

        System.out.println("Buffered Input Stream:");
        start = System.currentTimeMillis();
        crcValue = checksumBufferedInputStream(filename);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");

        System.out.println("Random Access File:");
        start = System.currentTimeMillis();
        crcValue = checksumRandomAccessFile(filename);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");

        System.out.println("Random Access File:");
        start = System.currentTimeMillis();
        crcValue = checksumMappedFile(filename);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");
    }
}
