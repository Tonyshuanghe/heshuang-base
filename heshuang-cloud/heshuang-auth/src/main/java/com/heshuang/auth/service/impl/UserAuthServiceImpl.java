package com.heshuang.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.heshuang.auth.mapper.AuthMapper;
import com.heshuang.auth.service.UserAuthService;
import com.heshuang.core.base.constant.AuthConst;
import com.heshuang.core.base.dto.auth.UserAuthInfoReqDTO;
import com.heshuang.core.base.dto.auth.UserIdReqDTO;
import com.heshuang.core.base.utils.JbcryptUtil;
import com.heshuang.core.base.vo.auth.UserAuthVO;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: heshuang
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private AuthMapper authMapper;


    @Override
    public Map<String, Object> loginHandle(UserAuthInfoReqDTO reqDTO) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer type = reqDTO.getType();
        String account = reqDTO.getAccount();
        String password = reqDTO.getPassword();
        UserAuthVO userAuthVo = authMapper.selectUserAuthInfo(type, account);
        if (userAuthVo != null && !StrUtil.isEmptyIfStr(userAuthVo.getId())) {
            if (!JbcryptUtil.checkPwd(password, userAuthVo.getPassword())) {
                returnMap.put(AuthConst.FLAG, AuthConst.FLAG_ONE_VAL);
            } else {
                returnMap.put(AuthConst.ID, userAuthVo.getId());
                returnMap.put(AuthConst.USER, userAuthVo);
                returnMap.put(AuthConst.FLAG, AuthConst.FLAG_TWO_VAL);
            }
        } else {
            returnMap.put(AuthConst.FLAG, AuthConst.FLAG_ZERO_VAL);
        }
        return returnMap;
    }

    @Override
    public List<String> queryUserIdByRole(UserIdReqDTO reqDTO) {
        return authMapper.selectUserIdByRole(reqDTO.getUserId());
    }

    @Override
    public List<String> queryUserIdByPerm(UserIdReqDTO reqDTO) {
        return authMapper.selectUserIdByPerm(reqDTO.getUserId());
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        // "[1]"?????????1????????????1???"[3578]"????????????????????????3???5???8???????????????"\\d{9}"????????????????????????0???9???????????????9??????
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }


}
