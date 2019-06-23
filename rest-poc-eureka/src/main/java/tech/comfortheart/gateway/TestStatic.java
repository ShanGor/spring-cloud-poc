package tech.comfortheart.gateway;

import reactor.core.publisher.Mono;

import static tech.comfortheart.gateway.Constant.REQUEST_ID;

public class TestStatic {

    public static Mono<String> getId() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.getOrEmpty(REQUEST_ID)).map(opt -> {
                   if (opt.isPresent()) {
                       return opt.get().toString();
                   } else {
                       return null;
                   }
                });
    }
}
