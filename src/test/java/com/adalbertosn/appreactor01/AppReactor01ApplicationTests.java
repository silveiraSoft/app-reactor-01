package com.adalbertosn.appreactor01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class AppReactor01ApplicationTests {
    private static final Logger logger = LogManager.getLogger();
    @Test
    void contextLoads() {
    }

    @Test
    void test3() {
        logger.info("Test mapper3 ");
        //Flux.interval(Duration.ofMillis(60))
        Flux.range(1,5)
                //.doOnNext(e -> System.out.println("Before: " + e) )
                .doOnNext(e -> logger.info("Before: " + e) )
                .subscribe();
    }

    @Test
    void test4() {
        logger.info("Test test4 ");
         Flux.interval(Duration.of(1000, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

    @Test
    void mappers1()  throws InterruptedException {
        Flux.interval(Duration.ofMillis(100))
                .take(5)
                .doOnNext(before -> System.out.println("Before: " + before))
                .flatMap(before ->
                        Flux.just(before)
                )
                .doOnNext(after -> System.out.println("--> After: " + after.toString()))
                .subscribe();
        Thread.sleep(1000);
    }

    @Test
    void mappers2()  throws InterruptedException {
        Flux.interval(Duration.ofMillis(100))
                .take(5)
                .doOnNext(before -> System.out.println("Before: " + before))
                .flatMap(before ->
                        Flux.interval(Duration.ofMillis(60))
                        .take(3)
                        .map(i-> "From " +  before + ", at " + (Instant.now().toEpochMilli() % 1000) + " : " + i)
                )
                .doOnNext(after -> System.out.println("--> After: " + after.toString()))
                //.doOnNext(after -> System.out.println("--> After: " + after))
                //        .then()

                .subscribe();

        Thread.sleep(1000);
    }
}
