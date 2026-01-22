package com.zayaanit.module.workspaces.invitations;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Zubayer Ahamed
 * @since Jan 19, 2026
 */
@Repository
public interface InvitationRepo extends JpaRepository<Invitation, Long> {

	Optional<Invitation> findByEmailAndWorkspaceId(String email, Long workspaceId);
	Optional<Invitation> findByToken(String token);
	List<Invitation> findAllByWorkspaceId(Long workspaceId);
}
