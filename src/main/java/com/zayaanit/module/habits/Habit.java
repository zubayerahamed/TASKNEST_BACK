package com.zayaanit.module.habits;

import java.time.LocalDate;

import com.zayaanit.model.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "habits")
@EqualsAndHashCode(callSuper = true)
public class Habit extends AbstractModel<Long> {

	private static final long serialVersionUID = -3623380084915279300L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "title", length = 20, nullable = false)
	private String title;

	@Lob
	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "is_completed", length = 1, nullable = false, columnDefinition = "BIT DEFAULT 0")
	private Boolean isCompleted;

}
