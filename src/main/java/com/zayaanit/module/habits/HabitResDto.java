package com.zayaanit.module.habits;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitResDto {

	private Long id;
	private Long userId;
	private String title;
	private String description;
	private Long categoryId;
	private LocalDate startDate;
	private Boolean isCompleted;

	private int totalDaysFollowed;
	private String currentMonth;
	private String todayStatus;

	public HabitResDto(Habit habit) {
		BeanUtils.copyProperties(habit, this);
	}
}
