package com.swiftqueue.repository.schedule;

import com.swiftqueue.model.schedule.TimeSlot;
import com.swiftqueue.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findAllByUserInfo(UserInfo userInfo);
}
