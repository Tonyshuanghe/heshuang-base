//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import java.util.concurrent.atomic.AtomicBoolean;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoreJpaClassWriter {
    private static final Logger log = LoggerFactory.getLogger(BoreJpaClassWriter.class);
    private static final String JPA_METHOD_FACTORY_NAME = "org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory";
    private static Boolean hasJpaMethodClass;
    private static final AtomicBoolean modified = new AtomicBoolean(false);

    public static synchronized boolean hasDefaultJpaQueryMethodFactoryClass() {
        if (hasJpaMethodClass != null) {
            return hasJpaMethodClass;
        } else {
            try {
                Thread.currentThread().getContextClassLoader().loadClass("org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory");
                hasJpaMethodClass = true;
            } catch (ClassNotFoundException var1) {
                log.debug("【Bore -> 'JPA 版本检测' 提示】检查到你的项目中没有【{}】类，说明你的 Spring Data JPA 版本是 v2.3.0 之前的版本.", "org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory");
                hasJpaMethodClass = false;
            } catch (Exception var2) {
                if (log.isDebugEnabled()) {
                    log.debug("【Bore -> 'JPA 版本检测' 提示】检查你的项目中是否有【{}】类时出错，将默认你的 Spring Data JPA 版本是 v2.3.0 之前的版本.", "org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory", var2);
                } else {
                    log.error("【Bore -> 'JPA 版本检测' 错误】检查你的项目中是否有【{}】类时出错，将默认你的 Spring Data JPA 版本是 v2.3.0 之前的版本，检测时的出错原因是：【{}】，若想看更全的错误堆栈日志信息，请开启 debug 日志级别.", "org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory", var2.getMessage());
                }

                hasJpaMethodClass = false;
            }

            return hasJpaMethodClass;
        }
    }

    public static synchronized void modify() {
        if (hasDefaultJpaQueryMethodFactoryClass()) {
            log.debug("【Bore 提示】检测到你的 Spring Data JPA 版本是 v2.3.0 及以上，可不用修改 class 来兼容老版本的 JPA.");
        } else if (modified.get()) {
            log.debug("【Bore 提示】已经修改过了【BoreQueryLookupStrategy.class】中的部分方法，将不再修改.");
        } else {
            log.info("【Bore 提示】检测到你的 Spring Data JPA 版本较低，为了兼容老版本的 JPA，将修改部分 class 字节码来做兼容。不过条件允许的话，我仍然建议你将 Spring Data JPA 版本升级到 v2.3.0 及之后的版本.");

            try {
                ClassPool classPool = ClassPool.getDefault();
                classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
                CtClass ctClass = classPool.get("com.sanrui.support.jpa.plugs.BoreQueryLookupStrategy");
                CtMethod lookupStrategyMethod = ctClass.getDeclaredMethod("createOldJpaQueryLookupStrategy");
                lookupStrategyMethod.setBody("{return org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy.create($1, $2, $3, $4, $5);}");
                CtMethod ctMethod = ctClass.getDeclaredMethod("createOldBoreJpaQuery");
                ctMethod.setBody("{return new com.sanrui.support.jpa.plugs.BoreJpaQuery(new org.springframework.data.jpa.repository.query.JpaQueryMethod($1, $2, $3, $4), $5);}");
                ctClass.toClass();
                modified.getAndSet(true);
            } catch (Exception var4) {
                log.error("【Bore 错误提示】使用 Javassist 修改【BoreQueryLookupStrategy】class 中的代码出错，建议升级 Spring Boot 的版本为 v2.3.0 及之上.", var4);
            }

        }
    }

    private BoreJpaClassWriter() {
    }
}
