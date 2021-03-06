package com.architecture.workers.architecture.config;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.*;

import javax.servlet.Filter;
import java.util.*;

@Configuration // 让Spring自动扫描这个类
public class ShiroConfig {

     @Bean
     public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){

         ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

         //必须设置securityManager
         shiroFilterFactoryBean.setSecurityManager(securityManager);

         //设置用户登录的页面  如果需要登录没有登录的话 则调用这个接口
         shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

         //设置用户登录成功后的页面
         shiroFilterFactoryBean.setSuccessUrl("/");

         //设置用户没有授权的界面
         shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permis");
//
         //配置filter自定义拦截器
         Map<String, Filter> filterMap = new LinkedHashMap<>();
         filterMap.put("roleOrFilter",new CustomRolesOrAuthorizationFilter());

         shiroFilterFactoryBean.setFilters(filterMap);

         /**
          * 如果我们想设置一个多的数据过滤器的话 需要使用到Map
          * 记住 这里面的Map不是实例化出来一个HashMap的一个集合
          * 而是实例化出来一个 LinkedHashMap这个集合
          * 因为如果实例化出来map集合 是无序的 他会执行随意的一条优先
          * 而LinkedHashMap 实现的是从上到下进行执行的
          * */
         Map<String,String> map = new LinkedHashMap<>();

         //设置用户退出过滤器
         map.put("/logout/out","authc");

         //设置一个游客可以访问的页面 anon的含义是不需要用户进行认证授权登陆操作的就可以访问
         map.put("/pub/**","anon");

         //设置一个需要认证登录才可以进入的界面
         map.put("/authc/**","authc");

         //设置一个只有超级管理员可以登录的
         map.put("/sa/**","authc");
         //用户登录后台的时候 查看是否拥有admin权限 才可以进行访问后台roles[]是进行匹配是否拥有admin管理员权限

         map.put("/select/saveFileInfo","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/fileUpload","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/chakanXiang","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/findcondition","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/updataPersonOwnOrgCode","roleOrFilter[sa]");
         map.put("/select/jifenFind","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/findZhangDan","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/downLoadCount","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/ferrInsert","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/checkCerTifind","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/totalCount","roleOrFilter[bank,customer,admin,sa]");
         map.put("/select/**","roleOrFilter[admin,sa]");
         //用户如果想访问一个可以编辑或者修改东西的界面的时候 需要使用perms进行匹配用户有没有这个权限访问
         map.put("/sa/**","perms[/sa/findshenpi]");
         map.put("/select/**","perms[/select/*]");
         map.put("/**", "authc");

         shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

         return shiroFilterFactoryBean;
     }


     @Bean
     public SecurityManager securityManager(){
         DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

         //如果不是前后端分离的话 这个设置就不需要了  前后端分离的话 必须设置如下代码  不然不可行
         defaultWebSecurityManager.setSessionManager(sessionManager());
         //设置redis注入进去
         //defaultWebSecurityManager.setCacheManager(redisCacheManager());
         //设置relm 放在最后 不然可能会导致不生效!
         defaultWebSecurityManager.setRealm(cutromRelm());//记住这里不能直接使用new 进行创建对象 因为Spring的一个默认装载机制

         return defaultWebSecurityManager;
     }

     /**
      * 这里进行一个md5的一个加密 使用HashedCredentialsMatcher
      * 可以给他设置一个加密次数 主要用来对密码进行加密
      * */
//     @Bean
//     public HashedCredentialsMatcher hashedCredentialsMatcher(){
//         HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//         hashedCredentialsMatcher.setHashAlgorithmName("md5");
//         hashedCredentialsMatcher.setHashIterations(2);
//         return hashedCredentialsMatcher;
//     }

     @Bean
     public CutromRelm cutromRelm(){
         CutromRelm cutromRelm = new CutromRelm();
         //cutromRelm.setCredentialsMatcher(hashedCredentialsMatcher());
         return cutromRelm;
     }

     @Bean
     public SessionManager sessionManager(){
         CustomSessionManager sessionManager = new CustomSessionManager();
         //设置session的过期时间  默认30分钟  方法的单位是秒
         //设置redis持久化
         //sessionManager.setSessionDAO(redisSessionDAO());
         sessionManager.setSessionDAO(getMemorySessionDAO());
         sessionManager.setGlobalSessionTimeout(2000000);
         return sessionManager;
     }

    @Bean(name = "sessionDAO")
    public MemorySessionDAO getMemorySessionDAO() {
        return new MemorySessionDAO();
    }

    //配置session的缓存管理器
    @Bean(name = "shiroCacheManager")
    public MemoryConstrainedCacheManager getMemoryConstrainedCacheManager() {
        return new MemoryConstrainedCacheManager();
    }


    @Bean(name = "sessionIdCookie")
    public SimpleCookie getSimpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("SHRIOSESSIONID");
        return simpleCookie;
    }

    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(getMemorySessionDAO());
        sessionManager.setSessionIdCookie(getSimpleCookie());
        return sessionManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }

    /**
     * shiro里实现的Advisor类,用来拦截注解的方法 .
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }





//     /**
//      * 集成一个Redis
//      * */
//     public RedisManager redisManager(){
//         RedisManager redisManager = new RedisManager();
//         redisManager.setHost("localhost");
//         redisManager.setPort(6379);
//         return redisManager;
//     }
//
//     public RedisCacheManager redisCacheManager(){
//         RedisCacheManager redisCacheManager = new RedisCacheManager();
//         redisCacheManager.setRedisManager(redisManager());
//         //设置一个redis缓存时间  秒为单位
//         redisCacheManager.setExpire(20);
//         return redisCacheManager;
//     }
//
//     /**
//      * 这里处理一个问题  如果用户在修改某一样东西的时候我们的代码重启了
//      * 就会导致用户提交不成功并且登录也得不到保存
//      *
//      * 这里会有个坑  如果使用这样的reids进行持久化的话
//      * 那么实体类必须序列化
//      * */
//     public RedisSessionDAO redisSessionDAO(){
//         RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//         redisSessionDAO.setRedisManager(redisManager());
//         return redisSessionDAO;
//     }
}
