package dev.rhapsy.d4j.client.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoadBalancerConfiguration.class);

    // Creando un load balancer del lado del cliente
    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {
        log.info("Configuring Load balancer to prefer same instance");
        return ServiceInstanceListSupplier.
                builder().withBlockingDiscoveryClient() // todas las peticiones usa la misma instancia
                .build(context);
    }
}
