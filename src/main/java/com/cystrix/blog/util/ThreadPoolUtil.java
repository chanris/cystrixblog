package com.cystrix.blog.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/6/1
 * @description
 */
@Slf4j
public final class ThreadPoolUtil {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    // 默认线程池配置
    private static final int DEFAULT_CORE_POOL_SIZE = CPU_COUNT;
    private static final int DEFAULT_MAX_POOL_SIZE = CPU_COUNT * 2;
    private static final int DEFAULT_QUEUE_CAPACITY = 1024;
    private static final long DEFAULT_KEEP_ALIVE_SECONDS = 60L;

    /**
     * 创建一个标准线程池
     *
     * @param namePrefix 线程名前缀
     * @return ThreadPoolExecutor 实例
     */
    public static ThreadPoolExecutor createThreadPool(String namePrefix) {
        log.info("创建线程池: {}", namePrefix);
        return new ThreadPoolExecutor(
                DEFAULT_CORE_POOL_SIZE,
                DEFAULT_MAX_POOL_SIZE,
                DEFAULT_KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(DEFAULT_QUEUE_CAPACITY),
                new NamedThreadFactory(namePrefix),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 优雅关闭线程池
     */
    public static void shutdown(ThreadPoolExecutor executor) {
        if (executor == null || executor.isShutdown()) return;
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 自定义线程工厂，便于排查问题
     */
    static class NamedThreadFactory implements ThreadFactory {
        private final String prefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public NamedThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, prefix + "-thread-" + threadNumber.getAndIncrement());
        }
    }

    /**
     * 打印线程池状态（可用于监控）
     */
    public static void logThreadPoolStatus(ThreadPoolExecutor executor, String poolName) {
        if (executor == null) return;
        System.out.printf("[ThreadPool:%s] PoolSize=%d, Active=%d, QueueSize=%d, Completed=%d%n",
                poolName,
                executor.getPoolSize(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                executor.getCompletedTaskCount()
        );
    }
}

