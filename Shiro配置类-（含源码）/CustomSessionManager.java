package com.architecture.workers.architecture.config;


import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.*;
import java.io.Serializable;

public class CustomSessionManager extends DefaultWebSessionManager {

    private static final String TOKENSSSION = "token";

    public CustomSessionManager(){
        super();
    }

    /**
     * 底层源代码 复制过来 加入一个if判断 就可以得到前端传递过来的headers信息里面的token
     * 进行一个权限和角色的管理.
     * */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getParameter(TOKENSSSION);
        if(sessionId == null || sessionId == ""){
               sessionId = WebUtils.toHttp(request).getHeader(TOKENSSSION);
        }
        if(sessionId != null){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }else{
            return super.getSessionId(request,response);
        }
    }

}
