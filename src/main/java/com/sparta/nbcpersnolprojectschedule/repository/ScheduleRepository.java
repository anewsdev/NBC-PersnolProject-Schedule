package com.sparta.nbcpersnolprojectschedule.repository;

import com.sparta.nbcpersnolprojectschedule.dto.ScheduleRequestDto;
import com.sparta.nbcpersnolprojectschedule.dto.ScheduleResponseDto;
import com.sparta.nbcpersnolprojectschedule.entity.Schedule;
import io.micrometer.observation.ObservationFilter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {

        //DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();// 기본 키를 반환받기 위한 객체

        //쿼리 작성
        String sql = "INSERT INTO schedule (content, author, password, create_date, update_date ) " +
                "VALUES (?, ?, ?, now(), now())";

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, schedule.getContent());
                    ps.setString(2, schedule.getAuthor());
                    ps.setString(3, schedule.getPassword());

                    return ps;
                },
                keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedule.setScheduleId(id);
        return schedule;
    }

    public List<ScheduleResponseDto> findAll() {
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {

            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("schedule_id");
                String content = rs.getString("content");
                String author = rs.getString("author");
                String password = rs.getString("password");
                String createDate = rs.getString("create_date");
                String updateDate = rs.getString("update_date");
                return new ScheduleResponseDto(id, content, author, password, createDate, updateDate);
            }
        });
    }

    public void update(Schedule updatedSchedule) {
        String sql = "UPDATE schedule SET content = ?, author = ?, update_date = now() WHERE schedule_id = ?";
        jdbcTemplate.update(sql, updatedSchedule.getContent(), updatedSchedule.getAuthor(), updatedSchedule.getScheduleId());
    }

    public void delete(Schedule deleteschedule) {
        String sql = "DELETE FROM schedule WHERE schedule_id = ?";
        jdbcTemplate.update(sql, deleteschedule.getScheduleId());
    }

    public Optional<Schedule> findScheduleById(Long scheduleId) {
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";
        try {
            Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{scheduleId}, (rs, rowNum) ->
                    new Schedule(
                            rs.getLong("schedule_id"),
                            rs.getString("content"),
                            rs.getString("author"),
                            rs.getString("password"),
                            rs.getString("create_date"),
                            rs.getString("update_date")
                    )
            );
            return Optional.ofNullable(schedule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
