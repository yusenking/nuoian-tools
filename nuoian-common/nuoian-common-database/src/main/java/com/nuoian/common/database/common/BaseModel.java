package com.nuoian.common.database.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/8 16:58
 * @Description: 实体基础类
 * @Package: com.nuoian.common.database.common
 * @Version: 1.0
 */
@Data
public class BaseModel implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 页
     */
    @TableField(exist = false)
    private Integer page = 1;

    /**
     * 行数
     */
    @TableField(exist=false)
    private Integer size = 10;
}
