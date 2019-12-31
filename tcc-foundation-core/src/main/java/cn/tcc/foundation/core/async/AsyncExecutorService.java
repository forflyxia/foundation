package cn.tcc.foundation.core.async;

import java.util.concurrent.*;

public class AsyncExecutorService {

    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(5000));

    public static <T> Future<?> submit(Callable<T> task) {
        try {
            return executorService.submit(task);
        } catch (Throwable throwable) {
            System.out.println("AsyncExecutorService submit callable task error");
        }
        return null;
    }

    public static <T> Future<?> submit(Runnable task) {
        try {
            return executorService.submit(task);
        } catch (Throwable throwable) {
            System.out.println("AsyncExecutorService submit runable task error");
        }
        return null;
    }
}
