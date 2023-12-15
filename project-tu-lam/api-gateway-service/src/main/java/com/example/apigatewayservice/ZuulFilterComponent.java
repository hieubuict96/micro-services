package com.example.apigatewayservice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ZuulFilterComponent extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(ZuulFilterComponent.class);

    @Override
    public Object run() throws ZuulException {
        logger.info("***************** ");
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return "pre";
    }
}
