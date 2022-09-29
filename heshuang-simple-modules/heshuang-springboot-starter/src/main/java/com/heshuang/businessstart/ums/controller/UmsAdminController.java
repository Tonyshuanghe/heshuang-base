package com.heshuang.businessstart.ums.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heshuang.businessstart.ums.dto.UmsAdminLoginParam;
import com.heshuang.businessstart.ums.dto.UmsAdminParam;
import com.heshuang.businessstart.ums.dto.UmsAdminSmsLoginParam;
import com.heshuang.businessstart.ums.dto.UpdateAdminPasswordParam;
import com.heshuang.businessstart.ums.entity.UmsAdmin;
import com.heshuang.businessstart.ums.entity.UmsRole;
import com.heshuang.businessstart.ums.service.UmsAdminService;
import com.heshuang.businessstart.ums.service.UmsRoleService;
import com.heshuang.businessstart.ums.service.impl.PhoneCaptchaService;

import com.heshuang.core.base.constant.RestExStatus;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.result.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsRoleService roleService;
    @Autowired
    private PhoneCaptchaService phoneCaptchaService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public RestResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        return RestResult.of("成功", adminService.register(umsAdminParam));
    }

    @ApiOperation(value = "获取登录短信验证码")
    @RequestMapping(value = "/sendCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public RestResult sendCaptcha(@RequestParam String phone) {
        String code = phoneCaptchaService.sendCaptcha(phone);
        return RestResult.of("查询成功", code);
    }


    @ApiOperation(value = "短信登录以后返回token")
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    @ResponseBody
    public RestResult smsLogin(@Validated @RequestBody UmsAdminSmsLoginParam umsAdminSmsLoginParam) {
        String token = adminService.smsLogin(umsAdminSmsLoginParam.getPhone(), umsAdminSmsLoginParam.getSmsCode());
        if (token == null) {
            throw BusinessException.of("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RestResult.of("查询成功", tokenMap);
    }


    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RestResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            throw BusinessException.of("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RestResult.of("查询成功", tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public RestResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            throw BusinessException.of("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return RestResult.of("查询成功", tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public RestResult getAdminInfo(Principal principal) {
        if (principal == null) {
            throw BusinessException.of(RestExStatus.UN_RESOURCE_AUTH);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return RestResult.of("", data);
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public RestResult logout() {
        return RestResult.of(RestExStatus.SUCCESS);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public RestResult<Page<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
        return RestResult.of("", adminList);
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RestResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getById(id);
        return RestResult.of("", admin);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public RestResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        boolean success = adminService.update(id, admin);
        if (success) {
            return RestResult.of(RestExStatus.SUCCESS);
        }
        return RestResult.of(RestExStatus.FAIL);
    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return RestResult.of(RestExStatus.SUCCESS);
        } else if (status == -1) {
            return RestResult.of(status, "提交参数不合法");
        } else if (status == -2) {
            return RestResult.of(status, "找不到该用户");
        } else if (status == -3) {
            return RestResult.of(status, "旧密码错误");
        } else {
            return RestResult.of(RestExStatus.FAIL);
        }
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public RestResult delete(@PathVariable Long id) {
        boolean success = adminService.delete(id);
        if (success) {
            return RestResult.of(RestExStatus.SUCCESS);
        }
        return RestResult.of(RestExStatus.FAIL);
    }

    @ApiOperation("修改帐号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        boolean success = adminService.update(id, umsAdmin);
        if (success) {
            return RestResult.success(null);
        }
        return RestResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateRole(@RequestParam("adminId") Long adminId,
                                 @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return RestResult.success(count);
        }
        return RestResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public RestResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return RestResult.success(roleList);
    }
}
