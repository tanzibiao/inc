package com.inc.admin.controller.biz;

import com.inc.admin.controller.sys.BaseController;
import com.inc.admin.utils.EhCacheUtil;
import com.inc.admin.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 * @author inc
 */
@RequestMapping("/test")
@RestController
public class TestController extends BaseController {

	@PostMapping("put")
    R put(String key , String value) {
		EhCacheUtil.put(key, value);
		return R.ok();
	}

	@PostMapping("get")
    R get(String key) {
		return R.ok(EhCacheUtil.get(key));
	}
	@PostMapping("del")
    R del(String key) {
		EhCacheUtil.delete(key);
		return R.ok();
	}

}
