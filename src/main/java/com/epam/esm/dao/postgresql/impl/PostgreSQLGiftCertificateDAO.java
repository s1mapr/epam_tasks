package com.epam.esm.dao.postgresql.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ISO8601TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgreSQLGiftCertificateDAO implements GiftCertificateDAO {

    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    private final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificate";
    private final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id=?";
    private final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?";
    private final String SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
    private final String SELECT_GIFT_CERTIFICATE_BY_TAG_ID = "SELECT gc.*\n" +
            "FROM gift_certificate gc\n" +
            "JOIN certificate_tag ct ON gc.id = ct.certificate_id\n" +
            "WHERE ct.tag_id = ?";

    @Override
    public int createGiftCertificate(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String date = ISO8601TimeFormatter.getFormattedDate(new Date());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_GIFT_CERTIFICATE, new String[]{"id"});
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setDouble(3, giftCertificate.getPrice());
            ps.setInt(4, giftCertificate.getDuration());
            ps.setString(5, date);
            ps.setString(6, date);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(), ISO8601TimeFormatter.getFormattedDate(new Date()), giftCertificate.getId());
    }

    @Override
    public void deleteGiftCertificate(int id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);
    }

    @Override
    public Optional<GiftCertificate> getGiftCertificateById(int id) {
        return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_ID, new BeanPropertyRowMapper<>(GiftCertificate.class), id).stream().findAny();
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByTagId(int id) {
        return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_TAG_ID, new BeanPropertyRowMapper<>(GiftCertificate.class), id);
    }

}
