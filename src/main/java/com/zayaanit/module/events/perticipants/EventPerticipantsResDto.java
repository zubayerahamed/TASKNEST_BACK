package com.zayaanit.module.events.perticipants;

import com.zayaanit.enums.PerticipantType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPerticipantsResDto {

	private Long eventId;
	private Long userId;
	private PerticipantType perticipantType;
	private Boolean isReminderSent;
	private ParticipantUser participantUser;
}