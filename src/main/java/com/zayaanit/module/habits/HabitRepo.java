package com.zayaanit.module.habits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HabitRepo extends JpaRepository<Habit, Long>{

}
