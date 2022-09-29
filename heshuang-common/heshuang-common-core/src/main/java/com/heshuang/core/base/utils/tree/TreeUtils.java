package com.heshuang.core.base.utils.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ll_wang
 * @version v1.0
 * @CreationTime: - 2020/5/26 7:07 下午
 * @Description:
 */
@Slf4j
public class TreeUtils {

    public static <T extends TreeBaseBean>  List<T> toTreeList(List<T> dataList) {
        return toTreeList(dataList,null);
    }

    /**
     * @param dataList
     * @param <T>
     * @return
     * @Author ll_wang
     * @CreationTime 2020/5/19 下午1:57
     * @Description list转tree结构
     */
    public static <T extends TreeBaseBean> List<T> toTreeList(List<T> dataList,List<T> rootTreeList) {
        rootTreeList = CollectionUtils.isEmpty(rootTreeList)?new ArrayList<>(8):rootTreeList;
        if (!CollectionUtils.isEmpty(dataList)) {
            rootTreeList = CollectionUtils.isEmpty(rootTreeList)?dataList.stream().filter(item -> {
                if (item == null) {
                    return false;
                }
                try {
                    return isParentNode(item.getParentId());
                } catch (Exception e) {
                    log.warn("获取{}对象{}属性失败", item, "parentId");
                }
                return false;
            }).collect(Collectors.toList()):rootTreeList;
            //parentId 不存在0,或者""的数据
            if(CollectionUtils.isEmpty(rootTreeList)){
                List<T> rootList = dataList.stream().sorted(Comparator.comparing(e -> e.getParentId().length())).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(rootList)){
                    rootTreeList = dataList.stream().filter(e->e.getParentId().length() == rootList.get(0).getParentId().length()).collect(Collectors.toList());
                }
            }
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

    private static <T extends TreeBaseBean> List<T> getTreeList(List<T> dataList, String parentId) {
        List<T> treeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataList) && parentId != null) {
            treeList = dataList.stream().filter(item -> {
                if (item == null) {
                    return false;
                }
                try {
                    return parentId.equals(String.valueOf(item.getParentId()));
                } catch (Exception e) {
                    log.warn("获取{}对象{}属性失败", item, "id");
                }
                return false;
            }).collect(Collectors.toList());
            //移除已绑定层级关系的数据
            dataList.removeAll(treeList);
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
    private static <T extends TreeBaseBean> void treeSetChildren(List<T> dataList, List<T> treeList) {
        //排序
        Collections.sort(treeList, ((o1, o2) -> {
            int sort1 = 0, sort2 = 0;
            try {
                sort1 = o1.getResOrder();
                sort2 = o2.getResOrder();
            } catch (Exception e) {
                log.debug("转树结构时获取属性异常", e);
            }
            return sort1 - sort2;
        }));

        for (T tree : treeList) {
            String treeId = null;
            try {
                treeId = tree.getId();
                if (treeId != null &&!tree.isLast()) {
                    List children = tree.getChildren();
                    if(CollectionUtils.isEmpty(children)){
                        tree.setChildren(getTreeList(dataList, treeId));
                    }
                }
            } catch (Exception e) {
                log.warn("转树结构时获取属性异常", e);
            }
        }
    }

    public static <T extends TreeBaseBean>  void getTreeLastList(List<T> dataList,List<T> lastList){
        if(CollectionUtils.isEmpty(dataList)){
            return;
        }
        for (T t : dataList) {
            if(CollectionUtils.isEmpty(t.getChildren())){
                lastList.add(t);
            }else{
                getTreeLastList(t.getChildren(),lastList);
            }
        }
    }

    public static <T extends TreeBaseBean>  List<T> getTreeAllList(List<T> treeList){
        ArrayList<T> allList = Lists.newArrayList();
        getTreeAllList(treeList,allList);
        for (T t : allList) {
            t.setChildren(Lists.newArrayList());
        }
        return allList;
    }


    public static <T extends TreeBaseBean>  void getTreeAllList(List<T> treeList,List<T> allList){
        if(CollectionUtils.isEmpty(treeList)){
            return;
        }
        for (T t : treeList) {
            if(CollectionUtils.isEmpty(t.getChildren())){
                allList.add(t);
            }else{
                getTreeLastList(t.getChildren(),allList);
                allList.add(t);
            }
        }
    }


    public static  <T extends TreeBaseBean> void filterByObject(List<T> dataList, Object name, Function<T,Boolean> function) {
        //1.反向取出层级树,所有数据
        List<T> objects = Lists.newArrayList();
        getTreeLastList(dataList,objects);
        //2.过滤出满足name条件的数据
        List<T> deviceData = objects.stream().filter(e -> function.apply(e)).collect(Collectors.toList());
        //3.去重
        Set<String> parentData = deviceData.stream().map(TreeBaseBean::getParentId).collect(Collectors.toSet());
        //4.删除多余层级
        removeData(dataList,parentData);
        deviceData.addAll(getTreeAllList(dataList));
        //5.重新组合
        toTreeList(deviceData,null);
    }

    private static <T extends TreeBaseBean> void removeData(List<T> dataList, Collection<String> parentData) {
        if(CollectionUtils.isEmpty(dataList)){
            return;
        }
        List<T> list = Lists.newArrayList();
        for (T t : dataList) {
            if( parentData.stream().anyMatch(e->e.contains(t.getId()))){
                removeData(t.getChildren(),parentData);
            }else{
                list.add(t);
            }
        }
        dataList.removeAll(list);
    }


    @Getter
    @Setter
    public static class TreeBaseBean<T> implements Serializable {
        /**
         * 层级id
         */
        private String id ;
        /**
         * 父层级
         */
        private String parentId;
        /**
         * 排序序号
         */
        private Integer resOrder;
        /**
         * 下一层级
         */
        private List<T> children;
        /**
         * 层级名称
         */
        private String name;
        /**
         * 是否最后一层
         */
        @JsonIgnore
        private boolean isLast = false;
    }
}