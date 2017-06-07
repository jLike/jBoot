
package io.github.jbooter.config.metrics;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;

import io.github.jbooter.config.JBooterProperties;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;

@Configuration
@ConditionalOnClass(CollectorRegistry.class)
public class PrometheusRegistry implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(PrometheusRegistry.class);

    private final MetricRegistry metricRegistry;

    private final JBooterProperties jBooterProperties;

    public PrometheusRegistry(MetricRegistry metricRegistry, JBooterProperties jBooterProperties) {
        this.metricRegistry = metricRegistry;
        this.jBooterProperties = jBooterProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (jBooterProperties.getMetrics().getPrometheus().isEnabled()) {
            String endpoint = jBooterProperties.getMetrics().getPrometheus().getEndpoint();
            log.info("Initializing Metrics Prometheus endpoint at {}", endpoint);
            CollectorRegistry collectorRegistry = new CollectorRegistry();
            collectorRegistry.register(new DropwizardExports(metricRegistry));
            servletContext
                .addServlet("prometheusMetrics", new MetricsServlet(collectorRegistry))
                .addMapping(endpoint);
        }
    }
}
