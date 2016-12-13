package com.study.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yutong on 2016/12/9.
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;

        File img = new File("/Users/apple/Documents/122.jpg");
        FileOutputStream outputStream = new FileOutputStream(img);
        byte[] arr = new byte[1024];
        while (inputStream.read(arr) != -1){
            outputStream.write(arr);
        }
        outputStream.close();
        inputStream.close();
    }
}
