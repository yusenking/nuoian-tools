package com.nuoian.tools.result;

import com.alibaba.fastjson.JSONObject;
import com.nuoian.tools.constants.ReturnConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 17:16
 * @Description: 控制器基类
 * @Package: com.nuoian.tools.result
 * @Version: 1.0
 */
public class BaseController extends ReturnResult{


    /**
     * 功能描述:
     * 〈列表查询无数据返回〉
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:20
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
     * @param rows 数据集合
     * @param total 数据总数
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:21
     */
    public static ReturnResult tableData(List<Object> rows, Integer total) {
        JSONObject data=new JSONObject();
        data.put("rows",rows);
        data.put("total",total);
        return ok(data);
    }
}
