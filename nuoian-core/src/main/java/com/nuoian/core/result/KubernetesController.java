package com.nuoian.core.result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 吴宇森
 * @Date: 2022/8/30 16:40
 * @Description: K8s探针控制器
 * @Package: com.nuoian.core.result
 * @Version: 1.0
 */
@RestController
@RequestMapping("/actuator/health")
public class KubernetesController {

    /**
     * 功能描述:
     * 〈存活控制器〉
     *
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/8/30 16:59
     */
    @GetMapping("liveness")
    public ReturnResult liveness() {
        return ReturnResult.ok();
    }

    /**
     * 功能描述:
     * 〈就绪控制器〉
     *
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/8/30 16:59
     */
    @GetMapping("readiness")
    public ReturnResult readiness() {
        return ReturnResult.ok();
    }
}
