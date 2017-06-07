
package io.github.jbooter.config.apidoc;

import static springfox.documentation.builders.PathSelectors.regex;

import io.github.jbooter.config.JBooterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import com.fasterxml.classmate.TypeResolver;

import io.github.jbooter.config.JBooterConstants;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnClass({ ApiInfo.class, BeanValidatorPluginsConfiguration.class })
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@Profile(JBooterConstants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    /**
     * Swagger Springfox configuration.
     *
     * @param jBooterProperties the properties of the application
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket swaggerSpringfoxDocket(JBooterProperties jBooterProperties) {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                jBooterProperties.getSwagger().getContactName(),
                jBooterProperties.getSwagger().getContactUrl(),
                jBooterProperties.getSwagger().getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
                jBooterProperties.getSwagger().getTitle(),
                jBooterProperties.getSwagger().getDescription(),
                jBooterProperties.getSwagger().getVersion(),
                jBooterProperties.getSwagger().getTermsOfServiceUrl(),
            contact,
                jBooterProperties.getSwagger().getLicense(),
                jBooterProperties.getSwagger().getLicenseUrl());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .forCodeGeneration(true)
            .directModelSubstitute(java.nio.ByteBuffer.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .select()
            .paths(regex(jBooterProperties.getSwagger().getDefaultIncludePattern()))
            .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    @Bean
    PageableParameterBuilderPlugin pageableParameterBuilderPlugin(TypeNameExtractor nameExtractor,
        TypeResolver resolver) {

        return new PageableParameterBuilderPlugin(nameExtractor, resolver);
    }
}
