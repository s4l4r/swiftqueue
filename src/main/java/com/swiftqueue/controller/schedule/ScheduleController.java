package com.swiftqueue.controller.schedule;

import com.swiftqueue.dto.schedule.ScheduleDTO;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.schedule.ScheduleService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @SneakyThrows
    @GetMapping("/{scheduleId}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long scheduleId) {
        ScheduleDTO scheduleDTO = scheduleService.getById(scheduleId);
        return ResponseEntity.ok(scheduleDTO);
    }

    @SneakyThrows
    @PostMapping
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<ScheduleDTO> saveSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        ScheduleDTO savedScheduleDTO = scheduleService.save(scheduleDTO);
        return ResponseEntity.created(UriComponentsBuilder
                        .fromPath("/api/v1/schedules/")
                        .pathSegment(String.valueOf(savedScheduleDTO.getId()))
                .build().toUri()).build();
    }

    @GetMapping("/timeslot/{timeSlotId}")
    public ResponseEntity<String> getScheduleNameByTimeSlotId(@PathVariable Long timeSlotId) throws ResourceNotFoundException {
        return ResponseEntity.ok(scheduleService.getByTimeSlotId(timeSlotId));
    }
}
