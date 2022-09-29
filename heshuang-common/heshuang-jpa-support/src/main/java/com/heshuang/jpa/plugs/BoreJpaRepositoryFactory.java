//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

public class BoreJpaRepositoryFactory extends JpaRepositoryFactory {
    private static final Logger log = LoggerFactory.getLogger(BoreJpaRepositoryFactory.class);
    private final EntityManager entityManager;
    private final QueryExtractor extractor;

    public BoreJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
        this.extractor = PersistenceProvider.fromEntityManager(entityManager);
        BoreJpaClassWriter.modify();
    }

    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(Key key, QueryMethodEvaluationContextProvider provider) {
        return Optional.of(BoreQueryLookupStrategy.create(this.entityManager, key, this.extractor, provider));
    }

    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return BoreSimpleJpaRepository.class;
    }
}
