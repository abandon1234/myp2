package com.nageoffer.onethread.config.nacos.cloud.starter.refresher;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.nageoffer.onethread.config.common.starter.refresher.AbstractDynamicThreadPoolRefresher;
import com.nageoffer.onethread.core.executor.support.BlockingQueueTypeEnum;
import com.nageoffer.onethread.core.toolkit.ThreadPoolExecutorBuilder;
import com.nageoffer.onethread.core.config.BootstrapConfigProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Nacos Cloud 版本刷新处理器
 */
@Slf4j(topic = "OneThreadConfigRefresher")
public class NacosCloudRefresherHandler extends AbstractDynamicThreadPoolRefresher {

    private ConfigService configService;

    public NacosCloudRefresherHandler(ConfigService configService, BootstrapConfigProperties properties) {
        super(properties);
        this.configService = configService;//获取 Nacos 的配置服务客户端
    }

    public void registerListener() throws NacosException {
        BootstrapConfigProperties.NacosConfig nacosConfig = properties.getNacos();
        configService.addListener(
                nacosConfig.getDataId(),
                nacosConfig.getGroup(),
                new Listener() {

                    @Override
                    public Executor getExecutor() {
                        return ThreadPoolExecutorBuilder.builder()
                                .corePoolSize(1)
                                .maximumPoolSize(1)
                                .keepAliveTime(9999L)
                                .workQueueType(BlockingQueueTypeEnum.SYNCHRONOUS_QUEUE)
                                .threadFactory("clod-nacos-refresher-thread_")
                                .rejectedHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                                .build();
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        refreshThreadPoolProperties(configInfo);
                    }
                });

        log.info("Dynamic thread pool refresher, add nacos cloud listener success. data-id: {}, group: {}", nacosConfig.getDataId(), nacosConfig.getGroup());
    }
}
