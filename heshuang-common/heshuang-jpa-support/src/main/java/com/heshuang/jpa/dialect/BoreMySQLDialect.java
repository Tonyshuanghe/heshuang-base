//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.dialect;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class BoreMySQLDialect extends MySQL8Dialect {
    public BoreMySQLDialect() {
        this.registerFunction("bitand", new SQLFunctionTemplate(StandardBasicTypes.BIG_INTEGER, "(?1 & ?2)"));
        this.registerFunction("bitor", new SQLFunctionTemplate(StandardBasicTypes.BIG_INTEGER, "(?1 | ?2)"));
        this.registerFunction("bitxor", new SQLFunctionTemplate(StandardBasicTypes.BIG_INTEGER, "(?1 ^ ?2)"));
        this.registerFunction("instr", new SQLFunctionTemplate(StandardBasicTypes.BIG_INTEGER, "INSTR(?1, ?2)"));
    }
}
