package com.fanbo.utils;

import com.fanbo.idworker.IdWorker;

/**
 * @Description: 全局唯一ID生成工具
 * @Author: FanBo
 */
public class IdWorkerUtil {

    /**
     * 序列号生成器
     */
    private static final IdWorker idWorker = new IdWorker(0, 0);

    /**
     * 生成唯一序列号
     */
    public static final String nextId() {
        return idWorker.nextId().toString();
    }

}
