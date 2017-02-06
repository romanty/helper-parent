package com.study.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yutong on 2016/12/9.
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
//        InputStream inputStream = System.in;
//
//        File img = new File("/Users/apple/Documents/122.jpg");
//        FileOutputStream outputStream = new FileOutputStream(img);
//        byte[] arr = new byte[1024];
//        while (inputStream.read(arr) != -1){
//            outputStream.write(arr);
//        }
//        outputStream.close();
//        inputStream.close();

//        BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/apple/Downloads/新建文件夹/city.data.js")));
//        String str;
//        StringBuilder sb = new StringBuilder();
//        while ((str =reader.readLine()) != null) {
//            sb.append(str);
//        }
//
//        JSONArray jsonArray = JSON.parseArray(sb.toString());
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//        for (int i=0; i< jsonArray.size(); i++) {
//            JSONObject _temp = jsonArray.getJSONObject(i);
//            JSONObject p = new JSONObject();
//            p.put("p", _temp.getString("text"));
//
//            JSONArray _arr = _temp.getJSONArray("children");
//            JSONArray _n_arr = new JSONArray();
//            for (int j=0; j<_arr.size(); j++) {
//                JSONObject _child = _arr.getJSONObject(j);
//                JSONObject _n_child = new JSONObject();
//                _n_child.put("n", _child.getString("text"));
//                _n_arr.add(_n_child);
//            }
//            p.put("c", _n_arr);
//
//            array.add(p);
//        }
//        jsonObject.put("citylist", array);
//        reader.close();
//        System.out.println(jsonObject);

        String filename = "43033@0@http://inbornpai-test.img-cn-beijing.aliyuncs.com/1/418650b149154d0dbdced5b6457740a7.png?w=828&h=552&s=714381@1/418650b149154d0dbdced5b6457740a7.png";
        int end = filename.contains("?") ? filename.indexOf("?") : filename.length();
        filename = filename.substring(filename.indexOf(".com/") + 5, end);
        System.out.println(filename);
    }
}
