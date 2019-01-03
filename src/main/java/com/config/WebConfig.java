package com.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.core.AuthorizeInterceptor;
import com.core.UploadServlet;
import com.core.UserSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: @Litonghui
 * Date: 2018/12/26 14:39
 * Content: 注册拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final static  String securityKey="qfsdfsdfasd";
    private final static  String adminUrl="myObject";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        String adminPreFix = "myObject";
//        if (adminPreFix != null) {
            AdminRewriteFilter.adminPrefix = adminUrl;
            webApplicationContext.getServletContext().setAttribute("adminprefix", adminUrl);
        //}
        //后台登陆验证
        registry.addInterceptor(new UserSecurityInterceptor("admin","loginUser")).addPathPatterns("/admin/**").excludePathPatterns("/admin/login/**");
		
		// 后台权限验证
		registry.addInterceptor(new AuthorizeInterceptor()).excludePathPatterns("/admin/login/**,/").excludePathPatterns("/error/**");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String root = System.getProperty("user.dir");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + root + "\\upload\\");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //AdminPreFixSetting adminPreFix = setting.getSetting(AdminPreFixSetting.class);
        //if(!StringHelper.IsNullOrEmpty(adminPreFix.getAdminUrl())){
            String loginUrl="/"+adminUrl+"/login";
            registry.addRedirectViewController("/login",loginUrl);
            registry.addRedirectViewController("/admin",loginUrl);
            registry.addRedirectViewController("/"+adminUrl, loginUrl);
        //}
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }

    /**
     * 注册过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        AdminRewriteFilter adminRewriteFilter = new AdminRewriteFilter();
        registrationBean.setFilter(adminRewriteFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new UploadServlet(), "/uploader");
        bean.setMultipartConfig(new MultipartConfigElement(""));
        return bean;
    }


}
