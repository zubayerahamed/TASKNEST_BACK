package com.zayaanit.module.workspaces.invitations;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

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
public class InvitationResDto {

	private Long id;
	private String email;
	private Long workspaceId;
	private LocalDateTime inviationDate;

	public InvitationResDto(Invitation invitation) {
		BeanUtils.copyProperties(invitation, this);
	}

}
