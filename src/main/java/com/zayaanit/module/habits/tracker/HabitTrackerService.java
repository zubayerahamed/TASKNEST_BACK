package com.zayaanit.module.habits.tracker;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zayaanit.exception.CustomException;
import com.zayaanit.module.BaseService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HabitTrackerService extends BaseService {

	@Autowired private HabitTrackerRepo habitTrackerRepo;

	@Transactional
	public HabitTrackerResDto markComplete(HabitTrackerReqDto reqDto) throws CustomException {

		Optional<HabitTracker> htOp = habitTrackerRepo.findByHabitIdAndCompleteDate(reqDto.getHabitId(), LocalDate.now());

		if(htOp.isPresent()) {
			throw new CustomException("Habit already marked as completed today", HttpStatus.BAD_REQUEST);
		}

		HabitTracker ht = reqDto.getBean();
		ht.setCompleteDate(LocalDate.now());
		ht = habitTrackerRepo.save(ht);
		return new HabitTrackerResDto(ht);
	}

	@Transactional
	public void markInComplete(HabitTrackerReqDto reqDto) throws CustomException {

		Optional<HabitTracker> htOp = habitTrackerRepo.findByHabitIdAndCompleteDate(reqDto.getHabitId(), LocalDate.now());

		if(!htOp.isPresent()) {
			throw new CustomException("Habit not completed yet.", HttpStatus.BAD_REQUEST);
		}

		habitTrackerRepo.delete(htOp.get());
	}
}
