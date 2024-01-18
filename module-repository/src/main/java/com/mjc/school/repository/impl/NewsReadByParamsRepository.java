package com.mjc.school.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mjc.school.repository.ReadByParamsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.repository.utils.NewsParams;

@Repository
public class NewsReadByParamsRepository implements ReadByParamsRepository<News, NewsParams, Long> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public NewsReadByParamsRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    @Override
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
