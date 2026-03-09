package com.zayaanit.module.habits.tracker;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitTrackerRepo extends JpaRepository<HabitTracker, Long> {

	Optional<HabitTracker> findByHabitIdAndCompleteDate(Long havitId, LocalDate completeDate);
}
