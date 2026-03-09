package com.zayaanit.module.habits.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.enums.ResponseStatusType;
import com.zayaanit.model.ResponseBuilder;
import com.zayaanit.model.SuccessResponse;
import com.zayaanit.module.RestApiController;

import jakarta.validation.Valid;

@RestApiController
@RequestMapping("/api/v1/habit-trackers")
public class HabitTrackerController {

	@Autowired private HabitTrackerService habitTrackerService;

	@PostMapping
	public ResponseEntity<SuccessResponse<HabitTrackerResDto>> create(@Valid @RequestBody HabitTrackerReqDto reqDto){
		HabitTrackerResDto resData = habitTrackerService.markComplete(reqDto);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
	}

	@PutMapping
	public ResponseEntity<SuccessResponse<HabitTrackerResDto>> update(@Valid @RequestBody HabitTrackerReqDto reqDto){
		habitTrackerService.markInComplete(reqDto);
		return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, null);
	}
}
