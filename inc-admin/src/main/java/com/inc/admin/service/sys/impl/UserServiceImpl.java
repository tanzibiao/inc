package com.inc.admin.service.sys.impl;


import com.inc.admin.context.FilterContextHandler;
import com.inc.admin.dao.sys.UserDao;
import com.inc.admin.dao.sys.UserRoleDao;
import com.inc.admin.domain.sys.UserDO;
import com.inc.admin.domain.sys.UserRoleDO;
import com.inc.admin.dto.sys.req.UpPwdReq;
import com.inc.admin.exception.BizException;
import com.inc.admin.service.sys.UserService;
import com.inc.admin.utils.MD5Utils;
import com.inc.admin.vo.UserVO;
import com.inc.admin.utils.R;
import com.tzb.faker4j.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Author 64301325@qq.com
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userMapper;
	@Autowired
    UserRoleDao userRoleMapper;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDO get(Long id) {
		List<Long> roleIds = userRoleMapper.listRoleId(id);
		UserDO user = userMapper.get(id);
		user.setroleIds(roleIds);
		return user;
	}

	@Override
	public List<UserDO> list(Map<String, Object> map) {
		return userMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}

	@Override
	public int save(UserDO user) {
		int count = userMapper.save(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		userRoleMapper.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return count;
	}

	@Override
	public int profile(UserDO user) {
		return userMapper.update(user);
	}

	@Override
	public R resetPwd(Long id) {
		//查询
		UserDO user = userMapper.get(id);
		if (user == null) {
			//该用户不存在
			return R.error("该用户不存在");
		}
		String pwd = Faker.num().num(6);
		UserDO up = new UserDO();
		up.setUserId(id);
		up.setPassword(MD5Utils.encrypt(user.getUsername(), pwd));
		userMapper.updatePwd(up);
		return R.ok("重置成功，新密码为："+pwd);
	}

	@Override
	public R updatePwd(UpPwdReq req) {
		long userId = Long.parseLong(FilterContextHandler.getUserID());
		String username = FilterContextHandler.getUsername();
		UserDO old = userMapper.get(userId);
		String encrypt = MD5Utils.encrypt(username, req.getOldPwd());
		if (!encrypt.equals(old.getPassword())) {
			throw new BizException("原密码不正确");
		}
		if (!req.getNewPwd().equals(req.getConfirmPwd())) {
			throw new BizException("两次输入的新密码不一致");
		}
		UserDO up = new UserDO();
		up.setUserId(userId);
		up.setPassword(MD5Utils.encrypt(username, req.getNewPwd()));
		return R.operate(userMapper.updatePwd(up)>0);
	}

	@Override
	public int update(UserDO user) {
		int r = userMapper.update(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		if(null!=roles){
			userRoleMapper.removeByUserId(userId);
			List<UserRoleDO> list = new ArrayList<>();
			for (Long roleId : roles) {
				UserRoleDO ur = new UserRoleDO();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				userRoleMapper.batchSave(list);
			}
		}
		return r;
	}

	@Override
	public int remove(Long userId) {
		userRoleMapper.removeByUserId(userId);
		return userMapper.remove(userId);
	}

	@Override
	public boolean exits(Map<String, Object> params) {
		boolean exits = userMapper.list(params).size() > 0;
		return exits;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public int resetPwd(UserVO userVO, UserDO userDO) throws Exception {
		if(Objects.equals(userVO.getUserDO().getUserId(),userDO.getUserId())){
			if(Objects.equals(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdOld()),userDO.getPassword())){
				userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdNew()));
				return userMapper.update(userDO);
			}else{
				throw new Exception("输入的旧密码有误！");
			}
		}else{
			throw new Exception("你修改的不是你登录的账号！");
		}
	}
	@Override
	public int adminResetPwd(UserVO userVO) throws Exception {
		UserDO userDO =get(userVO.getUserDO().getUserId());
		if("admin".equals(userDO.getUsername())){
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
		return userMapper.update(userDO);


	}

	@Transactional
	@Override
	public int batchremove(Long[] userIds) {
		int count = userMapper.batchRemove(userIds);
		userRoleMapper.batchRemoveByUserId(userIds);
		return count;
	}

	@Override
	public int updatePersonal(UserDO userDO) {
		return userMapper.update(userDO);
	}

	@Override
	public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
		return null;
	}


}
