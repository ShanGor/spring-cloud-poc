package tech.comfortheart.gateway;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class RestPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestPocApplication.class, args);
	}

	Vertx vertx = null;

	@Bean
	Vertx initVertx() {
		if (vertx == null) {
			vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(Runtime.getRuntime().availableProcessors() * 2));
		}

		return vertx;
	}

}
