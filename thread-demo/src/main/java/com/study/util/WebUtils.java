package com.study.util;

import com.study.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 400
 */
public class WebUtils {

    private static Logger log = LoggerFactory.getLogger(WebUtils.class);

    /**
     * contentType常量，application/json;charset=UTF-8或者text/html;charset=UTF-8
     */
//    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    /**
     * (成功操作时)输入字符到response
     *
     * @param response response
     * @param object   输出字符
     */
    public static void printOnPage(HttpServletResponse response, Object object) {
        response.setHeader("status", Constants.sucessStatus);
        response.setContentType(CONTENT_TYPE);
        response(response, object);
    }

    /**
     * 没有登录response响应--只能拦截器里使用
     *
     * @param response response
     */
    public static void noLoginOnPage(HttpServletResponse response) {
        response.setHeader("status", Constants.logonStatus);
    }

    /**
     * 下线通知response响应--只能拦截器里使用
     *
     * @param response response
     */
    public static void offlineOnPage(HttpServletResponse response) {
        response.setHeader("status", Constants.offlineStatus);
    }

    /**
     * (操作业务失败时)输入字符到response
     *
     * @param response response
     * @param object   输出字符
     */
    public static void failOnPage(HttpServletResponse response, Object object) {
        response.setHeader("status", Constants.failStatus);
        response.setContentType(CONTENT_TYPE);
        response(response, object);
    }

    /**
     * (登录失败(出现验证码))输入字符到response
     *
     * @param response response
     * @param object   输出字符
     */
    public static void loginFailOnPage(HttpServletResponse response, Object object) {
        response.setHeader("status", Constants.loginFailStatus);
        response.setContentType(CONTENT_TYPE);
        response(response, object);
    }


    /**
     * 数据库或者系统异常(不包括可以处理的异常如唯一性约束异常)
     *
     * @param response response
     */
    public static void exceptionOnPage(HttpServletResponse response) {
        response.setHeader("status", Constants.errorStatus);
    }

    /**
     * 输出响应对象
     *
     * @param response 响应
     * @param object   对象内容
     */
    private static void response(HttpServletResponse response, Object object) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
//            JsonResponse jsonResponse;
//            if (object instanceof JsonResponse) {
//                jsonResponse = (JsonResponse) object;
//            } else if (object instanceof String){
//                jsonResponse = new JsonResponse((String) object);
//            } else {
//                if (object == null) { // data为null时，返回空json
//                    jsonResponse = new JsonResponse(new JSONObject());
//                } else {
//                    jsonResponse = new JsonResponse(object);
//                }
//            }
//            pw.print(jsonResponse);
            pw.print(object);
        } catch (Exception e) {
            log.error("printOnPage:", e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
