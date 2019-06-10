package tech.comfortheart.gateway;

import io.vertx.core.Vertx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {
	@Autowired
	Vertx vertx

	@Test
	public void contextLoads() {
	}

	@Test
	public void testVertx() throws InterruptedException {
		vertx.setPeriodic(10, { id ->
			println("Timer fired!")
		})

		vertx.eventBus().

		Thread.currentThread().join(100)
	}
}
