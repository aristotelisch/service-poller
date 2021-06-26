package eu.happybit.poller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
  /**
   * Serve the swagger ui static files under /swagger-ui path
   *
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.43.0/");
  }
}
