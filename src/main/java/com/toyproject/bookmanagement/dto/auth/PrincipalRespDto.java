package com.toyproject.bookmanagement.dto.auth;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalRespDto {
	private int userId;
	private String email;
	private String name;
	private String authorities;
}
