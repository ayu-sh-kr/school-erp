package dev.archimedes.image.utils;

import dev.archimedes.image.exception.ImageProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
public class ImageUtils {

    private ImageUtils(){}

    public static byte[] compressImage(byte[] data){

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] temp  = new byte[4 * 1024];
        while (!deflater.finished()){
            int size = deflater.deflate(temp);
            outputStream.write(temp, 0, size);
        }

        try {
            outputStream.close();
        }catch (Exception e){
            throw new ImageProcessingException("Failed to close output stream");
        }
        log.info(String.valueOf(outputStream.toByteArray().length));
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
            throw new ImageProcessingException("Failed to close output stream");
        }
        log.info(String.valueOf(outputStream.toByteArray().length));
        return outputStream.toByteArray();
    }

}
