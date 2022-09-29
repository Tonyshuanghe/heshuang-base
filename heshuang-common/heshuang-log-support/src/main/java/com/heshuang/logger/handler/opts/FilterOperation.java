//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.opts;


import com.heshuang.logger.anno.IgnoreProp;
import com.heshuang.logger.handler.IFilter;
import com.heshuang.logger.handler.IOperation;
import lombok.Data;

import java.lang.reflect.Method;
@Data
public class FilterOperation implements IOperation<Method> {
    private IFilter filter;
    private String filterKey;

    private FilterOperation(Method method) throws Exception {
        this.init(method);
    }

    public static FilterOperation build(Method method) throws Exception {
        return new FilterOperation(method);
    }
    @Override
    public void init(Method method) throws Exception {
        IgnoreProp ignoreProp = (IgnoreProp)method.getAnnotation(IgnoreProp.class);
        this.filter = (IFilter)this.getBean(ignoreProp.value());
        this.filterKey = ignoreProp.filterKey();
    }

    public IFilter getFilter() {
        return this.filter;
    }

    public String getFilterKey() {
        return this.filterKey;
    }

    public void setFilter(IFilter filter) {
        this.filter = filter;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }
}
