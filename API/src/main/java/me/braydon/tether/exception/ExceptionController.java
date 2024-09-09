package me.braydon.tether.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import me.braydon.tether.model.ErrorResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The route to handle errors for this app.
 *
 * @author Braydon
 */
@RestController
@RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
public final class ExceptionController extends AbstractErrorController {
    public ExceptionController(@NonNull ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }
    
    @RequestMapping @ResponseBody @NonNull
    public ResponseEntity<ErrorResponse> onError(@NonNull HttpServletRequest request) {
        Map<String, Object> error = getErrorAttributes(request, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE
        ));
        HttpStatus status = getStatus(request); // The status code
        return new ResponseEntity<>(new ErrorResponse(status, (String) error.get("message")), status);
    }
}