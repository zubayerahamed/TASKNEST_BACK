package com.zayaanit.module.habits;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitReqDto {

	@NotBlank(message = "Title required")
	@Size(min = 1, max = 20, message = "Title must be 1 to 20 characters long")
	private String title;
	private String description;
	@NotNull(message = "Category required")
	private Long categoryId;
	@NotNull(message = "Start date required")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	public Habit getBean() {
		return Habit.builder()
				.title(title)
				.description(description)
				.categoryId(categoryId)
				.startDate(startDate)
				.build();
	}
}
