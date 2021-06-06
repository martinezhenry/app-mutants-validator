package com.hvs.mutant.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestResponseFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(RequestResponseFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String ipAddress = request.getHeader("X-Forward-For");
            if(ipAddress== null){
                ipAddress = request.getRemoteAddr();
            }

            String sessionId = UUID.randomUUID().toString().toUpperCase();
            MDC.put("request-id", sessionId);
            MDC.put("remote-ip", ipAddress);
            logger.debug("begin request");
            response.addHeader("X-REQUEST-ID", sessionId);
            chain.doFilter(request, response);
            logger.debug("end request");
        } finally {
            MDC.remove("request-id");
            MDC.remove("remote-ip");
        }
    }
}

