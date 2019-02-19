package com.tensquare.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void sendsms(Map<String,String>map){
        String code = map.get("checkcode");
        String mobile = map.get("mobile");
        System.out.println(code);
        System.out.println(mobile);
        try {
            smsUtil.sendSms(mobile, "SMS_151830050", "十次方管理系统签名", "{\"checkcode\":\"" + code + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
