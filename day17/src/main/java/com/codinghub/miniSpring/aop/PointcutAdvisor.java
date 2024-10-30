package com.codinghub.miniSpring.aop;

/**
 * @author 莱特0905
 * @Description: 切点代理接口
 * @Date: 2024/10/30 17:03:49
 */
public interface PointcutAdvisor extends Advisor{
    /**
     * 获取切点对象
     * @return 切点对象
     */
    Pointcut getPointCut();
}
