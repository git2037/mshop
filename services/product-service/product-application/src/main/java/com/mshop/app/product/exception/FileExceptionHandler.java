package com.mshop.app.product.exception;

import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.utils.StringUtils;
import com.mshop.app.product.constant.FileConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionHandler {

    private static final String SIZE_PARAM = "size";
    private static final String TYPES_PARAM = "types";

    @ExceptionHandler(value = {FileValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleFileValidationException(FileValidationException exception) {
        ErrorCode code = exception.getCode();
        return ApiResponse.buildFailResponse(code.getCode(), buildMessage(code));
    }

    private String buildMessage(ErrorCode errorCode) {
        String message = errorCode.getMessage();

        if (ProductServiceCode.FILE_SIZE_EXCEEDED.equals(errorCode)) {
            return message.replace(StringUtils.createPlaceholderParam(SIZE_PARAM),
                    String.valueOf(FileConstant.MAX_FILE_SIZE));
        } else if (ProductServiceCode.FILE_NOT_SUPPORTED.equals(errorCode)) {
            return message.replace(StringUtils.createPlaceholderParam(TYPES_PARAM),
                    FileConstant.ImageType.getFileSupported().toString());
        }
        return message;
    }
}
