package pl.jokes.jokesms.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsBeans {
  @Bean
  public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint constraint = new SecurityConstraint();
        constraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        constraint.addCollection(collection);
        context.addConstraint(constraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(httpConnector());
    return tomcat;
  }

  @Bean
  public Connector httpConnector() {
    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    connector.setScheme("http");
    connector.setPort(8082);
    connector.setSecure(false);
    connector.setRedirectPort(9443);
    return connector;
  }
}
