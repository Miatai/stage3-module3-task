package com.mjc.school.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.mjc.school")
@EnableAspectJAutoProxy
public class ApplicationConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    // dataSource.setUrl("jdbc:h2:tcp://localhost/./testdbstage3");
    dataSource.setUrl("jdbc:h2:tcp://localhost/./stage3db");
    dataSource.setUsername("user");
    dataSource.setPassword("user");
    return dataSource;
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {
    return Persistence.createEntityManagerFactory("GlobalManagement");
  }

  // @Bean("authorReadByNewsId")
  // public ReadByNewsIdSevice<AuthorDtoRequest, AuthorDtoResponse, Long> authorReadByNewsIdSevice(
  //      BaseRepository<Author, Long> authorRepository, 
  //      AuthorMapper mapper,
  //      BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
  //   return new AuthorService(authorRepository, mapper, newsService);
  // }

  // @Bean("tagReadByNewsId")
  // public ReadByNewsIdSevice<TagDtoRequest, TagDtoResponse, Long> tagReadByNewsId(
  //      BaseRepository<Tag, Long> tagRepository,
  //      TagMapper mapper,
  //      BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
  //   return new TagService(tagRepository, mapper, newsService);
  // }

}
