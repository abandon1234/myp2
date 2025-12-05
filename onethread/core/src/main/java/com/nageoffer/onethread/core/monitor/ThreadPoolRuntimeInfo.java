package com.nageoffer.onethread.core.monitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 线程池运行时监控实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolRuntimeInfo {

    /**
     * 线程池唯一标识
     */
    private String threadPoolId;

    /**
     * 核心线程数
     */
    private Integer corePoolSize;//线程池允许创建的最小线程数下限 ThreadPoolExecutor.getCorePoolSize() 获取

    /**
     * 最大线程数
     */
    private Integer maximumPoolSize;//线程池允许创建的最大线程数上限 ThreadPoolExecutor.getMaximumPoolSize() 获取

    /**
     * 当前线程数
     */
    private Integer currentPoolSize;//当前线程池中的线程总数 所有已创建的线程，无论是否正在执行任务 ThreadPoolExecutor.getPoolSize() 获取

    /**
     * 活跃线程数
     */
    private Integer activePoolSize;//当前正在执行任务的活跃线程数 不包括: 空闲等待任务的线程 ThreadPoolExecutor.getActiveCount() 获取

    /**
     * 最大线程数
     */
    private Integer largestPoolSize;//线程池历史上达到的最大线程数（largestPoolSize ≤ maximumPoolSize）ThreadPoolExecutor.getLargestPoolSize() 获取

    /**
     * 线程池任务总量
     */
    private Long completedTaskCount;//线程池已完成的任务总数 每当一个任务执行完成，该计数器就会增加 ThreadPoolExecutor.getCompletedTaskCount() 获取

    /**
     * 阻塞队列类型
     */
    private String workQueueName;//阻塞队列类型 BlockingQueue.getClass().getSimpleName()

    /**
     * 队列容量
     */
    private Integer workQueueCapacity;//阻塞队列的容量 BlockingQueue.remainingCapacity() 方法获取

    /**
     * 队列元素数量
     */
    private Integer workQueueSize;//阻塞队列中已存储的任务数量 BlockingQueue.size() 方法获取

    /**
     * 队列剩余容量
     */
    private Integer workQueueRemainingCapacity;//阻塞队列剩余容量 BlockingQueue.remainingCapacity() 方法获取

    /**
     * 拒绝策略
     */
    private String rejectedHandlerName;//拒绝策略 RejectedExecutionHandler.toString()

    /**
     * 执行拒绝策略次数
     */
    private Long rejectCount;
}
