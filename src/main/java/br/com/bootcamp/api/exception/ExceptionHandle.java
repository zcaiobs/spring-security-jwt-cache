package br.com.bootcamp.api.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandle {

    MessageSource messageSource;

    ExceptionHandle(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ExceptionDto> handle(MethodArgumentNotValidException exception){
        List<ExceptionDto> errosDto = new ArrayList<>();
        List<FieldError> fieldsErrors = exception.getBindingResult().getFieldErrors();
        fieldsErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ExceptionDto exceptionDto = new ExceptionDto(e.getField(), message);
            errosDto.add(exceptionDto);
        });
        return errosDto;
    }
}
