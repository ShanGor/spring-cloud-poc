package tech.comfortheart.gateway;

import org.junit.Test;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class SimpleTests {
    Logger log = LoggerFactory.getLogger(SimpleTests.class);
    @Test
    public void testMono() throws InterruptedException {
        Mono myHey = Mono.fromSupplier(() -> {
            return "hey";
        });
        myHey.subscribe(hey -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(hey);

        });
        myHey.doOnSuccess(you -> {
           System.out.println("Completed " + you);
        });

        System.out.println("first");
        Mono mono = Mono.just("hello");
        mono.subscribe(hey -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(hey);
        });
        System.out.println("world");
        Thread.currentThread().join(1000);
    }

    @Test
    public void testSubscribe() {
        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println("Dummy: " + i),
                error -> System.err.println("Error " + error),
                () -> {System.out.println("Dummy: Done");},
                s -> s.request(10));
//        ints.subscribe(ss);
        Mono.just(20).subscribe(ss);
        System.out.println("end");
    }

    @Test
    public void testFlux() {
        Flux.range(1, 20)
                .subscribe(hey-> {
                    System.out.println(hey);
        });

        System.out.println(100);
    }

    @Test
    public void testT() {
        Field[] fields = O.class.getDeclaredFields();

        Stream.of(fields).parallel().forEach(a -> {
            log.info(a.getName());
        });
    }

    static class O {
        String a;
        String b;
    }
}

class SampleSubscriber<T> extends BaseSubscriber<T> {

    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(1);
    }

    public void hookOnNext(T value) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Subscriber: " + value);
        request(1);
    }
}