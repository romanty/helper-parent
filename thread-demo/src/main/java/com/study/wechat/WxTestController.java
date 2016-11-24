package com.study.wechat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.util.CipherUtil;
import com.study.util.HttpUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yutong on 2016/10/27.
 */
@WebServlet("/wx/test")
public class WxTestController extends HttpServlet {

    private static final String token = "DDA12A3CFBA7463E900FFDC8383010B9";

    private static final String app_id = "";

    private static final String app_secret = "";

    private static final String access_token = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    private static final String user_info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        access(request, response);
    }

    /**
     * 验证URL真实性
     *
     * @param request 请求
     * @param response 响应
     * @return String
     */
    private String access(HttpServletRequest request, HttpServletResponse response) {
        // 验证URL真实性
        System.out.println("进入验证access");
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
        List<String> params = new ArrayList<>();
        params.add(token);
        params.add(timestamp);
        params.add(nonce);
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        Collections.sort(params, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        String temp = CipherUtil.encrypyBySHA1(params.get(0) + params.get(1) + params.get(2));
        if (temp != null && temp.equals(signature)) {
            try {
                response.getWriter().write(echostr);
                System.out.println("成功返回 echostr：" + echostr);
                return echostr;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("失败 认证");
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            // 从request中取得输入流
            inputStream = request.getInputStream();
            /*
             * 读取request的body内容 此方法会导致流读取问题 Premature end of file. Nested exception:
             * Premature end of file String requestBody =
             * inputStream2String(inputStream); System.out.println(requestBody);
             */
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List elementList = root.elements();

            // 遍历所有子节点
            for (Object e : elementList) {
                Element element = (Element) e;
                map.put(element.getName(), element.getText());
            }

            System.out.println("map = [" + JSON.toJSONString(map) + "]");

            String openId = map.get("FromUserName");


            // 获取access_token,{"access_token":"ACCESS_TOKEN","expires_in":7200}
            String access_resp = HttpUtil.getRequest(String.format(access_token, app_id, app_secret));
            JSONObject access_json = JSON.parseObject(access_resp);
            String accessToken = access_json.getString("access_token");

            String user_info_resp = HttpUtil.getRequest(String.format(user_info, accessToken, openId));
            JSONObject user_info_json = JSON.parseObject(user_info_resp);

            // TODO 将用户资料和openId存入文件系统中
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                // 释放资源
                inputStream.close();
            }
        }
    }
}
