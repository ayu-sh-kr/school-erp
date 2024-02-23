package dev.archimedes.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private String urlPath;
    private HttpStatus httpStatus;
    private Object object;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;

    public static ApiResponse generateResponse(String message, HttpStatus httpStatus){
        return ApiResponse.builder()
                .message(message)
                .date(new Date())
                .httpStatus(httpStatus)
                .build();
    }
}
