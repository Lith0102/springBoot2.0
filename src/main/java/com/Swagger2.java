package com;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
	
	@Bean
    public Docket restApi() {  
        return new Docket(DocumentationType.SWAGGER_2)  
                .apiInfo(apiInfo())  
                .groupName("APP请求接口")  
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)  
                .forCodeGeneration(true)  
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();  
    }  
    
  
    private ApiInfo apiInfo() {  
        return new ApiInfoBuilder()  
                .title("API列表")
                .description("用于APP接口调试及查询")  
                .version("1.0")
                .license("汇智精英")
               /* .licenseUrl("http://wz.qhddqsp.com")*/
                .build();  
    }  
}
