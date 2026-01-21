package com.zayaanit.module.workspaces.invitations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.SuccessResponse;
import com.zayaanit.module.RestApiController;

import jakarta.validation.Valid;

/**
 * Zubayer Ahamed
 * 
 * @since Jul 5, 2025
 */
@RestApiController
@RequestMapping("/api/v1/invitations")
public class InvitationController {

	@Autowired
	private InvitationService invitationService;

	@GetMapping
	public ResponseEntity<SuccessResponse<List<InvitationResDto>>> getAll() {
		List<InvitationResDto> resData = invitationService.getAll();
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	}

	@PostMapping
	public ResponseEntity<SuccessResponse<InvitationResDto>> create(@Valid @RequestBody CreateInvitationReqDto reqDto) {
		InvitationResDto resData = invitationService.create(reqDto);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
	}

	@PutMapping
	public ResponseEntity<SuccessResponse<InvitationResDto>> update(@Valid @RequestBody UpdateInvitationReqDto reqDto) {
		InvitationResDto resData = invitationService.update(reqDto);
		return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, resData);
	}

	@DeleteMapping("/{email}")
	public ResponseEntity<SuccessResponse<InvitationResDto>> delete(@PathVariable String email) {
		invitationService.deleteById(email);
		return ResponseBuilder.build(ResponseStatusType.DELETE_SUCCESS, null);
	}

	@GetMapping("/{token}/accept")
	public ResponseEntity<SuccessResponse<InvitationResDto>> accept(@PathVariable String token) {
		invitationService.acceptInvitation(token);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, null);
	}
}
