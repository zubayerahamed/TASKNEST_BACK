package com.zayaanit.module.habits.tracker;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitTrackerReqDto {

	@NotNull(message = "Habit id required")
	private Long habitId;

	public HabitTracker getBean() {
		return HabitTracker.builder()
				.habitId(habitId)
				.build();
	}
}
