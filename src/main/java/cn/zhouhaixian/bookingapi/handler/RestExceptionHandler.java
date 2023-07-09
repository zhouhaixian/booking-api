package cn.zhouhaixian.bookingapi.handler;

import cn.zhouhaixian.bookingapi.dto.ResultDTO;
import cn.zhouhaixian.bookingapi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultDTO exception(Exception e) {
        log.error(e.getMessage(), e);
        return new ResultDTO(ResultDTO.Status.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultDTO userNotFoundException() {
        return new ResultDTO(ResultDTO.Status.USER_NOT_FOUND);
    }

    @ExceptionHandler(ConfigurationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultDTO configurationNotFoundException() {
        return new ResultDTO(ResultDTO.Status.CONFIGURATION_NOT_FOUND);
    }

    @ExceptionHandler(FrequentBookingException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResultDTO frequentBookingException() {
        return new ResultDTO(ResultDTO.Status.FREQUENT_BOOKING);
    }

    @ExceptionHandler(DuplicateBookingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResultDTO duplicateBookingException() {
        return new ResultDTO(ResultDTO.Status.DUPLICATE_BOOKING);
    }

    @ExceptionHandler(DuplicateInitializationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResultDTO duplicateInitializationException() {
        return new ResultDTO(ResultDTO.Status.DUPLICATE_INITIALIZATION);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultDTO authenticationException() {
        return new ResultDTO(ResultDTO.Status.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultDTO accessDeniedException() {
        return new ResultDTO(ResultDTO.Status.FORBIDDEN);
    }

    @ExceptionHandler(IllegalAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultDTO illegalAccessException() {
        return new ResultDTO(ResultDTO.Status.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return new ResultDTO(ResultDTO.Status.BAD_REQUEST.getCode(), fieldErrors.get(0).getDefaultMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO illegalArgumentException(IllegalArgumentException e) {
        return new ResultDTO(ResultDTO.Status.BAD_REQUEST.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO httpMessageNotReadableException() {
        return new ResultDTO(ResultDTO.Status.BAD_REQUEST);
    }
}
