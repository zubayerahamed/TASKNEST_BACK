package com.zayaanit.module.habits;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zayaanit.exception.CustomException;
import com.zayaanit.module.BaseService;

import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HabitService extends BaseService {

	@Autowired private HabitRepo habitRepo;

	public List<HabitResDto> getAllHabits(){
		List<Habit> list = habitRepo.findAllByUserId(loggedinUser().getUserId());
		if(list.isEmpty()) return Collections.emptyList();

		List<HabitResDto> responseData = new ArrayList<>();
		list.stream().forEach(l -> {
			responseData.add(new HabitResDto(l));
		});
		return responseData;
	}

	public HabitResDto findById(Long id) throws CustomException {
		Optional<Habit> habitOp = habitRepo.findById(id);
		if(!habitOp.isPresent()) throw new CustomException("Habit not found", HttpStatus.NOT_FOUND);
		return new HabitResDto(habitOp.get());
	}

	@Transactional
	public HabitResDto create(CreateHabitReqDto reqDto) throws CustomException {
		// TODO: category need to identify for habit
		
		if(reqDto.getStartDate().isBefore(LocalDate.now())){
			throw new CustomException("Start date cant be before today", HttpStatus.BAD_REQUEST);
		}
		
		Habit habit = reqDto.getBean();
		habit.setUserId(loggedinUser().getUserId());
		habit.setIsCompleted(false);
		habit = habitRepo.save(habit);
		return new HabitResDto(habit);
	}

	@Transactional
	public HabitResDto update(UpdateHabitReqDto reqDto) throws CustomException {
		Optional<Habit> habitOp = habitRepo.findById(reqDto.getId());
		if(!habitOp.isPresent()) throw new CustomException("Habit not found", HttpStatus.NOT_FOUND);

		Habit exisObj = habitOp.get();
		BeanUtils.copyProperties(reqDto, exisObj, "userId");

		// TODO: validate habit tracker exist before start date, then delete it first

		return new HabitResDto(exisObj);
	}

	@Transactional
	public void deleteById(Long id) throws CustomException {
		Optional<Habit> habitOp = habitRepo.findById(id);
		if(!habitOp.isPresent()) throw new CustomException("Habit not found", HttpStatus.NOT_FOUND);

		habitRepo.delete(habitOp.get());
	}
}
