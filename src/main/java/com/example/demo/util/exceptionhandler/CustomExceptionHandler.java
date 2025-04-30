//package com.example.demo.util.exceptionhandler;
//
//
//
//import com.example.msfaastransactionlocation.config.Configs;
//import com.example.msfaastransactionlocation.dto.response.ExceptionResponse;
//import com.example.msfaastransactionlocation.util.logging.Logging;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.support.WebExchangeBindException;
//import org.springframework.web.reactive.function.server.ServerRequest;
//
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Slf4j
//@ControllerAdvice
//@RequiredArgsConstructor
//public class CustomExceptionHandler {
//    private final Configs configs;
//    private final Logging logging = new Logging();
//
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<Object> customException(CustomException ex, ServerRequest request) {
//
//        HttpStatus status = ex.getStatus();
//
//
//
//        ExceptionResponse exceptionResponse = new ExceptionResponse(
//                new ExceptionResponse.HeaderException(
//                        ex.getRequestRefId(),
//                        configs.getResponseConfig().getFailedResponseCode(),
//                        ex.getMessage(),
//                        String.valueOf(Instant.now().getEpochSecond())
//                ),
//                new ExceptionResponse.BodyOutput(
//                        ex.getMessage(),
//                        request.uri().toString()
//                )
//        );
//
//        logging
//                .setLogLevel("error")
//                .setTransactionID(UUID.randomUUID().toString())
//                .setProcess(ex.getProcess())
//                .setRequest(ex.getObject())
//                .setResponseMsg(ex.getMessage())
//                .setResponse(exceptionResponse)
//                .write();
//        return ResponseEntity.status(status)
//                .body(exceptionResponse);
//    }
//
//
//
//
//    @ExceptionHandler(WebExchangeBindException.class)
//    public ResponseEntity<Object> handleValidationExceptions(WebExchangeBindException ex) {
//        Map<String, String> errors = new HashMap<>();
//
//
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            String rejectedValue = error.getRejectedValue() != null ? error.getRejectedValue().toString() : "null";
//            String errorMessage = error.getDefaultMessage() + " (Rejected value: {" + rejectedValue + "})";
//            errors.put(error.getField(), errorMessage);
//        });
//
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//
//        // Combine all errors into a single description message
//        String combinedErrorMessage = String.join(", ", errors.values());
//
//        ExceptionResponse exceptionResponse = new ExceptionResponse(
//                new ExceptionResponse.HeaderException(
//                        UUID.randomUUID().toString(),
//                        configs.getResponseConfig().getFailedResponseCode(),
//                        combinedErrorMessage,
//                        String.valueOf(Instant.now().getEpochSecond())
//                ),
//                new ExceptionResponse.BodyOutput(
//                        combinedErrorMessage,
//                        "Invalid input data"
//                )
//        );
//
//        logging
//                .setLogLevel("warn")
//                .setResponseCode(status.value())
//                .setTransactionID(UUID.randomUUID().toString())
//                .setProcess("Input Validation")
//                .setRequest(null)
//                .setResponseMsg(ex.getMessage())
//                .setResponse(exceptionResponse)
//                .write();
//        return ResponseEntity.status(status)
//                .body(exceptionResponse);
//    }
//
//
//}
