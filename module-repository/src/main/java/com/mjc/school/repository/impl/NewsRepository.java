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
import com.mjc.school.repository.model.News;

@Repository
public class NewsRepository implements BaseRepository<News, Long> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public NewsRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Override
    public List<News> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> cq = cb.createQuery(News.class);
        Root<News> news = cq.from(News.class);
        cq.select(news);
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Optional<News> readById(Long newsId) {
        return Optional.of(entityManager.find(News.class, newsId));
    }

    @Override
    public News create(News entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public News update(News entity) {
        entityManager.getTransaction().begin();
        Optional<News> newsOptional = readById(entity.getId());
        if (newsOptional.isEmpty()) {
            return null;
        }
        News news = newsOptional.get();
        news.setTitle(entity.getTitle());
        news.setContent(entity.getContent());
        news.setLastUpdatedDate(entity.getLastUpdatedDate());
        news.setAuthor(entity.getAuthor());
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public boolean deleteById(Long newsId) {
        if (existById(newsId)) {
            entityManager.getTransaction().begin();
            News news = readById(newsId).get();
            entityManager.remove(news);
            entityManager.getTransaction().commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long newsId) {
        return readById(newsId) != null;
    }
}
