package com.zayaanit.module.workspaces.invitations;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zayaanit.exception.CustomException;
import com.zayaanit.mail.MailReqDto;
import com.zayaanit.mail.MailService;
import com.zayaanit.mail.MailType;
import com.zayaanit.module.BaseService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
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

	@Autowired
	private InvitationRepo invitationRepo;
	@Autowired private MailService mailService;

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
		inv.setStatus(InvitationStatus.INVITATION_SENT);
		inv.setInviationDate(LocalDateTime.now());
		inv.setToken(UUID.randomUUID().toString());

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
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void deleteById(String email) {
		// TODO Auto-generated method stub

	}

}
