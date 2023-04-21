package com.toyproject.bookmanagement.security.jwt;


import java.nio.charset.MalformedInputException;
import java.rmi.server.ExportException;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.toyproject.bookmanagement.dto.JwtRespDto;
import com.toyproject.bookmanagement.security.PrincipalUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class JwtTokenProvider {
	private final Key key;
	
	public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	public JwtRespDto generateToken(Authentication authentication) {
		
		String authorities = null;
		StringBuilder builder = new StringBuilder();
		
		authentication.getAuthorities().forEach(authority -> {
			builder.append(authority.getAuthority() + ",");
		});
		builder.delete(builder.length() - 1, builder.length());
		
		String auhorities = builder.toString();
		
		Date tokenExprieDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24)); // 현재시간 + 하루
		
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())		//토큰의 제목
				.claim("auth", auhorities)						// auth
				.setExpiration(tokenExprieDate)						// 토큰만료시간
				.signWith(key, SignatureAlgorithm.HS256) 	// 토큰 암호화
				.compact();
		
		return  JwtRespDto.builder().grantType("Bearer").accessToken(accessToken).build();
		}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("invalid JWT Token", e);
		} catch(ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch(UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch(IllegalArgumentException e) {
			log.info("IllegalArgumentException JWT Token");
		} catch(Exception e) {
			log.info("JWT Token Error");
		}
		
		return false;
	}
	
	public String getToken(String token) {
		String type = "Bearer";
		if(StringUtils.hasText(token) && token.startsWith(type)) {
			return token.substring(type.length() + 1);
		}
		return null;
	}
		

	
	}
	

