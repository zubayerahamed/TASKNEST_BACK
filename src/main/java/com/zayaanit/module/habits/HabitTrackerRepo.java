package com.zayaanit.module.habits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitTrackerRepo extends JpaRepository<HabitTracker, Long> {

}
