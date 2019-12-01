package com.qiaomu.push.thread;


import com.qiaomu.push.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @Author: lipx
 * @Description: 执行请求的工作线程
 * @Date: 15:23 2019/9/26
 */
public class RequestProcessorThread implements Callable<Boolean> {

    /****自己监控的内存列队***/
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true){
                // :如果列队满了或者空的，都会在执行操作时候阻塞住
                Request request = queue.take();
                request.process();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
