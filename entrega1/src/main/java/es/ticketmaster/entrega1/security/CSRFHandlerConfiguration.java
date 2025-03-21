package es.ticketmaster.entrega1.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CSRFHandlerConfiguration implements WebMvcConfigurer {
    
    /**
     * This method registers a custom interceptor (CSRFHandlerInterceptor) in the Spring configuration.
     * Its purpose is to handle CSRF security in the application.
     * @implNote Added @NonNull annotations to parameters to eliminate compile-time warnings.
     * @param registry register the interceptor.
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new CSRFHandlerInterceptor());
    }
}

class CSRFHandlerInterceptor implements HandlerInterceptor {

    /**
     * Its main purpose is to modify the response before it is sent to the client. 
     * This may include adding objects to the ModelAndView that will then be displayed in the view.
     * It is executed after the controller has processed the request but before the view (or response) 
     * is rendered and returned to the client.
     * @implNote Added @NonNull and @Nullable annotations to parameters to eliminate compile-time warnings.
     * @param request Contains information about the incoming HTTP request. 
     * @param response Represents the HTTP response that will be sent to the client. 
     * @param handler Represents the controller that processed the request. 
     * @param modelAndView (Optional, can be null) Contains the view and data to be sent to the response template. 
     * @throws Exception
     */
    @Override
    public void postHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response, 
                          final @NonNull Object handler, final @Nullable ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
            if (token != null) {
                modelAndView.addObject("token", token.getToken());
            }
        }
    }
}