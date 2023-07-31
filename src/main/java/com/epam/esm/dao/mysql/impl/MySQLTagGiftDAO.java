package com.epam.esm.dao.mysql.impl;

import com.epam.esm.dao.TagGiftDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGift;
import com.epam.esm.exeptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class MySQLTagGiftDAO implements TagGiftDAO {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addEntry(Tag tag, GiftCertificate giftCertificate) {
        Session currentSession = entityManager.unwrap(Session.class);
        TagGift tagGift = new TagGift(giftCertificate, tag);
        currentSession.saveOrUpdate(tagGift);
    }

    @Override
    public void deleteEntryByTagId(int tagId){
        Session currentSession = entityManager.unwrap(Session.class);
        Query<TagGift> deleteQuery = currentSession.createQuery("DELETE FROM TagGift tg WHERE tg.tag.id = :tagId");
        deleteQuery.setParameter("tagId", tagId);
        deleteQuery.executeUpdate();
    }

    @Override
    public void deleteEntryByCertificateId(int giftCertificateId){
        Session currentSession = entityManager.unwrap(Session.class);
        Query<TagGift> deleteQuery = currentSession.createQuery("DELETE FROM TagGift tg WHERE tg.giftCertificate.id = :giftCertificateId");
        deleteQuery.setParameter("giftCertificateId", giftCertificateId);
        deleteQuery.executeUpdate();
    }

//    private Optional<TagGift> getEntry(int certificateId, int tagId){
//        return jdbcTemplate.query(GET_ENTRY_BY_TAG_ID_AND_CERTIFICATE_ID,new BeanPropertyRowMapper<>(TagGift.class), certificateId, tagId).stream().findAny();
//    }




    @Override
    public List<GiftCertificate> getGiftCertificatesByTagId(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Optional<Tag> tag = Optional.ofNullable(currentSession.load(Tag.class, id));
        List<TagGift> tagGifts = tag.orElseThrow(()->new BadRequestException("There are no certificates that have tag with id " + id)).getGiftCertificateTags();
        return tagGifts.stream()
                .map(TagGift::getGiftCertificate)
                .collect(Collectors.toList());
    }


    @Override
    public List<TagGift> getAllEntries() {
        Session currentSession = entityManager.unwrap(Session.class);
        TypedQuery<TagGift> query = currentSession.createQuery("from TagGift ", TagGift.class);
        return query.getResultList();
    }

    @Override
    public List<TagGift> getTagGiftByCertificateId(GiftCertificate giftCertificate) {
        Session currentSession = entityManager.unwrap(Session.class);
        TypedQuery<TagGift> query = currentSession.createQuery("from TagGift tg WHERE tg.giftCertificate.id = :giftCertificateId");
        query.setParameter("giftCertificateId", giftCertificate.getId());
        return query.getResultList();
    }
}
