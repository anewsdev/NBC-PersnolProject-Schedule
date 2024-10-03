package com.sparta.nbcpersnolprojectschedule.service;

import com.sparta.nbcpersnolprojectschedule.dto.ScheduleRequestDto;
import com.sparta.nbcpersnolprojectschedule.dto.ScheduleResponseDto;
import com.sparta.nbcpersnolprojectschedule.entity.Schedule;
import com.sparta.nbcpersnolprojectschedule.repository.ScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        //RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        //DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getSchdule() {
        return scheduleRepository.findAll();
    }


    public ScheduleResponseDto getScheduleById(Long scheduleId) {
        return scheduleRepository.findScheduleById(scheduleId)
                .map(this::convertToDto)
                .orElseThrow(() -> new NoSuchElementException("Schedule not found with id: " + scheduleId));
    }

    private ScheduleResponseDto convertToDto(Schedule schedule) {
        // Schedule 엔티티를 ScheduleResponseDto로 변환하는 로직
        return new ScheduleResponseDto(
                schedule.getScheduleId(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getPassword(),
                schedule.getCreateDate(),
                schedule.getUpdateDate()
        );
    }

    public ScheduleResponseDto updateSchedule(Long scheduleId, String password, ScheduleRequestDto requestDto) {
        Schedule updatedSchedule = scheduleRepository.findScheduleById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + scheduleId));

        if (!updatedSchedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }

        updatedSchedule.updateFromDto(requestDto);
        scheduleRepository.update(updatedSchedule);

        return new ScheduleResponseDto(updatedSchedule);
    }


    public ScheduleResponseDto deleteSchedule(Long scheduleId, String password) {
        Schedule deleteschedule = scheduleRepository.findScheduleById(scheduleId)
                .orElseThrow(() -> new NoSuchElementException("Schedule not found with id: " + scheduleId));

        if (!deleteschedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }

        deleteschedule.deleteFromDto(scheduleId);
        scheduleRepository.delete(deleteschedule);
        return new ScheduleResponseDto(deleteschedule);
    }
}
