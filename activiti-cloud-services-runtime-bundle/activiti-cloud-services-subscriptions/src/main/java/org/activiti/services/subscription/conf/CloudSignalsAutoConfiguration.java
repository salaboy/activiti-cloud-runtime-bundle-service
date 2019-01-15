package org.activiti.services.subscription.conf;



import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConditionalOnProperty(value = "activiti.cloud.signals.enabled", matchIfMissing = true)
@PropertySource("classpath:config/cloud-signals.properties")
@ComponentScan({"org.activiti.services.subscription","org.activiti.services.subscriptions.behavior"})
public class CloudSignalsAutoConfiguration {

}
