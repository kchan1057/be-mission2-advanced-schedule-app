package com.example.bemission2advancedscheduleapp.lv3_6.repository;

import com.example.bemission2advancedscheduleapp.lv3_6.dto.ScheduleResponseDto;
import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule save(Schedule schedule) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("todo", schedule.getTodo());
        params.put("password", schedule.getPassword());
        params.put("writer_id", schedule.getWriterId());
        params.put("created_at", Timestamp.valueOf(schedule.getCreatedAt()));
        params.put("modified_at", Timestamp.valueOf(schedule.getModifiedAt()));

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        schedule.setId(key.longValue());
        return schedule;
    }

    private RowMapper<ScheduleResponseDto> responseMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }
    @Override
    public List<ScheduleResponseDto> findAll(int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
                SELECT s.id, s.todo, s.created_at, s.modified_at, 
                       w.name, w.email 
                FROM schedule s 
                JOIN writer w ON s.writer_id = w.id
                ORDER BY s.created_at DESC 
                LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(sql, responseMapper(), size, offset);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleMapper(), id);
        return result.stream().findFirst();
    }

    private RowMapper<Schedule> scheduleMapper() {
        return (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTodo(rs.getString("todo"));
            schedule.setPassword(rs.getString("password"));
            schedule.setWriterId(rs.getLong("writer_id"));
            schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return schedule;
        };
    }
    @Override
    public void delete(Long id, String password) {
        Schedule schedule = findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID를 찾을 수 없습니다."));
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    @Override
    public void updateTodo(Long id, String todo, String password) {
        Schedule schedule = findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."));
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        jdbcTemplate.update("UPDATE schedule SET todo = ?, modified_at = NOW() WHERE id = ?", todo, id);
    }

    @Override
    public void updateWriter(Long id, Long writerId, String password) {
        Schedule schedule = findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."));
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        jdbcTemplate.update("UPDATE schedule SET writer_id = ?, modified_at = NOW() WHERE id = ?", writerId, id);
    }
}

