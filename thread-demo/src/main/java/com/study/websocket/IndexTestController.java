package com.study.websocket;

import com.study.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author yutong on 2017/1/9.
 */
@Controller
public class IndexTestController {
    private static Logger logger = LoggerFactory.getLogger(IndexTestController.class);

    @Resource
    private SystemWebSocketHandler systemWebSocketHandler;

    @RequestMapping(value = "/test/pay", method = RequestMethod.GET)
    public String scanPic(HttpServletRequest request, HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();
        HttpSession session = request.getSession();
        session.setAttribute("uuid", uuid);
        return "/thirdLogin/scanPay";
    }

    /**
     * 扫码登录获取该用户的登录信息
     *
     * @param request  请求
     * @param response 响应
     * @param clientId 客户端编号
     */
    @RequestMapping(value = "/test/loginAuth", method = RequestMethod.POST)
    @ResponseBody
    public void loginAuth(HttpServletRequest request, HttpServletResponse response, String clientId) {
        if (StringUtils.isBlank(clientId)) {
            WebUtils.failOnPage(response, "客户端编号不能为空");
            return;
        }
//        Long memberId = tokenService.get(request);
        Long memberId = Long.parseLong("1");
        if (memberId == 0) {
            WebUtils.failOnPage(response, "手机未登录");
            return;
        }
//        String keygen = CustomizedPropertyConfigurer.getContextProperty("common.AES.key");
//        clientId = CipherUtil.decrypt(clientId, keygen);
        logger.info("用户登录扫码开始，clientId" + clientId + "，用户编号：" + memberId);

        // 设置存入redis有效时间1小时
//        springRedis.set(CacheConstants.pcWebScanLoginKey + clientId, ""+memberId, 3600);
        systemWebSocketHandler.sendMessageToUser(clientId, new TextMessage("登录成功"));
//        systemWebSocketHandler.sendMessageToUsers(new TextMessage("1"));
        systemWebSocketHandler.sendMessageToOthers(clientId, new TextMessage("1"));
        WebUtils.printOnPage(response, "登录成功");

    }
}
