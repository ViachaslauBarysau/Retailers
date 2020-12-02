package by.itechart.retailers.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    HttpStatus httpStatus;

    String message;
}
