package com.zayaanit.module.users;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.zayaanit.module.users.workspaces.UserWorkspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * @since Jan 23, 2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResDto {

	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private Boolean isActive;
	private String country;
	private String phone;
	private String location;
	private Date dateOfBirth;
	private byte[] thumbnail;

	private UserWorkspace userWorkspace;

	public MemberResDto(User user, UserWorkspace uw) {
		BeanUtils.copyProperties(user, this);
		this.userWorkspace = uw;
	}
}
