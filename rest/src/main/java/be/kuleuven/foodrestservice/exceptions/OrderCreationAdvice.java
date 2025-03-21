package be.kuleuven.foodrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class OrderCreationAdvice {

    @ResponseBody
    @ExceptionHandler(OrderCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String orderCreationExceptionHandler(OrderCreationException ex) {
        return ex.getMessage();
    }
}
