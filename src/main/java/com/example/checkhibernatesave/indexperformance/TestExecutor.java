package com.example.checkhibernatesave.indexperformance;

import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;
import org.apache.commons.math3.stat.StatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestExecutor {

    @Autowired
    private JarvisLockingService lockingService;

    private Logger logger = LoggerFactory.getLogger(TestExecutor.class);

    List<Double> times = new Vector<>();
    Long startTestingTime = 0L;

    public void startMasterThreads(int numThreads) {
        logger.info("Start a test with " + numThreads +  " threads");
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        startTestingTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {

                while (!Thread.currentThread().isInterrupted()) {
                    runOnce();
                }
            });
        }
    }

    @PreDestroy
    public void destroy() {
        sleep(5);
        Long seconds = (System.currentTimeMillis() - startTestingTime)/1000;
        long throughput = times.size() / seconds;
        double[] results = new double[times.size()];
        for (int i = 0; i < results.length; i++) {
            results[i] = times.get(i);
        }

        double mean = StatUtils.mean(results);
        double p50 = StatUtils.percentile(results, 50);
        double p75 = StatUtils.percentile(results, 75);
        double p95 = StatUtils.percentile(results, 95);
        double p99 = StatUtils.percentile(results, 99);

        logger.info("Total time: " + seconds + ", Total requests: " + times.size() + ", Throughput: " + throughput);
        logger.info(String.format("Mean: %f, p50: %f, p75: %f: p95: %f: p99: %f", mean, p50, p75, p95, p99));
    }

    private void runOnce() {
        long startTime = System.nanoTime();
        Optional<String> maybeCityCode = lockingService.acquireNextAvailableCity();
        long executionTime = System.nanoTime() - startTime;

        if (maybeCityCode.isPresent()) {
            logger.info("Thread " + Thread.currentThread().getId() +  ": Get city " + maybeCityCode.get() +  ", waiting time = " + executionTime + " ms");
            times.add(executionTime + 0.);
        } else {
            logger.info("No city to get, sleeping...");
            sleep(1);
        }
    }



    private void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
