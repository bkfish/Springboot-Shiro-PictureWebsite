package com.kitty.shiro.config;

import com.kitty.shiro.service.UserService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private UserService userService;
    /**
     * 创建ShiroFilterFactoryBean
     * shiro过滤bean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *  常用的过滤器：
         *      anon: 无需认证（登录）可以访问
         *      authc: 必须认证才可以访问
         *      user: 如果使用rememberMe功能可以直接访问
         *      perms: 该资源必须得到资源权限才可以访问
         *      role: 该资源必须得到角色权限才可以访问
         */

        Map<String, String> filerMap = new LinkedHashMap<>(); //顺序的map
        //配置记住我或认证通过可以访问的地址
        //filerMap.put("/testThymeleaf", "user");
        //如果没有拦截，默认会跳转到login.jsp，可以通过setLoginUrl设置登录页面
        //filerMap.put("/add","authc");
        //filerMap.put("/update","authc");
        filerMap.put("/index","anon");
        filerMap.put("/login","anon"); //login是表单提交的
        filerMap.put("/toLogin","anon");
        //授权过滤器
        filerMap.put("/see","perms[user:see]");

        //设置登录的页面，发送toLogin请求
        shiroFilterFactoryBean.setLoginUrl("/noAuth");

        //设置未授权的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        //设置过滤器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filerMap);
        return shiroFilterFactoryBean;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

}
