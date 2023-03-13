package com.nuoian.core.result;

import com.alibaba.fastjson2.JSONObject;
import com.nuoian.core.constants.ReturnConstants;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/1 15:01
 * @Description: 基础控制器
 * @Package: com.nuoian.core.result
 * @Version: 1.0
 */
public class BaseController extends ReturnResult{

    /**
     * 功能描述:
     * 〈列表查询无数据返回〉
     * @return: com.nuoian.core.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/4/1 17:20
     */
    public static ReturnResult noneData() {
        JSONObject data=new JSONObject();
        data.put("rows",new ArrayList<>());
        data.put("total",0);
        return ok(ReturnConstants.NONE_DATA,data);
    }

    /**
     * 功能描述:
     * 〈列表查询数据返回〉
     *
     * @param rows  数据集合
     * @param total 数据总数
     * @return: com.nuoian.core.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/4/1 17:21
     */
    public static ReturnResult tableData(Collection rows, Long total) {
        JSONObject data = new JSONObject();
        data.put("rows", rows);
        data.put("total", total);
        return ok(data);
    }
}
