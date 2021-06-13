package com.inc.admin.controller.sys;

import com.inc.admin.domain.sys.UserDO;
import com.inc.admin.dto.sys.UserDTO;
import com.inc.admin.dto.sys.req.UpPwdReq;
import com.inc.admin.service.sys.RoleService;
import com.inc.admin.service.sys.UserService;
import com.inc.admin.utils.MD5Utils;
import com.inc.admin.context.FilterContextHandler;
import com.inc.admin.dto.sys.LoginUserDTO;
import com.inc.admin.utils.ObjectCopyUtils;
import com.inc.admin.utils.PageUtils;
import com.inc.admin.utils.Query;
import com.inc.admin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 * @author inc
 */
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

	/**
	 * 登录的当前用户，前台需要验证用户登录的页面可以调用此方法
	 * @return
	 */
    @GetMapping("/currentUser")
    LoginUserDTO currentUser(){
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setUserId(FilterContextHandler.getUserID());
		loginUserDTO.setUsername(FilterContextHandler.getUsername());
		loginUserDTO.setName(FilterContextHandler.getName());
		return loginUserDTO;
	}

	/**
	 * 根据用户id获取用户
	 * @param id
	 * @return
	 */
    @GetMapping("{id}")
	R get(@PathVariable("id") Long id ){
		UserDO userDO = userService.get(id);
    	return R.ok().put("data", ObjectCopyUtils.copyProperties(new UserDTO(), userDO));
	}

	/**
	 * 分页查询用户
	 * @param params
	 * @return
	 */
    @GetMapping()
    R listByPage(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
		List<UserDO> list = userService.list(query);
		List<UserDTO> userDTOS = ObjectCopyUtils.copyListProperty(UserDTO.class, list);
        int total = userService.count(query);
        PageUtils pageUtil = new PageUtils(userDTOS, total);
        return R.ok().put("page",pageUtil);
    }

	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	@PostMapping()
    R save(@RequestBody UserDO user) {
		if (exits(user.getUsername())) {
			return R.error("用户名已存在");
		}
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		return R.operate(userService.save(user) > 0);
	}

	@PostMapping("register")
    R register(@RequestBody UserDO user) {
		if (exits(user.getUsername())) {
			return R.error("用户名已被注册");
		}
		user.setroleIds(Arrays.asList(56L));
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		return R.operate(userService.save(user) > 0);
	}

	@PostMapping("resetPwd")
    R resetPwd(@RequestBody UserDO user) {
		return userService.resetPwd(user.getUserId());
	}

	@PostMapping("updatePwd")
    R updatePwd(@RequestBody @Validated UpPwdReq req) {
		return userService.updatePwd(req);
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@PutMapping()
	R update(@RequestBody UserDO user) {
		return R.operate(userService.update(user) > 0);
	}

	@PostMapping("profile")
	R profile(@RequestBody UserDO user) {
		user.setUserId(Long.parseLong(FilterContextHandler.getUserID()));
		return R.operate(userService.profile(user)>0);
	}

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@DeleteMapping()
	R remove( Long id) {
		return R.operate (userService.remove(id) > 0);
	}

	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	boolean exits(String userName) {
		// 存在，不通过，false
		Map<String, Object> params = new HashMap<>();
		params.put("username", userName);
		return userService.exits(params);
	}
}
