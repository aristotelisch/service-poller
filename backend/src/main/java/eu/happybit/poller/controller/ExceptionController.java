package eu.happybit.poller.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import eu.happybit.poller.domain.ApiError;
import eu.happybit.poller.exception.ServiceNotFoundException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

    ApiError body =
        ApiError.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .errors(errors)
            .build();

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ServiceNotFoundException.class)
  public ResponseEntity<ApiError> entityNotFound(ServiceNotFoundException ex) {

    return new ResponseEntity<>(
        ApiError.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .errors(List.of(ex.getLocalizedMessage()))
            .build(),
        HttpStatus.NOT_FOUND);
  }
}
