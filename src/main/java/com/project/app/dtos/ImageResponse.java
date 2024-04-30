package com.project.app.dtos;

import org.springframework.http.HttpStatus;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {

	private String imageName;
	private String message;
	private Boolean success;
	private HttpStatus status;
	private List<String> imageMultipleName;
}
