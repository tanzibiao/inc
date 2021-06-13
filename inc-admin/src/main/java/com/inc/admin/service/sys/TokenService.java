package com.inc.admin.service.sys;

import org.springframework.stereotype.Service;

/**
 * @author 64301325@qq.com
 */
@Service
public interface TokenService {
    /**
     * 根据用户id生成token持久化
     */
    String createToken(Long userId);

    Long getUserIdByToken(String token);

    boolean removeToken(String token);
}
