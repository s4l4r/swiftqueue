package com.swiftqueue.controller.schedule;

import com.swiftqueue.dto.schedule.TimeSlotDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.schedule.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping("/{timeSlotId}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<TimeSlotDTO> getTimeSlotById(@PathVariable Long timeSlotId) throws ResourceNotFoundException {
        TimeSlotDTO timeSlotDTO = timeSlotService.getById(timeSlotId);
        if (timeSlotDTO == null)
            throw new ResourceNotFoundException("TimeSlot", timeSlotId);
        return ResponseEntity.ok(timeSlotDTO);
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<TimeSlotDTO> saveTimeSlot(@RequestBody @Valid TimeSlotDTO timeSlotDTO) throws BusinessException {
        TimeSlotDTO savedTimeSlotDTO = timeSlotService.save(timeSlotDTO);
        return ResponseEntity.created(UriComponentsBuilder
                        .fromPath("/api/v1/timeslots/")
                        .pathSegment(String.valueOf(savedTimeSlotDTO.getId()))
                .build().toUri()).build();
    }

    @PostMapping("/next/{scheduleId}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<TimeSlotDTO> getNextTimeSlot(@PathVariable Long scheduleId) throws BusinessException {
        return ResponseEntity.ok(timeSlotService.generateNextTimeSlot(scheduleId));
    }

    @DeleteMapping("/{timeSlotId}")
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long timeSlotId) throws BusinessException {
        this.timeSlotService.delete(timeSlotId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all-user/{userId}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<List<TimeSlotDTO>> getAllTimeSlotsForUser(@PathVariable Long userId) throws BusinessException {
        return ResponseEntity.ok(timeSlotService.getAllTimeSlotsForUser(userId));
    }
}
