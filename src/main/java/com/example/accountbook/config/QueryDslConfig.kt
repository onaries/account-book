package com.example.accountbook.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
open class QueryDslConfig(@PersistenceContext val entityManager: EntityManager) {

    @Bean
    open fun queryFactory() = com.querydsl.jpa.impl.JPAQueryFactory(entityManager)


}
