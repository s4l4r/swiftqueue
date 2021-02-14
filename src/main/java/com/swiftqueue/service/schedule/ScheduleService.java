package com.swiftqueue.service.schedule;

import com.swiftqueue.dto.schedule.ScheduleDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;

public interface ScheduleService {

    String TYPE = "Schedule";

    ScheduleDTO save(ScheduleDTO scheduleDTO) throws BusinessException;

    ScheduleDTO getById(Long scheduleId) throws ResourceNotFoundException;

    String getByTimeSlotId(Long timeSlotId) throws ResourceNotFoundException;
}
