package tech.comfortheart.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class MainController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String port;

    @GetMapping("/")
    public String hello() {
        return "Hello world at " + appName + ":" + port;
    }

    @GetMapping("/download")
    public Mono<Resource> download() {
        return Mono.just(new FileSystemResource("/Users/sam/Downloads/redis-5.0.5.tar"));
    }
}
