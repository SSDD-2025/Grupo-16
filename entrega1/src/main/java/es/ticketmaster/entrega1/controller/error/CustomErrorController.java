package es.ticketmaster.entrega1.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404 Not Found");
                model.addAttribute("errorMessage", "Oops! The page you are looking for does not exist.");
            } 
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorCode", "403 Forbidden");
                model.addAttribute("errorMessage", "You don't have permission to access this page.");
            } 
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "500 Internal Server Error");
                model.addAttribute("errorMessage", "Something went wrong on our end.");
            }
            else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                model.addAttribute("errorCode", "503 Service Unavailable");
                model.addAttribute("errorMessage", "The server is currently unavailable. Please try again later.");
            }
            else {
                model.addAttribute("errorCode", statusCode + " Error");
                model.addAttribute("errorMessage", "An unexpected error has occurred.");
            }
        } 
        else {
            model.addAttribute("errorCode", "Unknown Error");
            model.addAttribute("errorMessage", "Something went wrong, but we are not sure what.");
        }
        return "error";
    }
}