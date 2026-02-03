package com.zayaanit.module.users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zayaanit.exception.CustomException;
import com.zayaanit.model.MyUserDetail;
import com.zayaanit.module.BaseService;
import com.zayaanit.module.users.workspaces.UserWorkspace;
import com.zayaanit.module.users.workspaces.UserWorkspaceRepo;
import com.zayaanit.module.workspaces.Workspace;
import com.zayaanit.module.workspaces.WorkspaceRepo;

/**
 * Zubayer Ahamed
 * @since Jun 26, 2025
 */
@Service
public class UserService extends BaseService implements UserDetailsService  {

	@Autowired private UserRepo usersRepo;
	@Autowired private UserWorkspaceRepo usersWorkspacesRepo;
	@Autowired private WorkspaceRepo workspacesRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("Email required");
		}

		String[] credentials =  username.split("\\|");

		Optional<User> userOp = usersRepo.findById(Long.valueOf(credentials[0]));
		if(!userOp.isPresent()) throw new UsernameNotFoundException("User not exist.");

		User user = userOp.get();
		if(Boolean.FALSE.equals(user.getIsActive())) {
			throw new UsernameNotFoundException("User inactive.");
		}

		Optional<UserWorkspace> userWorkspaceOp = usersWorkspacesRepo.findByUserIdAndIsPrimary(user.getId(), Boolean.TRUE);
		if(!userWorkspaceOp.isPresent()) {
			throw new UsernameNotFoundException("Primary workspace not found");
		}

		// Find the current workspace assigned with user
		Optional<UserWorkspace> userCurrentWorkspaceOp = usersWorkspacesRepo.findByUserIdAndWorkspaceId(user.getId(), Long.valueOf(credentials[1]));
		if(!userCurrentWorkspaceOp.isPresent()) {
			throw new UsernameNotFoundException("Workspace not found");
		}


		Optional<Workspace> workspaceOp = workspacesRepo.findById(userCurrentWorkspaceOp.get().getWorkspaceId());
		if(!workspaceOp.isPresent()) {
			throw new UsernameNotFoundException("Workspace not found");
		}

		Workspace workspace = workspaceOp.get();
		if(Boolean.FALSE.equals(workspace.getIsActive())) {
			throw new UsernameNotFoundException("Workspace is disabled");
		}

		return new MyUserDetail(user, workspace, userWorkspaceOp.get());
	}

	public List<MemberResDto> getAllWorkspaceMembers() {
		List<MemberResDto> allMembers = new ArrayList<MemberResDto>();

		List<UserWorkspace> uwList = usersWorkspacesRepo.findAllByWorkspaceId(loggedinUser().getWorkspace().getId());
		uwList = uwList.stream().filter(f -> !f.getUserId().equals(loggedinUser().getUserId())).collect(Collectors.toList());

		for(UserWorkspace uw : uwList) {
			Optional<User> userOp = usersRepo.findById(uw.getUserId());
			if(userOp.isPresent()) {
				allMembers.add(new MemberResDto(userOp.get(), uw));
			}
		}

		return allMembers;
	}

	public UsersResDto findById(Long id) {
		Optional<User> userOp = usersRepo.findById(id);
		if(!userOp.isPresent()) return null;
		return new UsersResDto(userOp.get());
	}

	public UsersResDto saveThumbnail(Long id, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new CustomException("File is empty", HttpStatus.NOT_FOUND);
		}

		// Validate content type (CRITICAL)
		String contentType = file.getContentType();
		if (!List.of("image/jpeg", "image/png").contains(contentType)) {
			throw new CustomException("Only JPG/PNG allowed", HttpStatus.BAD_REQUEST);
		}

		// Optional size limit (e.g. 500KB)
		if (file.getSize() > 500 * 1024) {
			throw new CustomException("File too large", HttpStatus.BAD_REQUEST);
		}

		Optional<User> userOp = usersRepo.findById(id);
		if(!userOp.isPresent()) {
			throw new CustomException("User not found", HttpStatus.NOT_FOUND);
		}

		User user = userOp.get();
		user.setThumbnail(file.getBytes());
		user.setThumbnailContentType(contentType); // strongly recommended

		user = usersRepo.save(user);

		return new UsersResDto(user);
	}

}
