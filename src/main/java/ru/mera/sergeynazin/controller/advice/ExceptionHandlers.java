package ru.mera.sergeynazin.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: 11/11/17 resolve Order vs Priority
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Priority(Integer.MIN_VALUE)
@RestControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    private static ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final HttpServletRequest request, final NotFoundException ex) {
        logger.info("NotFoundException Occurred:: URL=" +request.getRequestURL()+"\n"+
                    "Message=\""+ex.getLocalizedMessage()+"\"");
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND,"Entity is not found", ex));
    }

    @ExceptionHandler(NotEnoughPermissionException.class)
    public ResponseEntity<?> handleNotEnoughPermission(final HttpServletRequest request, final NotEnoughPermissionException ex) {
        // TODO: 11/9/17 URL
        logger.info("NotEnoughPermissionException Occurred:: URL=" +request.getRequestURL()+"\n"+
                    "Message=\""+ex.getLocalizedMessage()+"\"");
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, "Permission Denied!", ex));
    }

    @ExceptionHandler(CreatingAlreadyExistentException.class)
    public ResponseEntity<?> handleCreatingAlreadyExistentException(final HttpServletRequest request, final CreatingAlreadyExistentException ex) {
        logger.info("CreatingAlreadyExistentException Occurred:: URL=" +request.getRequestURL()+"\n"+
            "Message=\""+ex.getLocalizedMessage()+"\"");
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Such Entity already exists", ex));
    }

    //@ExceptionHandler(HttpMessageNotReadableException.class)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info("HttpMessageNotReadableException Occurred:: HttpMessage=" +request.getDescription(true)+"\n"+
                    "Message=\""+ex.getLocalizedMessage()+"\"");
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON/XML request", ex));
    }

    /**
     * Just a demo method to show how it COULD be without wrapping it with ResponseEntity
     * @param request current {@link HttpServletRequest}
     * @param ex exception to handle
     * @return {@link ApiError} entity with custom stuff inside
     *          which would be wrapped with ResponseEntity due to {@link RestControllerAdvice}
     */
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "Sample returning plain object for to @Rest.. to work")
    //@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiError handleNotEnoughPermission(final HttpServletRequest request, final HttpMediaTypeNotSupportedException ex) {
        // TODO: 11/9/17 URI
        logger.info("HttpMediaTypeNotSupportedException Occurred:: URL=" +request.getRequestURI()+"\n"+
            "Message=\""+ex.getLocalizedMessage()+"\"");

        return new ApiError(HttpStatus.FORBIDDEN, "Permission Denied!", ex);
    }

    //@ExceptionHandler(MethodArgumentNotValidException.class)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info("MethodArgumentNotValidException Occurred:: request = [" + request.getContextPath() + "], " +
                    "\nexception = [" + ex.getLocalizedMessage() + "]"+
                    "\nCurrent Thread is :: " + Thread.currentThread().getName());

        final List<ApiSubError> errors = new ArrayList<>();

        errors.addAll(
            ex.getBindingResult()
                .getFieldErrors()
                .parallelStream()
                .peek(ExceptionHandlers::log)
                .map(er -> new ApiValidationError(er.getObjectName(), er.getField(), er.getRejectedValue(), er.getDefaultMessage()))
                .collect(Collectors.toList()));

        errors.addAll(
            ex.getBindingResult()
                .getGlobalErrors()
                .parallelStream()
                .peek(ExceptionHandlers::log)
                .map(er -> new ApiValidationError(er.getObjectName(), er.getDefaultMessage()))
                .collect(Collectors.toList()));


        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Wrong values have been passed", ex, errors);
        return super.handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);

    }

    private static void log(final FieldError er) {
        logger.info("FieldError with: ObjectName = [" + er.getObjectName() + "], Field = [" + er.getField() + "], RejectedValue = [" + er.getRejectedValue() + "], DefaultMessage = [" + er.getDefaultMessage() + "]");
    }
    private static void log(final ObjectError er) {
        logger.info("ObjectError with: ObjectName = [" + er.getObjectName() + "], DefaultMessage = [" + er.getDefaultMessage() + "]");
    }

//    private String resolveLocalizedErrorMessage(FieldError fieldError) {
//        Locale currentLocale =  LocaleContextHolder.getLocale();
//        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
//
//        //If the message was not found, return the most accurate field error code instead.
//        //You can remove this check if you prefer to get the default error message.
//        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
//            String[] fieldErrorCodes = fieldError.getCodes();
//            localizedErrorMessage = fieldErrorCodes[0];
//        }
//
//        return localizedErrorMessage;
//    }
}