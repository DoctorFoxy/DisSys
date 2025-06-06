package com.broker.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendRoutingController implements ErrorController {
    // Our routing needs to happen in 3 steps:
    //
    // 1: React to specific endpoints (like /api/items)
    // 2: Serve static files (like /index.html, /img/BasicHangoverCure.png)
    // 3: Handle Error/Frontend Routing
    //
    // The SPA frontend uses its own client-side routing (for example /myorders).
    // If the user refreshes one of these pages then our server needs to return the
    // frontend (index.html).
    //
    // Thus: This Controller returns the index.html to all 404-GET-requests which don't go to /api/...
    // Everything else returns the error as normal
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            String requestPath = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

            boolean isGet = request.getMethod().equals("GET");
            boolean is404 = statusCode == HttpStatus.NOT_FOUND.value();
            boolean isApiRequest = requestPath.startsWith("/api");

            boolean isFrontendRequest = isGet && is404 && !isApiRequest;
            if(isFrontendRequest) {
                return "forward:/index.html"; // return frontend entry-point
            }
        }

        // Otherwise return error like normal
        return null;
    }
}
