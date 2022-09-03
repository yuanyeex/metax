package com.yuanyeex.metax.collectorbootstrap;

import com.yuanyeex.metax.collector.Collector;
import com.yuanyeex.metax.collector.CollectorRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.concurrent.CountDownLatch;

/**
 * Entry of collector
 *
 * @author yuanyeex
 */
@Import({BootstrapConfiguration.class})
@SpringBootApplication
public class CollectorBootstrapApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext application = SpringApplication.run(CollectorBootstrapApplication.class, args);
        startAllCollectors(application);
        new CountDownLatch(1).await();
    }

    private static void startAllCollectors(ConfigurableApplicationContext context) {
        CollectorRegistry collectorRegistry = context.getBean(CollectorRegistry.class);
        for (Collector collector : collectorRegistry.listAll()) {
            collector.start();
        }
    }
}
