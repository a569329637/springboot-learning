package com.gsq.learning.shiro.auth.realm;

import com.gsq.learning.shiro.auth.token.JwtToken;
import com.gsq.learning.shiro.utils.JsonWebTokenUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;


/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(JwtRealm.class);

    @Override
    public String getName() {
        return "JwtRealm";
    }

    @Override
    public Class getAuthenticationTokenClass() {
        // 只支持此 token
        return JwtToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // find by database
        String payload = (String) principalCollection.getPrimaryPrincipal();
        if (payload.startsWith("jwt:") && payload.charAt(4) == '{' && payload.charAt(payload.length() - 1) == '}') {
            Map<String, Object> payloadMap = JsonWebTokenUtil.readValue(payload.substring(4));
            Set<String> roles = org.apache.shiro.util.StringUtils.splitToSet((String) payloadMap.get("roles"), ",");
            Set<String> permissions = org.apache.shiro.util.StringUtils.splitToSet((String) payloadMap.get("perms"), ",");
            SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
            if(null!=roles && !roles.isEmpty())
                info.setRoles(roles);
            if(null != permissions && !permissions.isEmpty())
                info.setStringPermissions(permissions);
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof JwtToken)) {
            return null;
        }
        JwtToken jwtToken = (JwtToken) token;
        if (StringUtils.isEmpty(jwtToken.getCredentials())
                || StringUtils.isEmpty(jwtToken.getPrincipal())) {
            throw new AuthenticationException("认证信息不能为空");
        }

        String jwt = (String) jwtToken.getCredentials();
        String payload;
        try {
            // 预先解析Payload
            // 没有做任何的签名校验
            payload = JsonWebTokenUtil.parseJwtPayload(jwt);
            logger.info("payload={}", payload);
        } catch(MalformedJwtException e){
            throw new AuthenticationException("error jwt");
        } catch(Exception e){
            throw new AuthenticationException("errors jwt");
        }
        if (StringUtils.isEmpty(payload)) {
            throw new AuthenticationException("error jwt");
        }

        Map<String, Object> payloadMap = JsonWebTokenUtil.readValue(payload);
        if (!payloadMap.get("sub").equals(jwtToken.getPrincipal())) {
            throw new AuthenticationException("error jwt payload");
        }

        return new SimpleAuthenticationInfo("jwt:"+payload, jwt, getName());
    }
}
