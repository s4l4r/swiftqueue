package com.swiftqueue.service.schedule;

import com.swiftqueue.dto.schedule.ScheduleDTO;
import com.swiftqueue.dto.schedule.TimeSlotDTO;
import com.swiftqueue.dto.user.ClientDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.schedule.Schedule;
import com.swiftqueue.model.user.Client;
import com.swiftqueue.repository.schedule.ScheduleRepository;
import com.swiftqueue.service.user.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class DefaultScheduleService implements ScheduleService {

    private final ModelMapper modelMapper;
    private final ClientService clientService;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public DefaultScheduleService(ModelMapper modelMapper, ClientService clientService,
                                  ScheduleRepository scheduleRepository) {
        this.modelMapper = modelMapper;
        this.clientService = clientService;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDTO getById(Long id) throws ResourceNotFoundException {
        Schedule schedule = scheduleRepository.findOne(id);
        if (schedule == null)
            throw new ResourceNotFoundException(TYPE, id);
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
        scheduleDTO.setTimeSlots(scheduleDTO.getTimeSlots().stream().sorted(Comparator.comparing(TimeSlotDTO::getDate)
                .thenComparing(TimeSlotDTO::getTime)).collect(Collectors.toCollection(TreeSet::new)));
        return scheduleDTO;
    }

    @Override
    @Transactional
    public ScheduleDTO save(ScheduleDTO dto) throws BusinessException {
        ClientDTO clientDTO;
        try {
            clientDTO = clientService.getById(dto.getClient().getId());
        } catch (ResourceNotFoundException ex) {
            throw new BusinessException(TYPE, "save", "No user found with ID: " + dto.getClient().getId());
        }
        Schedule schedule = modelMapper.map(dto, Schedule.class);
        schedule.setClient(modelMapper.map(clientDTO, Client.class));
        return modelMapper.map(scheduleRepository.save(schedule), ScheduleDTO.class);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public String getByTimeSlotId(Long timeSlotId) throws ResourceNotFoundException {
        String scheduleName = scheduleRepository.xFindNameByTimeSlotsId(timeSlotId);
        if (scheduleName == null)
            throw new ResourceNotFoundException(TYPE, "Timeslot with ID: " + timeSlotId);
        return scheduleName;
    }
}
