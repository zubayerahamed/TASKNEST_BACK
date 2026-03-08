package com.zayaanit.module.habits.tracker;

import java.time.LocalDate;

import com.zayaanit.model.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "habit_tracker")
@EqualsAndHashCode(callSuper = true)
public class HabitTracker extends AbstractModel<Long> {
	
	private static final long serialVersionUID = 5644557326887840533L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "habit_id")
	private Long habitId;

	@Column(name = "habit_date", nullable = false)
	private LocalDate habitDate;
}
