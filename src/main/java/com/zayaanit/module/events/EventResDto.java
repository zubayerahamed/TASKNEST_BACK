package com.zayaanit.module.events;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zayaanit.module.documents.DocumentResDto;
import com.zayaanit.module.events.checklists.EventChecklistResDto;
import com.zayaanit.module.events.perticipants.EventPerticipantsResDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * @since Jul 3, 2025
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResDto {

	private Long id;
	private String title;
	private String description;
	private Long projectId;
	private Long categoryId;
	private LocalDate eventDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String location;
	private Boolean isReminderEnabled;
	private Integer reminderBefore; // How many minutes before to send reminder
	private String eventLink;
	private Boolean isReminderSent;
	private Boolean isCompleted;
	private List<EventChecklistResDto> checklists;
	private String projectName;
	private String categoryName;
	private List<DocumentResDto> documents;
	private List<EventPerticipantsResDto> participants = new ArrayList<>();

	public EventResDto(Event obj) {
		BeanUtils.copyProperties(obj, this);
	}

	public EventResDto(Event obj, List<EventPerticipantsResDto> participants) {
		BeanUtils.copyProperties(obj, this);
		this.participants = participants;
	}

	public static EventResDto from(Event obj) {
		EventResDto dto = new EventResDto();
		BeanUtils.copyProperties(obj, dto);
		return dto;
	}

	public static EventResDto from(Event obj, List<EventPerticipantsResDto> participants) {
		EventResDto dto = new EventResDto();
		BeanUtils.copyProperties(obj, dto);
		dto.setParticipants(participants);
		return dto;
	}
}
