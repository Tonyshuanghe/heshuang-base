package com.heshuang.businessstart.ums.service.impl;


import com.google.common.collect.Maps;

import com.heshuang.businessstart.ums.entity.UmsAdmin;
import com.heshuang.businessstart.ums.service.UmsAdminService;

import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.utils.DateUtils;
import com.heshuang.core.base.utils.VerifyCodeUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PhoneCaptchaService {

  public static final String MSG_CODE_KEY = "msg:code:%s:%s";
  
  public static final String MSG_CODE_TIME = "msg:time:%s:%s";
  
  public static final String MSG_CODE_LOCK = "msg:lock:%s:%s";
  
  public static final String MSG_LOGIN = "1";
  
  private final String SELECT_QUERY = "select user_id uid,login_name cn, user_name displayName,user_phone mobile, user_sex sex, lower(passwd) password,if(expiration_time is not null, expiration_time<now(), 0)expired,status, area_id areaCode from s_user s1 ";
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Autowired
  private RedisTemplate redisTemplate;

  
  @Value("${bore.login.msg.timeout:300}")
  private Integer msgTimeOut;
  
  @Value("${bore.login.msg.limit:60}")
  private Integer msgLimit;
  
  @Value("${bore.login.msg.length:6}")
  private Integer msgCodeLenth;

  @Value("${bore.login.msg.template:1475428}")
  private String msgTemplateId;

  @Value("${bore.login.ops.secretId:AKIDoRpyrjW5TffRyGMGxa3HG0XqthFYbPxG}")
  private String secretId;

  @Value("${bore.login.ops.secretKey:6wXt0rSowpSqzsWu2vYzLSJvfCUXKsBE}")
  private String secretKey;

  @Value("${bore.login.msg.opsAppId:1400706698}")
  private String opsAppId;

  @Value("${bore.login.msg.sign:艾普雷}")
  private String msgSignId;

  @Autowired
  private UmsAdminService umsAdminService;
  
  public String sendCaptcha(String phone) {
    String redisCode = (String)this.redisTemplate.opsForValue().get(String.format("msg:lock:%s:%s", new Object[] { "1", phone }));
    if (StringUtils.isNotBlank(redisCode)) {
      Long expireTm = this.redisTemplate.getExpire(String.format("msg:lock:%s:%s", new Object[] { "1", phone }), TimeUnit.SECONDS);
      throw new RuntimeException(String.format("%s验证码已发送请在%s秒后重试", new Object[] { phone, expireTm }));
    }
    String randomCode = VerifyCodeUtils.randomCode(this.msgCodeLenth.intValue());
    this.redisTemplate.opsForValue().set(
        String.format("msg:lock:%s:%s", new Object[] { "1", phone }), randomCode, this.msgLimit.intValue(), TimeUnit.SECONDS);
    String captchaCode = (String)this.redisTemplate.opsForValue().get(String.format("msg:code:%s:%s", new Object[] { "1", phone }));
    if (StringUtils.isNotBlank(captchaCode)) {
      Long expireTm = this.redisTemplate.getExpire(String.format("msg:code:%s:%s", new Object[] { "1", phone }), TimeUnit.SECONDS);
      throw new RuntimeException(String.format("你最近%s获取过验证码，请使用上次的验证码进行登录", new Object[] { DateUtils.getDatePoor(this.msgTimeOut.intValue()) }));
    }
    if (getUserByPhone(phone) == null) {throw new RuntimeException(String.format("手机号%s未注册", new Object[] { phone }));}
    sendSms(new String[]{phone},new String[]{randomCode,String.valueOf(this.msgTimeOut.intValue()/60)});
    this.redisTemplate.opsForValue().set(
        String.format("msg:code:%s:%s", new Object[] { "1", phone }), randomCode, this.msgTimeOut.intValue(), TimeUnit.SECONDS);
    this.redisTemplate.opsForValue().set(
        String.format("msg:time:%s:%s", new Object[] { "1", phone }), DateUtils.getTime());
    return null;
  }

  private void sendSms(String[] phoneNumber,String[] templateParamSet)  {
    try {
      // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
      // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
      Credential cred = new Credential(secretId, secretKey);
      // 实例化一个http选项，可选的，没有特殊需求可以跳过
      HttpProfile httpProfile = new HttpProfile();
      httpProfile.setEndpoint("sms.tencentcloudapi.com");
      // 实例化一个client选项，可选的，没有特殊需求可以跳过
      ClientProfile clientProfile = new ClientProfile();
      clientProfile.setHttpProfile(httpProfile);
      // 实例化要请求产品的client对象,clientProfile是可选的
      SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
      // 实例化一个请求对象,每个接口都会对应一个request对象
      SendSmsRequest req = new SendSmsRequest();

      req.setPhoneNumberSet(phoneNumber);
      req.setSmsSdkAppId(opsAppId);
      req.setTemplateId(msgTemplateId);
      req.setSignName(msgSignId);
      req.setTemplateParamSet(templateParamSet);
      // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
      SendSmsResponse resp = client.SendSms(req);
    } catch (TencentCloudSDKException e) {
      log.error("发送短信异常:{}",e.getMessage());
    }
  }

  public static void main(String[] args) {
    System.out.println(DateUtils.getTime());
  }
  
  public String getLoginCaptcha(String phone) {
    return String.format("%s", new Object[] { this.redisTemplate.opsForValue().get(
            String.format("msg:code:%s:%s", new Object[] { "1", phone })) });
  }
  
  public Boolean validateLoginCaptcha(String phone, String captcha) {
    return Boolean.valueOf(!StringUtils.equals(getLoginCaptcha(phone), captcha));
  }

  public UserDetails getUserByPhone(String phone) {
    UserDetails userDetails = umsAdminService.loadUserByUsername(phone);
    if (!userDetails.isEnabled()) {
      throw BusinessException.of("帐号已被禁用");
    }
    return userDetails;
  }



}
