

package io.github.jbooter.security.uaa;

import io.github.jbooter.config.JBooterProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
@ConditionalOnClass({ ClientCredentialsResourceDetails.class, LoadBalancerClient.class })
@ConditionalOnProperty("jbooter.security.client-authorization.client-id")
public class UaaAutoConfiguration {

    private JBooterProperties jBooterProperties;

    public UaaAutoConfiguration(JBooterProperties jBooterProperties) {
        this.jBooterProperties = jBooterProperties;
    }

    @Bean
    public LoadBalancedResourceDetails loadBalancedResourceDetails(LoadBalancerClient loadBalancerClient) {
        LoadBalancedResourceDetails loadBalancedResourceDetails = new LoadBalancedResourceDetails(loadBalancerClient);
        JBooterProperties.Security.ClientAuthorization clientAuthorization = jBooterProperties.getSecurity()
            .getClientAuthorization();
        loadBalancedResourceDetails.setAccessTokenUri(clientAuthorization.getAccessTokenUri());
        loadBalancedResourceDetails.setTokenServiceId(clientAuthorization.getTokenServiceId());
        loadBalancedResourceDetails.setClientId(clientAuthorization.getClientId());
        loadBalancedResourceDetails.setClientSecret(clientAuthorization.getClientSecret());
        return loadBalancedResourceDetails;
    }
}
