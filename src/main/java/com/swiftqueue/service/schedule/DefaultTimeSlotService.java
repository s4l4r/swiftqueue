package com.swiftqueue.service.schedule;

import com.swiftqueue.dto.BaseDTO;
import com.swiftqueue.dto.schedule.ScheduleDTO;
import com.swiftqueue.dto.schedule.TimeSlotDTO;
import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.schedule.TimeSlot;
import com.swiftqueue.model.user.UserInfo;
import com.swiftqueue.repository.schedule.TimeSlotRepository;
import com.swiftqueue.service.user.UserInfoService;
import com.swiftqueue.util.SwiftQueueDateTimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultTimeSlotService implements TimeSlotService {

    private final ModelMapper modelMapper;
    private final UserInfoService userInfoService;
    private final ScheduleService scheduleService;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public DefaultTimeSlotService(ModelMapper modelMapper, UserInfoService userInfoService, ScheduleService scheduleService,
                                  TimeSlotRepository timeSlotRepository) {
        this.modelMapper = modelMapper;
        this.userInfoService = userInfoService;
        this.scheduleService = scheduleService;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Transactional(readOnly = true)
    public TimeSlotDTO getById(Long id) throws ResourceNotFoundException {
        TimeSlot timeSlot = timeSlotRepository.findOne(id);
        if (timeSlot == null)
            throw new ResourceNotFoundException("Timeslot", id);
        return modelMapper.map(timeSlot, TimeSlotDTO.class);
    }

    @Transactional
    public TimeSlotDTO save(TimeSlotDTO dto) throws BusinessException {
        dto.setSchedule(getComponent(dto, ScheduleDTO.class));
        dto.setUserInfo(getComponent(dto, UserInfoDTO.class));
        dto.setReserved(true);
        TimeSlot timeSlot = modelMapper.map(dto, TimeSlot.class);
        timeSlot = timeSlotRepository.save(timeSlot);
        return modelMapper.map(timeSlot, TimeSlotDTO.class);
    }

    @Override
    public TimeSlotDTO generateNextTimeSlot(Long scheduleId) throws BusinessException {
        ScheduleDTO schedule;
        try {
            schedule = scheduleService.getById(scheduleId);
        } catch (ResourceNotFoundException ex) {
            throw new BusinessException(TYPE, "save", "No schedule found with ID: " + scheduleId);
        }
        schedule.setTimeSlots(schedule.getTimeSlots().stream().sorted(Comparator.comparing(TimeSlotDTO::getDate)
                .thenComparing(TimeSlotDTO::getTime)).collect(Collectors.toCollection(TreeSet::new)));
        return generateTimeSlots(schedule).stream()
                .filter(timeSlotDTO -> !schedule.getTimeSlots().contains(timeSlotDTO))
                .min(Comparator.comparing(TimeSlotDTO::getDate).thenComparing(TimeSlotDTO::getTime))
                .orElseThrow(() -> new BusinessException(TYPE, "generate timeslot", "No more timeslots available!"));
    }

    @Override
    public void delete(Long timeSlotId) throws BusinessException {
        if (!timeSlotRepository.exists(timeSlotId))
            throw new BusinessException(TYPE, "delete", "No timeslot found with ID: " + timeSlotId);
        timeSlotRepository.delete(timeSlotId);
    }

    @Override
    public List<TimeSlotDTO> getAllTimeSlotsForUser(Long userId) throws BusinessException {
        try {
            UserInfoDTO userInfo = userInfoService.getById(userId);
            return timeSlotRepository.findAllByUserInfo(modelMapper.map(userInfo, UserInfo.class))
                    .stream().map(result -> modelMapper.map(result, TimeSlotDTO.class))
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            throw new BusinessException(TYPE, "get all for user", "No user found with ID: " + userId);
        }
    }

    private Set<TimeSlotDTO> generateTimeSlots(ScheduleDTO schedule) {
        Set<TimeSlotDTO> timeSlots = new HashSet<>();
        LocalDate startDate = SwiftQueueDateTimeUtils.fromPersianDateAsString(schedule.getFromDate());
        LocalDate endDate = SwiftQueueDateTimeUtils.fromPersianDateAsString(schedule.getToDate());
        LocalDate localDate = startDate;
        while (localDate.isBefore(endDate) || localDate.isEqual(endDate)) {
            LocalTime startTime = LocalTime.parse(schedule.getFromTime());
            LocalTime endTime = LocalTime.parse(schedule.getToTime());
            while (startTime.isBefore(endTime)) {
                String date = SwiftQueueDateTimeUtils.fromGregorianDateAsLocalDateTime(localDate);
                String time = String.format("%02d:%02d", startTime.getHour(), startTime.getMinute());
                timeSlots.add(TimeSlotDTO.builder().date(date).time(time).build());
                startTime = startTime.plusMinutes(schedule.getIntervalTime());
            }
            localDate = localDate.plusDays(1);
        }
        return timeSlots;
    }

    private <T extends BaseDTO> T getComponent(TimeSlotDTO timeSlotDTO, Class<T> type) throws BusinessException {
        try {
            return type.cast(type.isAssignableFrom(UserInfoDTO.class)
            ? userInfoService.getById(timeSlotDTO.getUserInfo().getId())
            : scheduleService.getById(timeSlotDTO.getSchedule().getId()));
        } catch (ResourceNotFoundException ex) {
            throw new BusinessException(TYPE, "save", "No " + ex.getType() + " found with ID: " + ex.getId());
        }
    }
}
