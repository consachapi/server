package pe.regioncusco.llankasun.server.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.regioncusco.llankasun.server.commons.ErrorMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            //AccessDeniedException.class,
            HeaderFilterException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> unauthorizedRequest(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                HttpStatus.UNAUTHORIZED.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> notFoundRequest(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                HttpStatus.NOT_FOUND.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler({
            BadGatewayException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> badGateway(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_GATEWAY.value(),
                new Date(),
                HttpStatus.BAD_GATEWAY.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.BAD_GATEWAY);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            ConflictException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> conflict(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                HttpStatus.CONFLICT.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
            AccessDeniedException.class,
            ForbiddenException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> forbidden(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                HttpStatus.FORBIDDEN.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> exception(Exception ex, HttpServletRequest request){
        ex.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            DuplicateKeyException.class,
            WebExchangeBindException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> badRequest(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }
/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            FeignException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorMessage> feign(Exception ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                HttpStatus.BAD_REQUEST.toString(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }*/
}
