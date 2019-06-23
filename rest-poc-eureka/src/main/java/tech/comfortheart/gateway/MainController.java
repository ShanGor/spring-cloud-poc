package tech.comfortheart.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import static tech.comfortheart.gateway.Constant.REQUEST_ID;


@RestController
public class MainController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String port;

    @GetMapping("/")
    public Mono<String> hello() {
        return Mono.subscriberContext()
                .map(context -> context.getOrEmpty(REQUEST_ID))
                .map(opt -> {
            if (opt.isPresent()) {
                System.out.println("Got X-REQUEST-ID: " + opt.get());
                return "Hello world " + opt.get() + " at " + appName + ":" + port;
            } else {
                return "Hello world at " + appName + ":" + port;
            }
        });
    }

    @GetMapping("/static")
    public Mono<String> testStatic() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.getOrEmpty(REQUEST_ID))
                .flatMap(opt ->
            WebClient.create("http://localhost:8080")
                    .get()
                    .uri("/hey").headers(httpHeaders -> {
                if (opt.isPresent())
                    httpHeaders.add("HEY-ID", opt.get().toString());
                else
                    httpHeaders.add("HEY-ID", "NO HEY ID");
            }).retrieve().bodyToMono(String.class)
        );

    }

    private Mono<String> toGetId() {
        return TestStatic.getId().doOnNext(str -> System.out.println("GOT id: " + str));
    }

    @GetMapping("/hey")
    public String hey(ServerHttpRequest request) {
        System.out.println("HEY ID is: " + request.getHeaders().get("HEY-ID"));
        return "hey\n";
    }

    /**
     * Zero copy mechanism.
     * @return
     */
    @GetMapping("/download")
    public Mono<Resource> download() {
        return Mono.just(new FileSystemResource("/Users/sam/Downloads/redis-5.0.5.tar"));
    }
}
