package com.yuanyeex.metax.collectorbootstrap;

import com.yuanyeex.metax.collector.netcap.config.NetcapConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.concurrent.CountDownLatch;

/**
 * Entry of collector
 *
 * @author yuanyeex
 */
@Import(NetcapConfig.class)
@SpringBootApplication
public class CollectorBootstrapApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CollectorBootstrapApplication.class, args);
        new CountDownLatch(1).await();
    }

}
