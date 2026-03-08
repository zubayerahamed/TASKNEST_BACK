package com.zayaanit.module.habits;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HabitRepo extends JpaRepository<Habit, Long>{

	List<Habit> findAllByUserId(Long userId);
}
