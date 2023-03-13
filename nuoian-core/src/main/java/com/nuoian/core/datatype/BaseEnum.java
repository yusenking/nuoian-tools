package com.nuoian.core.datatype;

import java.util.EnumSet;
import java.util.Objects;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/8 17:42
 * @Description: 枚举通用接口
 * @Package: com.nuoian.core.datatype
 * @Version: 1.0
 */
public interface BaseEnum<T> {

    T getValue();

    String getLabel();

    static <E extends Enum<E> & BaseEnum> E getEnumByValue(Object value, Class<E> clazz) {
        Objects.requireNonNull(value);
        // 获取类型下的所有枚举
        EnumSet<E> allEnums = EnumSet.allOf(clazz);
        E matchEnum = allEnums.stream()
                .filter(e -> StringUtils.equal(e.getValue(), value))
                .findFirst()
                .orElse(null);
        return matchEnum;
    }

    static <E extends Enum<E> & BaseEnum> String getLabelByValue(Object value, Class<E> clazz) {
        Objects.requireNonNull(value);
        // 获取类型下的所有枚举
        EnumSet<E> allEnums = EnumSet.allOf(clazz);
        E matchEnum = allEnums.stream()
                .filter(e -> StringUtils.equal(e.getValue(), value))
                .findFirst()
                .orElse(null);
        String label = null;
        if (matchEnum != null) {
            label = matchEnum.getLabel();
        }
        return label;
    }

}
