package com.heshuang.auth.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.heshuang.api.OperateLogApi;
import com.heshuang.api.UserApi;
import com.heshuang.auth.service.UserAuthService;
import com.heshuang.core.base.constant.ApplicationConst;
import com.heshuang.core.base.constant.AuthConst;
import com.heshuang.core.base.dto.auth.UserAuthInfoReqDTO;
import com.heshuang.core.base.dto.auth.UserIdReqDTO;
import com.heshuang.core.base.dto.common.OperateLog;
import com.heshuang.core.base.enums.RespCode;
import com.heshuang.core.base.result.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: heshuang
 */
@RestController
@Slf4j
@Api(tags = {"认证管理"}, description = "认证管理")
public class AuthController {

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 登录
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/auth/login")
    @ApiOperation("登录")
    public RespBody login(@RequestBody UserAuthInfoReqDTO reqDTO) {
        RespBody body = SpringUtil.getBean(OperateLogApi.class).add(new OperateLog());
        if (StrUtil.isEmpty(reqDTO.getAccount()) || StrUtil.isEmpty(reqDTO.getPassword()) || reqDTO.getType() == null) {
            return RespBody.fail(RespCode.ILLEGAL_PARAMETER_ERROR.getCode(), RespCode.ILLEGAL_PARAMETER_ERROR.getMsg());
        }
        Map<String, Object> resultMap = userAuthService.loginHandle(reqDTO);
        String flag = resultMap.get(AuthConst.FLAG).toString();
        if (AuthConst.FLAG_ZERO_VAL.equals(flag)) {
            return RespBody.fail(RespCode.USER_NO_EXIST_ERROR.getCode(), RespCode.USER_NO_EXIST_ERROR.getMsg());
        }
        if (AuthConst.FLAG_ONE_VAL.equals(flag)) {
            return RespBody.fail(RespCode.USER_OR_PASSWD_ERROR.getCode(), RespCode.USER_OR_PASSWD_ERROR.getMsg());
        }
        if (AuthConst.FLAG_TWO_VAL.equals(flag)) {
            String ID = reqDTO.getType() + ApplicationConst.DEFAULT_FLAG + resultMap.get(AuthConst.ID).toString();
            StpUtil.login(ID);
            resultMap.put("token", StpUtil.getTokenValue());
            StpUtil.getSession().set(AuthConst.USER,resultMap.get(AuthConst.USER));
            return RespBody.success(resultMap);
        }
        return RespBody.success();
    }


    /**
     * 登录状态
     *
     * @return
     */
    @PostMapping("/auth/isLogin")
    @ApiOperation("登录状态")
    @SaCheckLogin
    public RespBody isLogin() {
        return RespBody.success(StpUtil.isLogin());
    }

    /**
     * 获取Token信息
     *
     * @return
     */
    @PostMapping("/auth/getTokenInfo")
    @ApiOperation("获取Token信息")
    @SaCheckLogin
    public RespBody getTokenInfo() {
        return RespBody.success(StpUtil.getTokenInfo());
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/auth/logout")
    @ApiOperation("退出")
    @SaCheckLogin
    public RespBody logout() {
        StpUtil.logout(StpUtil.getLoginId());
        return RespBody.success();
    }

    /**
     * 获取用户对应的角色
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/auth/getRole")
    @ApiOperation("获取用户对应的角色")
    public RespBody<List<String>> getRole(@RequestBody UserIdReqDTO reqDTO) {
        return RespBody.success(userAuthService.queryUserIdByRole(reqDTO));
    }

    /**
     * 获取用户对应的角色菜单
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/auth/getPerm")
    @ApiOperation("获取用户对应的角色菜单")
    public RespBody<List<String>> getPerm(@RequestBody UserIdReqDTO reqDTO) {
        return RespBody.success(userAuthService.queryUserIdByPerm(reqDTO));
    }
}
