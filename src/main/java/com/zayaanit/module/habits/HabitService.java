package com.zayaanit.module.habits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zayaanit.module.BaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HabitService extends BaseService {

	@Autowired private HabitRepo habitRepo;

	
}
