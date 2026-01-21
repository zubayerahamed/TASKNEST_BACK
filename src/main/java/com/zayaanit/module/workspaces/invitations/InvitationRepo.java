package com.zayaanit.module.workspaces.invitations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Zubayer Ahamed
 * @since Jan 19, 2026
 */
@Repository
public interface InvitationRepo extends JpaRepository<Invitation, Long> {

}
