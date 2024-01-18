package com.mjc.school.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.repository.utils.NewsParams;

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
        return Optional.ofNullable(entityManager.find(News.class, newsId));
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

    public List<News> readByParams(NewsParams params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> cq = cb.createQuery(News.class);
        Root<News> news = cq.from(News.class);

        List<Predicate> predicates = getPredicates(params, cb, news);

        cq.select(news).where(predicates.toArray(Predicate[]::new));
        return entityManager.createQuery(cq).getResultList();
    }

    private List<Predicate> getPredicates(NewsParams params, CriteriaBuilder cb, Root<News> news) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.getTitle() != null && !params.getTitle().trim().isEmpty()) {
            predicates.add(cb.like(news.get("title"), "%" + params.getTitle() + "%"));
        }

        if (params.getContent() != null && !params.getContent().trim().isEmpty()) {
            predicates.add(cb.like(news.get("content"), "%" + params.getContent() + "%"));
        }

        if (params.getAuthorName() != null && !params.getAuthorName().trim().isEmpty()) {
            Join<News, Author> author = news.join("author");
            predicates.add(cb.like(author.get("name"), "%" + params.getAuthorName() + "%"));
        }
        if ((params.getTagIds() != null && !params.getTagIds().isEmpty())
                || (params.getTagNames() != null && !params.getTagNames().isEmpty())) {
            Join<News, Tag> tag = news.join("tags");

            if (params.getTagIds() != null && !params.getTagIds().isEmpty()) {
                predicates.add(tag.get("id").in(params.getTagIds()));
            }

            if (params.getTagNames() != null && !params.getTagNames().isEmpty()) {
                predicates.add(tag.get("name").in(params.getTagNames()));
            }
        }

        return predicates;
    }
}
