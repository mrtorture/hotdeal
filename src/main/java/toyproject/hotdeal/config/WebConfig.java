package toyproject.hotdeal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toyproject.hotdeal.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    private String mapping = "/upload/**";
//    private String resourceUri = "file:///C:/Projects/Java/toyproject/hotdeal/upload/";
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(mapping)
//                .addResourceLocations(resourceUri);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/posts/new", "/posts/*/delete", "/posts/*/update", "/posts/wish", "/posts/my")
                .addPathPatterns("/comments/new", "/comments/*/delete", "/comments/*/update")
                .addPathPatterns("/votes/new", "/votes/delete")
                .addPathPatterns("/wishes/new", "/wishes/delete");
    }
}
