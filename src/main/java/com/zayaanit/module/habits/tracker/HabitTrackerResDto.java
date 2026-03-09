package com.zayaanit.module.habits.tracker;

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
public class HabitTrackerResDto {

	private Long id;
	private Long habitId;
	private LocalDate completeDate;

	public HabitTrackerResDto(HabitTracker habitTracker) {
		BeanUtils.copyProperties(habitTracker, this);
	}
}
