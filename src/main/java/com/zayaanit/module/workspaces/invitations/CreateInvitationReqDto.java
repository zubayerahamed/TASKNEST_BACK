package com.zayaanit.module.workspaces.invitations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * 
 * @since Jan 19, 2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvitationReqDto {

	private String email;

	public Invitation getBean() {
		return Invitation.builder().email(email).build();
	}
}
