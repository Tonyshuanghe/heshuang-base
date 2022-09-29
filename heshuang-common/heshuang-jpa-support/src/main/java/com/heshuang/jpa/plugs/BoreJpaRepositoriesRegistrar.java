//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import java.lang.annotation.Annotation;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

public class BoreJpaRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {
    public BoreJpaRepositoriesRegistrar() {
    }

    protected Class<? extends Annotation> getAnnotation() {
        return EnableBoreJpa.class;
    }

    protected RepositoryConfigurationExtension getExtension() {
        return new JpaRepositoryConfigExtension();
    }
}
