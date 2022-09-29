//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dsl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public final class Sorts {
    private Sorts() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<Order> orders = new ArrayList();

        public Builder() {
        }

        public Builder asc(String property) {
            return this.asc(true, property);
        }

        public Builder desc(String property) {
            return this.desc(true, property);
        }

        public Builder asc(boolean condition, String property) {
            if (condition) {
                this.orders.add(new Order(Direction.ASC, property));
            }

            return this;
        }

        public Builder desc(boolean condition, String property) {
            if (condition) {
                this.orders.add(new Order(Direction.DESC, property));
            }

            return this;
        }

        public Sort build() {
            return Sort.by(this.orders);
        }
    }
}
