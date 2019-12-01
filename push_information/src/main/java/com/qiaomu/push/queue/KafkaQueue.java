package com.qiaomu.push.queue;

import com.qiaomu.push.request.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 李品先
 * @description:kafka列队
 * @Date 2019-11-07 10:55
 */
public class KafkaQueue {
    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queueList = new ArrayList<ArrayBlockingQueue<Request>>();

    /**
     * 标示位
     */
    private Map<Integer,Boolean> flagMap = new ConcurrentHashMap<>();
    /**
     * 采用静态内部类方式初始化单例
     */
    private static class Singelton{
        private static KafkaQueue instance;
        static {
            instance = new KafkaQueue();
        }
        public static KafkaQueue getInstance(){
            return instance;
        }
    }

    /**
     * 利用JVM机制去保证多线程并发安全，
     * 内部类的初始化一定只会发生一次，不管多少现场并发去初始化
     * @return
     */
    public static KafkaQueue getInstance(){
        return Singelton.getInstance();
    }

    /**
     * 增加一个内存队列
     * @param queue
     */
    public void addQueut(ArrayBlockingQueue<Request> queue){
        this.queueList.add(queue);

    }

    /**
     * 获取内存队列数量
     * @return
     */
    public int queueSize(){
        return  queueList.size();
    }

    /**
     * 获取内存队列
     * @param index
     * @return
     */
    public  ArrayBlockingQueue<Request> getQueue(int index){
        return queueList.get(index);
    }

    public Map<Integer,Boolean> getFlagMap(){
        return flagMap;
    }
}
