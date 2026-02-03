package com.zayaanit.module.users;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.SuccessResponse;
import com.zayaanit.module.RestApiController;

/**
 * Zubayer Ahamed
 * 
 * @since Jun 26, 2025
 */
@RestApiController
@RequestMapping("/api/v1/users")
public class UsersController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<SuccessResponse<UsersResDto>> find(@PathVariable Long id){
		UsersResDto resData = userService.findById(id);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
	} 

	@GetMapping("/thumbnail/{id}")
	public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) {
		UsersResDto user = userService.findById(id);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		byte[] thumbnail = user.getThumbnail();

		if (thumbnail == null || thumbnail.length == 0) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG) // or PNG
				.cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)).body(thumbnail);
	}

	@PutMapping(value = "/thumbnail/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<SuccessResponse<UsersResDto>> uploadThumbnail(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
		UsersResDto resData = userService.saveThumbnail(id, file);
		return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, resData);
	}

}
