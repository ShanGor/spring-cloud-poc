package tech.comfortheart.gateway;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static tech.comfortheart.gateway.Constant.REQUEST_ID;

@Component
public class LogFilter implements WebFilter {
    @Override
    @NonNull
    public Mono<Void> filter(
            @NonNull ServerWebExchange ex,
            @NonNull WebFilterChain chain) {

        return chain.filter(ex)
                .subscriberContext(
                        ctx -> addRequestHeadersToContext(ex.getRequest(), ctx)
                );
    }


    public Context addRequestHeadersToContext(ServerHttpRequest request, Context ctx) {
        if (request.getHeaders().containsKey(REQUEST_ID))
            return ctx.put(REQUEST_ID, request.getHeaders().get(REQUEST_ID).get(0));
        else
            return ctx;
    }
}
