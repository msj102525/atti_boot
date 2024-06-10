package org.ict.atti_boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // WebMvcConfigurer 인터페이스에서 오버라이드한 메소드로,
    // CORS 관련 설정을 추가하는 데 사용됩니다.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 설정을 추가합니다. (/**는 모든 경로를 의미합니다.)
        registry.addMapping("/**")
                // 개발 단계에서는 일반적으로 프론트엔드 서버의 주소가 됩니다.
                .allowedOrigins("http://localhost:3000")
                // 해당 오리진에서 허용할 HTTP 메소드를 지정합니다.
                // GET, POST, PUT, DELETE, HEAD, OPTIONS 메소드를 허용합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                // 모든 HTTP 헤더를 요청에서 허용합니다.
                .allowedHeaders("*")
                // 쿠키나 인증과 관련된 정보를 포함한 요청을 허용합니다.
                .allowCredentials(true);
    }
}
