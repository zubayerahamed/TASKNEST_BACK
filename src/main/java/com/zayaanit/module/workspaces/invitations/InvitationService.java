package com.zayaanit.module.workspaces.invitations;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zayaanit.exception.CustomException;
import com.zayaanit.mail.MailReqDto;
import com.zayaanit.mail.MailService;
import com.zayaanit.mail.MailType;
import com.zayaanit.module.BaseService;
import com.zayaanit.module.users.workspaces.UserWorkspace;
import com.zayaanit.module.users.workspaces.UserWorkspaceRepo;
import com.zayaanit.module.workspaces.Workspace;
import com.zayaanit.module.workspaces.WorkspaceRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Zubayer Ahamed
 * 
 * @since Jan 19, 2026
 */
@Slf4j
@Service
public class InvitationService extends BaseService {

	@Autowired private InvitationRepo invitationRepo;
	@Autowired private MailService mailService;
	@Autowired private WorkspaceRepo workspaceRepo;
	@Autowired private UserWorkspaceRepo userWorkspaceRepo;

	public List<InvitationResDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public InvitationResDto create(@Valid CreateInvitationReqDto reqDto) {

		if(StringUtils.isBlank(reqDto.getEmail())) {
			throw new CustomException("Email address required", HttpStatus.BAD_REQUEST);
		}

		Invitation inv = reqDto.getBean();
		inv.setWorkspaceId(loggedinUser().getWorkspace().getId());
		inv.setInviationDate(LocalDateTime.now());
		inv.setToken(UUID.randomUUID().toString());
		inv.setInvitationCount(1);
		inv.setExpiryDate(inv.getInviationDate().plusDays(4));

		// Send Email from here
		try {
			MailReqDto email = new MailReqDto();
			email.setFrom("tasknest@zayaanit.com");
			email.setTo(inv.getEmail());
			email.setSubject("TaskNest - Invitation to join Workspace");
			String link = "http://localhost:4200/accept-invitation/" + inv.getToken();
			email.setMailType(MailType.INVITATION_REQUEST);
			Map<String, Object> contextData = new HashMap<>();
			contextData.put("workspaceName", loggedinUser().getWorkspace().getName());
			contextData.put("invitationLink", link);
			email.setContextData(contextData);
			mailService.sendMail(email);
		} catch (Exception e) {
			log.error("Error is : {}, {}", e.getMessage(), e);
			throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		inv = invitationRepo.save(inv);

		return new InvitationResDto(inv);
	}

	@Transactional
	public InvitationResDto update(@Valid UpdateInvitationReqDto reqDto) {
		if(StringUtils.isBlank(reqDto.getEmail())) {
			throw new CustomException("Email address required", HttpStatus.BAD_REQUEST);
		}

		Optional<Invitation> invOp = invitationRepo.findByEmailAndWorkspaceId(reqDto.getEmail(), loggedinUser().getWorkspace().getId());
		if(!invOp.isPresent()) throw new CustomException("Inviation not exist or already accepted or deleted", HttpStatus.NOT_FOUND); 

		Invitation inv = invOp.get();
		inv.setToken(UUID.randomUUID().toString());
		inv.setInviationDate(LocalDateTime.now());
		inv.setExpiryDate(inv.getInviationDate().plusDays(4));
		inv.setInvitationCount(inv.getInvitationCount() + 1);

		// Send Email from here
		try {
			MailReqDto email = new MailReqDto();
			email.setFrom("tasknest@zayaanit.com");
			email.setTo(inv.getEmail());
			email.setSubject("TaskNest - Invitation to join Workspace");
			String link = "http://localhost:4200/accept-invitation/" + inv.getToken();
			email.setMailType(MailType.INVITATION_REQUEST);
			Map<String, Object> contextData = new HashMap<>();
			contextData.put("workspaceName", loggedinUser().getWorkspace().getName());
			contextData.put("invitationLink", link);
			email.setContextData(contextData);
			mailService.sendMail(email);
		} catch (Exception e) {
			log.error("Error is : {}, {}", e.getMessage(), e);
			throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		inv = invitationRepo.save(inv);

		return new InvitationResDto(inv);
	}

	@Transactional
	public void deleteById(String email) {
		if(StringUtils.isBlank(email)) {
			throw new CustomException("Email address required", HttpStatus.BAD_REQUEST);
		}

		Optional<Invitation> invOp = invitationRepo.findByEmailAndWorkspaceId(email, loggedinUser().getWorkspace().getId());
		if(!invOp.isPresent()) throw new CustomException("Inviation not exist or already accepted or deleted", HttpStatus.NOT_FOUND);

		invitationRepo.delete(invOp.get());
	}

	@Transactional
	public void acceptInvitation(String token) {
		if(StringUtils.isBlank(token)) {
			throw new CustomException("Invalid token", HttpStatus.BAD_REQUEST);
		}

		Optional<Invitation> invOp = invitationRepo.findByToken(token);
		if(!invOp.isPresent()) throw new CustomException("Inviation not exist or already accepted or deleted", HttpStatus.NOT_FOUND);

		Invitation inv = invOp.get();

		if(!inv.getEmail().equals(loggedinUser().getEmail())) {
			throw new CustomException("Invalid invitation link", HttpStatus.BAD_REQUEST);
		}

		if(inv.getExpiryDate().isAfter(LocalDateTime.now())) {
			throw new CustomException("Invitation link expired", HttpStatus.BAD_REQUEST);
		}

		// Create a workspace allocation for this user
		Optional<Workspace> workspaceOp = workspaceRepo.findById(inv.getWorkspaceId());
		if(!workspaceOp.isPresent()) throw new CustomException("Workspace not found or deleted", null);

		Workspace workspace = workspaceOp.get();

		// Create user-business relationship
		UserWorkspace userWorkspace = UserWorkspace.builder()
				.userId(loggedinUser().getUserId())
				.workspaceId(workspace.getId())
				.isPrimary(Boolean.FALSE)
				.isAdmin(Boolean.FALSE)
				.isCollaborator(Boolean.TRUE).build();

		userWorkspace = userWorkspaceRepo.save(userWorkspace);

		// Welcome message through email
		// Send Email from here
		try {
			MailReqDto email = new MailReqDto();
			email.setFrom("tasknest@zayaanit.com");
			email.setTo(inv.getEmail());
			email.setSubject("TaskNest - Welcome to Workspace : " + workspace.getName());
			email.setMailType(MailType.INVITATION_WELCOME);
			Map<String, Object> contextData = new HashMap<>();
			contextData.put("workspaceName", loggedinUser().getWorkspace().getName());
			contextData.put("username", loggedinUser().getUsername());
			email.setContextData(contextData);
			mailService.sendMail(email);
		} catch (Exception e) {
			log.error("Error is : {}, {}", e.getMessage(), e);
			throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// delete the invitation
		invitationRepo.delete(inv);
	}


}
