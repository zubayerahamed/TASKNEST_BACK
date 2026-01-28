package com.zayaanit.module.events.perticipants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * @since Jan 28, 2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantUser{
	public String email;
	private String firstName;
	private String lastName;
	private byte[] thumbnail;
}
