package com.heshuang.core.base.utils.tree;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author heshuang
 * @version v1.0
 * @CreationTime: - 2020/5/26 7:07 下午
 * @Description:
 */
@Slf4j
public class ReflectTreeUtils {

    public static <T> List<T> toTreeList(List<T> dataList) {
        return toTreeList(dataList, null);
    }

    /**
     * @param dataList
     * @param <T>
     * @return
     * @Author heshuang
     * @CreationTime 2020/5/19 下午1:57
     * @Description list转tree结构
     */
    public static <T> List<T> toTreeList(List<T> dataList, List<T> rootTreeList) {
        rootTreeList = CollectionUtil.isEmpty(rootTreeList) ? new ArrayList<>(8) : rootTreeList;
        if (!CollectionUtil.isEmpty(dataList)) {
            rootTreeList = CollectionUtil.isEmpty(rootTreeList) ? dataList.stream().filter(item -> {
                if (item == null) {
                    return false;
                }
                try {
                    return isParentNode(ReflectUtil.getFieldValue(item, "parentId"));
                } catch (Exception e) {
                    log.warn("获取{}对象{}属性失败", item, "parentId");
                }
                return false;
            }).collect(Collectors.toList()) : rootTreeList;
            treeSetChildren(dataList, rootTreeList);
        }
        return rootTreeList;
    }

    private static Boolean isParentNode(Object node) {
        if (node == null) {
            return true;
        }
        if ("0".equals(String.valueOf(node)) || "".equals(String.valueOf(node))) {
            return true;
        }
        return false;
    }

    private static <T> List<T> getTreeList(List<T> dataList, String parentId) {
        List<T> treeList = new ArrayList<>();
        if (!CollectionUtil.isEmpty(dataList) && parentId != null) {
            treeList = dataList.stream().filter(item -> {
                if (item == null) {
                    return false;
                }
                try {
                    return parentId.equals(String.valueOf(ReflectUtil.getFieldValue(item, "parentId")));
                } catch (Exception e) {
                    log.warn("获取{}对象{}属性失败", item, "id");
                }
                return false;
            }).collect(Collectors.toList());
            treeSetChildren(dataList, treeList);
        }
        return treeList;
    }

    /**
     * 设置children属性
     *
     * @param dataList
     * @param treeList
     * @param <T>
     */
    private static <T> void treeSetChildren(List<T> dataList, List<T> treeList) {
        //排序
        Collections.sort(treeList, ((o1, o2) -> {
            int sort1 = 0, sort2 = 0;
            try {
                sort1 = (Integer) ReflectUtil.getFieldValue(o1, "resOrder");
                sort2 = (Integer) ReflectUtil.getFieldValue(o2, "resOrder");
            } catch (Exception e) {
                log.debug("转树结构时获取属性异常", e);
            }
            return sort1 - sort2;
        }));

        for (T tree : treeList) {
            String treeId = null;
            try {
                treeId = ReflectUtil.getFieldValue(tree, "id").toString();
                if (treeId != null) {
                    if (CollectionUtil.isEmpty((List) ReflectUtil.getFieldValue(tree, "children"))) {
                        ReflectUtil.setFieldValue(tree, "children", getTreeList(dataList, treeId));
                    }
                }
            } catch (Exception e) {
                log.warn("转树结构时获取属性异常", e);
            }
        }
    }

}