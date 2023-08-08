package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>, PagingAndSortingRepository<Tag, Integer> {
    void deleteTagById(int id);
    Optional<Tag> getTagById(int id);
    Optional<Tag> getTagByName(String name);
}
