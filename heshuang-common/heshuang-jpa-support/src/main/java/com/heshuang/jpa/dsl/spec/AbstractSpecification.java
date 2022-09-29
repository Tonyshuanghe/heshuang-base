//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dsl.spec;

import java.io.Serializable;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

abstract class AbstractSpecification<T> implements Specification<T>, Serializable {
    AbstractSpecification() {
    }

    public String getProperty(String property) {
        return property.contains(".") ? StringUtils.split(property, ".")[1] : property;
    }

    public From getRoot(String property, Root<T> root) {
        if (property.contains(".")) {
            String joinProperty = StringUtils.split(property, ".")[0];
            return root.join(joinProperty, JoinType.LEFT);
        } else {
            return root;
        }
    }
}
