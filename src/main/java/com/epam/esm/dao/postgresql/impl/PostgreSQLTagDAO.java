package com.epam.esm.dao.postgresql.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgreSQLTagDAO implements TagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String SELECT_ALL_TAGS = "SELECT * FROM tag";
    private final String INSERT_TAG = "INSERT INTO tag(name) VALUES (?)";
    private final String DELETE_TAG = "DELETE FROM tag WHERE id=?";
    private final String selectTagByName = "SELECT * FROM tag WHERE name = ?";
    private final String selectTagsByCertificateId = "SELECT t.*\n" +
            "FROM tag t\n" +
            "JOIN certificate_tag ct ON t.id = ct.tag_id\n" +
            "WHERE ct.certificate_id = ?";
    private final String SELECT_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";


    @Override
    public List<Tag> getAllTags(){
        return jdbcTemplate.query(SELECT_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        return jdbcTemplate.query(selectTagByName, new BeanPropertyRowMapper<>(Tag.class), name).stream().findAny();
    }

    @Override
    public void createTag(String name){
        jdbcTemplate.update(INSERT_TAG, name);
    }

    @Override
    public void deleteTagById(int id){
        jdbcTemplate.update(DELETE_TAG, id);
    }


    @Override
    public List<Tag> getTagsByCertificateId(int id) {
        return jdbcTemplate.query(selectTagsByCertificateId, new BeanPropertyRowMapper<>(Tag.class), id);
    }


    @Override
    public Optional<Tag> getTagById(int id) {
        return jdbcTemplate.query(SELECT_TAG_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id).stream().findAny();
    }

}
