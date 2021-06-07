package com.hvs.mutant.filter;

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

    /**
     * Filter to preexecute to catch client IP host & put request-id in logger and headers response
     *
     * @param request  HttpServletRequest request received
     * @param response httpServletResponse response to send
     * @param chain    Filter to send request y response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            // get client ip host form request header
            String ipAddress = request.getHeader("X-Forward-For");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }

            // build random request id
            String sessionId = UUID.randomUUID().toString().toUpperCase();

            // setting request id & remote ip to logger (MDC)
            MDC.put("request-id", sessionId);
            MDC.put("remote-ip", ipAddress);
            logger.debug("begin request");

            // setting header response with the request id
            response.addHeader("X-REQUEST-ID", sessionId);
            chain.doFilter(request, response);
            logger.debug("end request");
        } finally {
            // clean MDC request id & remote ip from Logger
            MDC.remove("request-id");
            MDC.remove("remote-ip");
        }
    }
}

