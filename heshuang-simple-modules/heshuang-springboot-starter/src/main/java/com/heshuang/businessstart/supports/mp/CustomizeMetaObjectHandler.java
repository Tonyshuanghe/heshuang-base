package com.heshuang.businessstart.supports.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author
 * @date 数据填充
 */
@Component
@Slf4j
public class CustomizeMetaObjectHandler implements MetaObjectHandler {

    /**
     * 删除日期字段
     */
    public static final String DELETE_DATE = "deleteTm";

    /**
     * 删除字段
     */
    public static final String DELETED = "isFlag";

    /**
     * 更新日期字段
     */
    public static final String UPDATE_DATE = "updateTm";

    /**
     * 创建日期字段
     */
    public static final String CREATE_DATE = "createTm";

    /**
     * 创建人字段
     */
    public static final String CREATE_BY = "createBy";

    /**
     * 创建人字段
     */
    public static final String UPDATE_BY = "updateBy";

    /**
     * 删除标记
     */
    public static final int DELETED_FLAG = 0;


    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject, CREATE_DATE, LocalDateTime.class, LocalDateTime.now());
        if (metaObject.hasSetter(CREATE_BY)) {
            try {
                //TODO
                this.strictInsertFill(metaObject, CREATE_BY, Long.class, null);
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, UPDATE_DATE, LocalDateTime.class, LocalDateTime.now());
        if (metaObject.hasSetter(UPDATE_BY)) {
            try {
                //TODO
                this.strictInsertFill(metaObject, UPDATE_BY, Long.class, null);
            } catch (Exception e) {
            }
        }
        if (metaObject.hasGetter(DELETED)) {
            // 判断删除字段值
            Object deletedVal = metaObject.getValue(DELETED);
            if (deletedVal != null && (Integer) deletedVal == DELETED_FLAG) {
                boolean deleteDate = metaObject.hasSetter(DELETE_DATE);
                if (deleteDate) {
                    // 更新删除时间
                    this.strictUpdateFill(metaObject, DELETE_DATE, LocalDateTime.class, LocalDateTime.now());
                }
            }
        }
    }

}
