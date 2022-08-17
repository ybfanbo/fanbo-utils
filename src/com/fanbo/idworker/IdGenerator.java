package com.fanbo.idworker;

/**
 * 全局唯一ID生成器
 * @author FanBo
 */
public interface IdGenerator<T> {
    /**
     * @return 返回下一个全局唯一ID
     */
    T nextId();
}
