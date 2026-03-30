package hkmu.wadd.config;

import org.apache.catalina.Context;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> factory.addContextCustomizers((Context context) -> {
            // Force Tomcat to look at the exact folder in your project directory
            String relativePath = "src/main/webapp";
            File docBaseFile = new File(relativePath);
            
            if (docBaseFile.exists()) {
                context.setDocBase(docBaseFile.getAbsolutePath());
            }
        });
    }
}