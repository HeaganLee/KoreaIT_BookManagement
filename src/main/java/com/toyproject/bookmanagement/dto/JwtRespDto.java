package com.toyproject.bookmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Builder // 인자 순서를 생각하지 않고 객체를 생성가능
@Data  	 // Getter, Setter, EqualsAndHashCode, ToString 자동생성
public class JwtRespDto {
	private String grantType;
	private String accessToken;
}