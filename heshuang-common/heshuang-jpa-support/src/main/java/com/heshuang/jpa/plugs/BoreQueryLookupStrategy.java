//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import java.lang.reflect.Method;
import javax.persistence.EntityManager;

import com.heshuang.jpa.plugs.anno.BoreQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

public class BoreQueryLookupStrategy implements QueryLookupStrategy {
    private static final Logger log = LoggerFactory.getLogger(BoreQueryLookupStrategy.class);
    private final EntityManager entityManager;
    private final QueryExtractor extractor;
    private final QueryLookupStrategy jpaQueryLookupStrategy;
    private Object queryMethodFactory;

    private BoreQueryLookupStrategy(EntityManager entityManager, Key key, QueryExtractor extractor, QueryMethodEvaluationContextProvider provider) {
        this.entityManager = entityManager;
        this.extractor = extractor;
        if (BoreJpaClassWriter.hasDefaultJpaQueryMethodFactoryClass()) {
            this.queryMethodFactory = new DefaultJpaQueryMethodFactory(extractor);
            this.jpaQueryLookupStrategy = JpaQueryLookupStrategy.create(entityManager, (JpaQueryMethodFactory)this.queryMethodFactory, key, provider, EscapeCharacter.DEFAULT);
        } else {
            this.jpaQueryLookupStrategy = this.createOldJpaQueryLookupStrategy(entityManager, key, extractor, provider, EscapeCharacter.DEFAULT);
        }

    }

    public QueryLookupStrategy createOldJpaQueryLookupStrategy(EntityManager entityManager, Key key, QueryExtractor extractor, QueryMethodEvaluationContextProvider provider, EscapeCharacter character) {
        return null;
    }

    static QueryLookupStrategy create(EntityManager entityManager, Key key, QueryExtractor extractor, QueryMethodEvaluationContextProvider provider) {
        return new BoreQueryLookupStrategy(entityManager, key, extractor, provider);
    }

    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory, NamedQueries namedQueries) {
        BoreQuery boreQueryAnnotation = (BoreQuery)method.getAnnotation(BoreQuery.class);
        if (boreQueryAnnotation == null) {
            return this.jpaQueryLookupStrategy.resolveQuery(method, metadata, factory, namedQueries);
        } else {
            BoreJpaQuery boreJpaQuery;
            if (this.queryMethodFactory == null) {
                boreJpaQuery = this.createOldBoreJpaQuery(method, metadata, factory, this.extractor, this.entityManager);
            } else {
                boreJpaQuery = new BoreJpaQuery(((JpaQueryMethodFactory)this.queryMethodFactory).build(method, metadata, factory), this.entityManager);
            }

            boreJpaQuery.setBoreQuery(boreQueryAnnotation);
            boreJpaQuery.setQueryClass(method.getDeclaringClass());
            return boreJpaQuery;
        }
    }

    public BoreJpaQuery createOldBoreJpaQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory, QueryExtractor extractor, EntityManager entityManager) {
        return null;
    }
}
