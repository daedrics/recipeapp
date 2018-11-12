package com.recipeapp.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

	private final Parameter jwtTokenHeader = new ParameterBuilder().name("Authorization")
			.description("'Bearer <token>'. Alternatively a cookie containing the JWT can also be passed")
			.modelRef(new ModelRef("string")).parameterType("header").required(false).build();

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.redionmuraj.recipeapp")).paths(PathSelectors.any())
				.build().globalOperationParameters(Collections.singletonList(jwtTokenHeader));
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub

	}

	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub

	}

	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub

	}

	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// TODO Auto-generated method stub

	}

	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// TODO Auto-generated method stub

	}

	public void configurePathMatch(PathMatchConfigurer configurer) {
		// TODO Auto-generated method stub

	}

	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub

	}

	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub

	}

	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub

	}

	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub

	}

	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub

	}

	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub

	}

	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub

	}

	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub

	}

	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub

	}
}
