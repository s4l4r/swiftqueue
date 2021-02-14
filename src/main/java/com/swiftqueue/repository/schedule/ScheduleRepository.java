package com.swiftqueue.repository.schedule;

import com.swiftqueue.model.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s.name FROM Schedule s INNER JOIN s.timeSlots t WHERE t.id = :timeSlotId ")
    String xFindNameByTimeSlotsId(@Param("timeSlotId") Long timeSlotId);
}
