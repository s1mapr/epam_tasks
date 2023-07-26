package com.epam.esm.dao.mysql.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exeptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MySQLTagDAO implements TagDAO {

    private EntityManager entityManager;

    private static final int LIMIT_FOR_PAGINATION = 10;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> getAllTagsWithPagination(Integer page) {
        Session currentSession = entityManager.unwrap(Session.class);
        TypedQuery<Tag> query = currentSession.createQuery("from Tag", Tag.class);
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
    public Optional<Tag> getTagByName(String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Tag> query = currentSession.createQuery("FROM Tag WHERE name = :tagName", Tag.class);
        query.setParameter("tagName", name);
        Tag tag = query.uniqueResult();
        return Optional.ofNullable(tag);
    }

    @Override
    public void createTag(Tag tag) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(tag);
    }

    @Override
    public void deleteTagById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Tag tag = getTagById(id).orElseThrow(() -> new BadRequestException("Tag with id " + id + " doesn't exist"));
        currentSession.delete(tag);
    }


    @Override
    public Optional<Tag> getTagById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Tag tag = currentSession.get(Tag.class, id);
        if (tag == null) {
            throw new BadRequestException("Tag with id " + id + " doesn't exist");
        }
        return Optional.of(tag);
    }

}
