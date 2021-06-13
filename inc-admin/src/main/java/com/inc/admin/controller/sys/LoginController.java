package com.inc.admin.controller.sys;

import com.inc.admin.context.FilterContextHandler;
import com.inc.admin.domain.sys.UserDO;
import com.inc.admin.dto.sys.LoginDTO;
import com.inc.admin.dto.sys.UserToken;
import com.inc.admin.exception.BizException;
import com.inc.admin.service.sys.MenuService;
import com.inc.admin.service.sys.UserService;
import com.inc.admin.utils.JwtUtils;
import com.inc.admin.utils.MD5Utils;
import com.inc.admin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 64301325@qq.com
 * @version V1.0
 */
@RequestMapping()
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;

    @PostMapping("/login")
    R login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        String username = loginDTO.getUsername().trim();
        String password = loginDTO.getPwd().trim();
        password = MD5Utils.encrypt(username, password);
        Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        List<UserDO> userDOs = userService.list(param);
        if(userDOs.size()<1){
            throw new BizException("用户或密码错误");
        }
        UserDO userDO = userDOs.get(0);
        if (null == userDO || !userDO.getPassword().equals(password)) {
            throw new BizException("用户或密码错误");
        }
        UserToken userToken = new UserToken(userDO.getUsername(), userDO.getUserId().toString(), userDO.getName());
        String token="";
        try {
            token = JwtUtils.generateToken(userToken, 2*60*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //首先清除用户缓存权限
        menuService.clearCache(userDO.getUserId());
        return R.ok("登录成功")
                .put("token", token).put("user",userDO)
                .put("perms",menuService.PermsByUserId(userDO.getUserId()))
                .put("router",menuService.RouterDTOsByUserId(userDO.getUserId()));
    }


    @RequestMapping("/logout")
    R logout(HttpServletRequest request, HttpServletResponse response) {
        menuService.clearCache(Long.parseLong(FilterContextHandler.getUserID()));
        return R.ok();
    }


}
