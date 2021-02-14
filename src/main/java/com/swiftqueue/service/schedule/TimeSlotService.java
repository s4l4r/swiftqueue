package com.swiftqueue.service.schedule;

import com.swiftqueue.dto.schedule.TimeSlotDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;

import java.util.List;

public interface TimeSlotService {

    String TYPE = "TimeSlot";

    TimeSlotDTO getById(Long timeSlotId) throws ResourceNotFoundException;

    TimeSlotDTO save(TimeSlotDTO timeSlotDTO) throws BusinessException;

    TimeSlotDTO generateNextTimeSlot(Long scheduleId) throws BusinessException;

    void delete(Long timeSlotId) throws BusinessException;

    List<TimeSlotDTO> getAllTimeSlotsForUser(Long userId) throws BusinessException;
}
