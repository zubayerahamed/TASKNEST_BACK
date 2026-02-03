package com.zayaanit.module.users;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * @since Jul 2, 2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersResDto {

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

	public UsersResDto(User user) {
		BeanUtils.copyProperties(user, this);
	}
}
