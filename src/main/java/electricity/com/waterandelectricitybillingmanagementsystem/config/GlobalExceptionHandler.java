package electricity.com.waterandelectricitybillingmanagementsystem.config;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleAllExceptions(Exception ex, HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/api/")) {
            return org.springframework.http.ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of(
                        "error", "Internal Server Error",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI()
                    ));
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("message", ex.getMessage());
        mav.setViewName("error/500");
        return mav;
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public Object handle404(Exception ex, HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/api/")) {
            return org.springframework.http.ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(java.util.Map.of(
                        "error", "Not Found",
                        "path", request.getRequestURI()
                    ));
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("error/404");
        return mav;
    }
}
