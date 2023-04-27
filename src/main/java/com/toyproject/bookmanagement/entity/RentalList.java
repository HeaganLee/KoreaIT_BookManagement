package com.toyproject.bookmanagement.entity;

import com.toyproject.bookmanagement.dto.book.RentalListRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RentalList {
	private int bookListId;
	private String bookName;
	private int userId;
	
	public RentalListRespDto toDto() {
		return RentalListRespDto.builder()
				.bookListId(bookListId)
				.bookName(bookName)
				.userId(userId)
				.rentalStatus(userId == 0)
				.build();
	}
}
