package dev.archimedes.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private String urlPath;
    private Object object;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;

    public static ApiResponse generateResponse(String message){
        return ApiResponse.builder()
                .message(message)
                .date(new Date())
                .build();
    }
}
