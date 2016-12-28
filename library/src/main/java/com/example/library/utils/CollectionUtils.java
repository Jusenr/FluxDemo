package com.example.library.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * collection处理
 * Created by zsw on 2016/7/6.
 */
public final class CollectionUtils {

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty())
            return null;
        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 排序器
     */
    public static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
}
