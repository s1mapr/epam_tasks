package com.epam.esm.dao.mysql.impl;

import com.epam.esm.dao.TagGiftDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGift;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MySQLTagGiftDAO implements TagGiftDAO {

    private final JdbcTemplate jdbcTemplate;

    private final String ADD_ENTRY = "INSERT INTO certificate_tag VALUES (?,?)";

    private final String DELETE_ENTRY_BY_TAG_ID = "DELETE FROM certificate_tag WHERE tag_id = ?";
    private final String DELETE_ENTRY_BY_CERTIFICATE_ID = "DELETE FROM certificate_tag WHERE certificate_id = ?";
    private final String GET_ENTRY_BY_TAG_ID_AND_CERTIFICATE_ID = "SELECT * FROM certificate_tag WHERE certificate_id = ? AND tag_id = ?";

    @Override
    public void addEntry(int tagId, int certificateId) {
        if(getEntry(certificateId, tagId).isEmpty()) {
            jdbcTemplate.update(ADD_ENTRY, tagId, certificateId);
        }
    }

    @Override
    public void deleteEntryByTagId(int tagId){
        jdbcTemplate.update(DELETE_ENTRY_BY_TAG_ID, tagId);
    }

    @Override
    public void deleteEntryByCertificateId(int certificateId){
        jdbcTemplate.update(DELETE_ENTRY_BY_CERTIFICATE_ID, certificateId);
    }

    private Optional<TagGift> getEntry(int certificateId, int tagId){
        return jdbcTemplate.query(GET_ENTRY_BY_TAG_ID_AND_CERTIFICATE_ID,new BeanPropertyRowMapper<>(TagGift.class), certificateId, tagId).stream().findAny();
    }
}
