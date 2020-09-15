package de.alpharogroup.user.auth.dto;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.http.HttpStatus;

import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThrowableReport
{

	private String message;

	private LocalDateTime occurred;

	private String requestDescription;

	private HttpStatus status;
}
