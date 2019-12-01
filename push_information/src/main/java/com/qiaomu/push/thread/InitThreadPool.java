package com.qiaomu.push.thread;

import com.qiaomu.push.queue.KafkaQueue;
import com.qiaomu.push.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 李品先
 * @description:初始化线程池
 * @Date 2019-11-07 10:41
 */
public class InitThreadPool {
    /**
     * 初始化线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);


    public InitThreadPool() {
        KafkaQueue requestQueut = KafkaQueue.getInstance();
        for (int i=0;i<20;i++){
            ArrayBlockingQueue<Request> queue =  new ArrayBlockingQueue<Request>(100);
            requestQueut.addQueut(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    public static void init(){
        getInstance();
    }


    /**
     * 采用静态内部类方式初始化单例
     */
    private static class Singelton{
        private static InitThreadPool instance;
        static {
            instance = new InitThreadPool();
        }
        public static InitThreadPool getInstance(){
            return instance;
        }
    }

    /**
     * 利用JVM机制去保证多线程并发安全，
     * 内部类的初始化一定只会发生一次，不管多少现场并发去初始化
     * @return
     */
    public static InitThreadPool getInstance(){
        return Singelton.getInstance();
    }
}
