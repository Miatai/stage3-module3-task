package com.mjc.school.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Tag;

@Repository
public class TagRepository implements BaseRepository<Tag, Long> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public TagRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Tag> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> tags = cq.from(Tag.class);
        cq.select(tags);
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> readById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag create(Tag entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public Tag update(Tag entity) {
        entityManager.getTransaction().begin();
        Optional<Tag> tagOptional = readById(entity.getId());
        if (tagOptional.isEmpty()) {
            return null;
        }
        Tag tag = tagOptional.get();
        tag.setName(entity.getName());
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.getTransaction().begin();
            Tag tag = readById(id).get();
            entityManager.remove(tag);
            entityManager.getTransaction().commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id) != null;
    }
}
