package com.sparta.nbcpersnolprojectschedule.entity;

import com.sparta.nbcpersnolprojectschedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    private long scheduleId; //일정 고유번호
    private String content; // 일정 할일
    private String author; // 작성자
    private String password; //게시글 비밀번호
    private String createDate; // 일정 생성 일시
    private String updateDate; // 일정 수정 일시

    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.content = scheduleRequestDto.getContent();
        this.author = scheduleRequestDto.getAuthor();
        this.password = scheduleRequestDto.getPassword();
        this.createDate = scheduleRequestDto.getCreateDate();
        this.updateDate = scheduleRequestDto.getUpdateDate();
    }

    public Schedule(long id, String content, String author, String password, String createDate, String updateDate) {

        this.scheduleId = id;
        this.content = content;
        this.author = author;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;

    }

    public void updateFromDto(ScheduleRequestDto requestDto) {

        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
        this.updateDate = requestDto.getUpdateDate();

    }

    public void deleteFromDto(Long scheduleId) {

        this.scheduleId = scheduleId;
    }
}
