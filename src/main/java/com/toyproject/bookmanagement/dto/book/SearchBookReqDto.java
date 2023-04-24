package com.toyproject.bookmanagement.dto.book;

import lombok.Data;

@Data
public class SearchBookReqDto {
	private int page;
	private int category;
	private String serarchValue;
}
