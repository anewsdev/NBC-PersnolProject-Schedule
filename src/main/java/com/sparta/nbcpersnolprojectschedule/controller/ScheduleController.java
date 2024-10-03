package com.sparta.nbcpersnolprojectschedule.controller;

import com.sparta.nbcpersnolprojectschedule.dto.ScheduleRequestDto;
import com.sparta.nbcpersnolprojectschedule.dto.ScheduleResponseDto;
import com.sparta.nbcpersnolprojectschedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController //왜 RestController 써야하지?
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    //일정 생성
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }
    //일정 전체 조회
    @GetMapping
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getSchdule();
    }
    //ID로 일정 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long scheduleId) {
        try {
            ScheduleResponseDto schedule = scheduleService.getScheduleById(scheduleId);
            return ResponseEntity.ok(schedule);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //일정 수정(id 값으로)
    @PutMapping("/{scheduleId}/{password}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long scheduleId, @PathVariable String password, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(scheduleId, password, requestDto);
    }
    //일정 삭제(id 값으로)
    @DeleteMapping("/{scheduleId}/{password}")
    public ScheduleResponseDto deleteSchedule(@PathVariable Long scheduleId, @PathVariable String password) {
        return scheduleService.deleteSchedule(scheduleId, password);
    }
}
