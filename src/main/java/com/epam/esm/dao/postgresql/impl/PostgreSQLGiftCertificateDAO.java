package com.epam.esm.dao.postgresql.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exeptions.BadRequestException;
import com.epam.esm.util.ISO8601TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgreSQLGiftCertificateDAO implements GiftCertificateDAO {

    private EntityManager entityManager;

    private static final int LIMIT_FOR_PAGINATION = 10;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int createGiftCertificate(GiftCertificate giftCertificate) {
        Session currentSession = entityManager.unwrap(Session.class);
        String date = ISO8601TimeFormatter.getFormattedDate(new Date());
        giftCertificate.setCreateDate(date);
        giftCertificate.setLastUpdateDate(date);
        currentSession.save(giftCertificate);
        return giftCertificate.getId();
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from GiftCertificate", GiftCertificate.class).getResultList();
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificatesWithPagination(Integer page) {
        Session currentSession = entityManager.unwrap(Session.class);
        TypedQuery<GiftCertificate> query = currentSession.createQuery("from GiftCertificate", GiftCertificate.class);
        if (Objects.nonNull(page)) {
            if (page <= 0 || page > Math.ceil((double) query.getResultList().size() /10)) {
                throw new BadRequestException("page " + page + " are not available");
            }
            query.setFirstResult(LIMIT_FOR_PAGINATION * (page-1));
            query.setMaxResults(LIMIT_FOR_PAGINATION);
        }
        return query.getResultList();
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        Session currentSession = entityManager.unwrap(Session.class);
        if (!currentSession.contains(giftCertificate)) {
            giftCertificate = (GiftCertificate) currentSession.merge(giftCertificate);
        }
        currentSession.update(giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        GiftCertificate giftCertificate = getGiftCertificateById(id).orElseThrow(() -> new BadRequestException("Gift certificate with id " + id + " doesn't exist"));
        currentSession.delete(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> getGiftCertificateById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        GiftCertificate giftCertificate = currentSession.get(GiftCertificate.class, id);
        if (giftCertificate == null) {
            throw new BadRequestException("Certificate with id " + id + " doesn't exist");
        }
        return Optional.of(giftCertificate);
    }


}
