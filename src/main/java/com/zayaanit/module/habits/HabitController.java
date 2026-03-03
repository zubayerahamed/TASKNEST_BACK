package com.zayaanit.module.habits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zayaanit.module.RestApiController;

@RestApiController
@RequestMapping("/api/v1/habits")
public class HabitController {

	@Autowired private HabitService habitService;

	
}
