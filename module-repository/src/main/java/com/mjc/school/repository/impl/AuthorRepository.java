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
import com.mjc.school.repository.model.Author;

@Repository
public class AuthorRepository implements BaseRepository<Author, Long> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Author> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> authors = cq.from(Author.class);
        cq.select(authors);
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Optional<Author> readById(Long authorId) {
        return Optional.of(entityManager.find(Author.class, authorId));
    }

    @Override
    public Author create(Author entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public Author update(Author entity) {
        entityManager.getTransaction().begin();
        Optional<Author> authorOptional = readById(entity.getId());
        if (authorOptional.isEmpty()) {
            return null;
        }
        Author author = authorOptional.get();
        author.setName(entity.getName());
        author.setLastUpdatedDate(entity.getLastUpdatedDate());
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public boolean deleteById(Long authorId) {
        if (existById(authorId)) {
            entityManager.getTransaction().begin();
            Author author = readById(authorId).get();
            entityManager.remove(author);
            entityManager.getTransaction().commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long authorId) {
        return readById(authorId) != null;
    }
}