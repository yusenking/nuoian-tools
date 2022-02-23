package com.nuoian.tools.result;

import com.nuoian.tools.constants.ReturnConstants;

import java.util.HashMap;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/23 17:03
 * @Description: 返回工具类
 * @Package: com.nuoian.tools.result
 * @Version: 1.0
 */
public class ReturnResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 功能描述:
     * 〈默认成功返回〉
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:07
     */
    public static ReturnResult ok(){
        return ok(ReturnConstants.RETURN_SUCCESS);
    }

    /**
     * 功能描述:
     * 〈成功返回带消息〉
     * @param message 返回的消息
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:07
     */
    public static ReturnResult ok(String message) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.put("code", ReturnConstants.CODE_SUCCESS);
        returnResult.put("message", message);
        return returnResult;
    }

    /**
     * 功能描述:
     * 〈成功返回带数据〉
     * @param data 数据对象
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:08
     */
    public static ReturnResult ok(Object data) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.put("code", ReturnConstants.CODE_SUCCESS);
        returnResult.put("message", ReturnConstants.RETURN_SUCCESS);
        returnResult.put("data", data);
        return returnResult;
    }

    /**
     * 功能描述:
     * 〈成功返回带数据及自定义消息〉
     * @param message 消息
     * @param data 数据
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:09
     */
    public static ReturnResult ok(String message,Object data) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.put("code", ReturnConstants.CODE_SUCCESS);
        returnResult.put("message", message);
        returnResult.put("data", data);
        return returnResult;
    }

    /**
     * 功能描述:
     * 〈默认返回失败〉
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:15
     */
    public static ReturnResult error() {
        return error(ReturnConstants.RETURN_ERROR);
    }


    /**
     * 功能描述:
     * 〈错误返回自定义消息〉
     * @param messag 自定义消息
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:13
     */
    public static ReturnResult error(String messag) {
        return error(ReturnConstants.CODE_ERROR, messag);
    }

    /**
     * 功能描述:
     * 〈错误返回自定义状态及消息〉
     * @param code 状态Code
     * @param message 自定义消息
     * @return: com.nuoian.tools.result.ReturnResult
     * @author: 吴宇森
     * @date: 2022/2/23 17:12
     */
    public static ReturnResult error(int code, String message) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.put("code", code);
        returnResult.put("message", message);
        return returnResult;
    }
}
