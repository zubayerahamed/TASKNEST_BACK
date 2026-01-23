package com.zayaanit.module.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.SuccessResponse;
import com.zayaanit.module.RestApiController;

/**
 * Zubayer Ahamed
 * 
 * @since Jan 23, 2026
 */
@RestApiController
@RequestMapping("/api/v1/members")
public class MemberController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<SuccessResponse<List<MemberResDto>>> getAll() {
		List<MemberResDto> resData = userService.getAllWorkspaceMembers();
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

}
