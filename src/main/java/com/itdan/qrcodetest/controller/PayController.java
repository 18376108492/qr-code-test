package com.itdan.qrcodetest.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付测试
 */

@Controller

public class PayController {

    private Logger logger=LoggerFactory.getLogger(PayController.class);

    //初始化阿里沙箱参数

    String APP_ID ="";
    //应用私钥
    String APP_PRIVATE_KEY ="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCgiWjxqmCSwMNbVhVe55ojwubrMSKljIwBYl/DOcBt3SUZeWujv8M1RpUptjMmjIdhltyKgvT+VGF1A7Q2W+1cfC8n748koHfixyyLtzSew9gduE3h2JgyMAAycPR1nDu7VMOhnDBNk4vUyzNk53QeSuJI4KGmfWM7M+2o9JdGZxI6Fa1UejqwH1StuxS//Ck/3wDbG+YGrT6eRC46IwbGP2HOYevp9rKYsGag4mpoIqJBxxnj/B2ogVFZ+JNKRn/PSrv4iqMwdkeETstEQarLWgtsQC933CaWpvMkZvNgZNKoYtxiffvgNaaNYPvVTfmKuCEJ+c8R+3sC+O0mpb63AgMBAAECggEAAVFfdSxq4omGkQdh4YeRMl31O1UJ7cX7mD9Qfe/Jbp10IoNmqM94MMXFj7QZM2XP7rMoWFK+5B+ZNTa0kgWrKykx0egbirduhIQSD4uPeQYujf/nU8VNaowOVFutBhIkQ2Nsv7TiiKB/c12/rEJiJ6WBugtuCEnbOK8yckKfw0ot9VBde9/do574kWz1fMaj7b4cZkwfFgXf+WxKiFJnwho+5BTcrtbWv0WHzqv4HNsErGSi+6uH7W+oVnPQQvoX0mSr22PBznG8KfQBCir+Z5SP9Bkn+xhiHxlmqeihe9zx7G/DLCKUQfy+Kwz8dBASuS03lXDq6OyG9dFBXRhDwQKBgQDp5teQaptk4Uy3lQbfBcZucbl0WYTfmDFWCQXM7GP9cAqfBSct2gHZzKqFYOO7Fg3wHVKdqdEiPBiRQRAzMCX9LVoWm0ZDBogxIyFgbUtA44PvpOyBYwjdAJm0c0A3uXvuJANZYJuTxQrXlPYPrIFutEU0sAUcVWcTOLRq26yzkQKBgQCvtCjL6EbMiQ9uRpv7zS67kx/FaYzZol5ocPQrt+Mpc7TDlQFHPtDhTzjBv6AYkFpkYqRdoLQ26y3asRp5a81VrCx7djG5hnXjIz0fk2C6hk49uXm1kzl9OQPXScExbKdUKDXS/yXJ2+F0qOEqeH0t7kThkd2w6J/hcLUyL40ZxwKBgCsSGve46cx1wE693noK8jM8Nc46/cC+obcaCjtl/DObAhmuNTy4PEcC7ROKd+TndsGUZLYroxsOvedhd741S4mHZGNtTE7jUQjbUzYSah0UyCLE19griGJJzgPwhQ691RhRAVzf8UYQrqJsB7NzLJJaF7RQBWDmSQi9FFvo/7BBAoGAccSCwf1FknyQ3EOmDnX984DBCq9cfzLuVEwHHiO0WAvOmPFrYXlgAPOt+W4duKoHjYZ9jQ5YglLnXvpb/RD7ZKTWLJ8MdWeF/6u6kS55bG04TilBkHuitt1rCkR8boc/FXPv3g5NppXUNxh+q2RXEZ19Rl2PoVQO6gdzm+pEP9MCgYBRblpGndXUNAPHOP1CY2VlhuENxZJnTlUj7jaEIMy/c2a/LwKUWa467FSNTHpRdkUMzssXy2kE6zF1lPjHxH55X+7/qLjtrjwUCqEuMR+FkwSRaAMaEXqS+7w/bi2iqwbSW5wXNr1mEFr+eLtZY8sffw7TD4ru1PLPBm9EsyiO9A==";
    //阿里公钥
    String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvNBYCePTw9B9M7jjQ3j0TqRTTjUX43RWeLnJUku2A+ZaxiROaxlW1AxdqE1+5jOpzgrZ39GCaRomePJBOdCTZi0278Ir9ubdlebpXcAkV5wcRTEjCm47v4AfNqZKA9fq0zkjTmqBxaysbeZ8xsW9vxl9y0tFdQm3aslVDGrv+XnC+loA5WgcDPgU5ygfMgDi5LJmkY/TgIrMI1h6nDB2Y0ftD/Zr0kmhNYLjBGr5AcolxaNpmWxQEoPL5m+Q3TP+ZEb/SGeOQ1uVFecfbk1czrtxYvq23iFJgSUqA9aNbrIBQ5x1P70VIXS/st8NS8i2nw7MCt86XPKh31/fvjvRIQIDAQAB";
    //编码集
    String CHARSET="utf-8";
    //支付宝网关
    String SERVER_URL="https://openapi.alipaydev.com/gateway.do";
    //测试地址:
    //正式地址:"https://openapi.alipay.com/gateway.do"

    @RequestMapping("/alipayTest")
    public void alipayTest(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(SERVER_URL, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        //获得初始化的AlipayClient
         AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
         //创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");
        //在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" + " \"out_trade_no\":\"20150320010101013\"," + " \"total_amount\":\"88.88\"," + " \"subject\":\"Iphone6 16G\"," + " \"product_code\":\"QUICK_WAP_PAY\"" + " }");
        //填充业务参数
         String form="";
         try {
             form = alipayClient.pageExecute(alipayRequest).getBody();
             //调用SDK生成表单
             } catch (AlipayApiException e)
         { e.printStackTrace(); }
         httpResponse.setContentType("text/html;charset=" + CHARSET);
         httpResponse.getWriter().write(form);
         //直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close(); }

}

