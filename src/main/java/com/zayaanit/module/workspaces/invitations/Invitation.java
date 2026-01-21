package com.zayaanit.module.workspaces.invitations;

import java.time.LocalDateTime;

import com.zayaanit.model.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Zubayer Ahamed
 * 
 * @since Jun 19, 2025
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invitation")
@EqualsAndHashCode(callSuper = true)
public class Invitation extends AbstractModel<Long> {

	private static final long serialVersionUID = 2932605023333073712L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "workspace_id")
	private Long workspaceId;

	@Column(name = "invitation_date")
	private LocalDateTime inviationDate;

	@Column(name = "token", length = 100)
	private String token;

	@Column(name = "invitation_count")
	private Integer invitationCount;

	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;
}
