package com.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Author: @Litonghui
 * Date: 2018/12/26 15:44
 * Content: 自定义错误页面
 */
@Component
public class MyErrorPageRegistrar implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage page400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
        ErrorPage page401 = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
        ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
        ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
        ErrorPage error500 = new ErrorPage(Throwable.class, "/error/500");
        registry.addErrorPages(page400, page401, page404, page500, error500);
    }
}
