package com.sparta.nbcpersnolprojectschedule.dto;

import com.sparta.nbcpersnolprojectschedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private long scheduleId; //일정 고유번호
    private String content; // 일정 할일
    private String author; // 작성자
    private String password; //게시글 비밀번호
    private String createDate; // 일정 생성 일시
    private String updateDate; // 일정 수정 일시

    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.content = schedule.getContent();
        this.author = schedule.getAuthor();
        this.password = schedule.getPassword();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
    public ScheduleResponseDto(long scheduleId, String content, String author, String password, String createDate, String updateDate) {
        this.scheduleId = scheduleId;
        this.content = content;
        this.author = author;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
