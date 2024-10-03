package com.sparta.nbcpersnolprojectschedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private String content; // 일정 할일
    private String author; // 작성자
    private String password; //게시글 비밀번호
    private String createDate; // 일정 생성 일시
    private String updateDate; // 일정 수정 일시

}
