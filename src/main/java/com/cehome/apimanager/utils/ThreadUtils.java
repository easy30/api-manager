package com.cehome.apimanager.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void execute(Task task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                task.invoke();
            }
        });
    }

    public interface Task {
        void invoke();
    }
}
