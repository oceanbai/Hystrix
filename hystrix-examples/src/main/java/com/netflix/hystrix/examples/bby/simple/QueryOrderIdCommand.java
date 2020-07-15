package com.netflix.hystrix.examples.bby.simple;

import com.netflix.hystrix.*;
import org.junit.Test;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author bby15929
 * @date 2020年07月15日10:44:52
 */
public class QueryOrderIdCommand extends HystrixCommand<Integer> {

    public QueryOrderIdCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("orderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryByOrderId"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(10)//至少有10个请求，熔断器才进行错误率的计算
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)//熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
                        .withCircuitBreakerErrorThresholdPercentage(50)//错误率达到50开启熔断保护
                        .withExecutionTimeoutEnabled(true))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties
                        .Setter().withCoreSize(10)));
    }

    @Override
    protected Integer run() {
        if (true){
            throw new RuntimeException();
        }
        return 1;
    }

    @Override
    protected Integer getFallback() {
        return -1;
    }

    public static class UnitTest {

        @Test
        public void getGetSetGet() {
            Integer r = new QueryOrderIdCommand().execute();
            System.out.println("result:" + r);
        }
    }
}
