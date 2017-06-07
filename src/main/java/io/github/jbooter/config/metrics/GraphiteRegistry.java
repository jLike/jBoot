
package io.github.jbooter.config.metrics;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

import io.github.jbooter.config.JBooterProperties;

@Configuration
@ConditionalOnClass(Graphite.class)
public class GraphiteRegistry {

    private final Logger log = LoggerFactory.getLogger(GraphiteRegistry.class);

    private final JBooterProperties jBooterProperties;

    public GraphiteRegistry(MetricRegistry metricRegistry, JBooterProperties jBooterProperties) {
        this.jBooterProperties = jBooterProperties;
        if (this.
            jBooterProperties.getMetrics().getGraphite().isEnabled()) {
            log.info("Initializing Metrics Graphite reporting");
            String graphiteHost = jBooterProperties.getMetrics().getGraphite().getHost();
            Integer graphitePort = jBooterProperties.getMetrics().getGraphite().getPort();
            String graphitePrefix = jBooterProperties.getMetrics().getGraphite().getPrefix();
            Graphite graphite = new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
            GraphiteReporter graphiteReporter = GraphiteReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .prefixedWith(graphitePrefix)
                .build(graphite);
            graphiteReporter.start(1, TimeUnit.MINUTES);
        }
    }
}
