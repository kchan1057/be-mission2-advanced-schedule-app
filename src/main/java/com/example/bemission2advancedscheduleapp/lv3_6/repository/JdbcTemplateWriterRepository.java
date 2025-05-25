package com.example.bemission2advancedscheduleapp.lv3_6.repository;

import com.example.bemission2advancedscheduleapp.lv3_6.entitiy.Writer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcTemplateWriterRepository implements WriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateWriterRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Writer> findByNameAndEmail(String name, String email) {
        String sql = "SELECT * FROM writer WHERE name = ? AND email = ?";
        List<Writer> results = jdbcTemplate.query(sql, writerRowMapper(), name, email);
        return results.stream().findAny();
    }

    @Override
    public Writer save(Writer writer) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("writer")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", writer.getName());
        params.put("email", writer.getEmail());
        params.put("created_at", Timestamp.valueOf(writer.getCreatedAt()));
        params.put("modified_at", Timestamp.valueOf(writer.getModifiedAt()));

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        writer.setId(key.longValue());
        return writer;
    }

    @Override
    public Optional<Writer> findById(Long id) {
        String sql = "SELECT * FROM writer WHERE id = ?";
        List<Writer> result = jdbcTemplate.query(sql, writerRowMapper(), id);
        return result.stream().findFirst();
    }

    private RowMapper<Writer> writerRowMapper() {
        return (rs, rowNum) -> new Writer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }
}
