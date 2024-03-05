package org.example.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Hello world!
 */
public class ZookeeperSessionBuilder {
    public static void main(String[] args) {
        CuratorFramework curatorFramework = buildClient1();
        curatorFramework.start();
        System.out.println("zookeeper已连接");
    }


    /**
     * 构建客户端
     * @return
     */
    public static CuratorFramework buildClient1() {
       return CuratorFrameworkFactory.newClient("192.168.119.143:2181",5000,3000,getPolicy());
    }

    /**
     * 重试策略
     * @return
     */
    private static RetryPolicy getPolicy() {
        return new ExponentialBackoffRetry(1000, 3);
    }

    /**
     * 构建客户端，基于 test 目录操作
     * @return
     */
    public static CuratorFramework buildClient2() {
        return CuratorFrameworkFactory.builder()
                .connectString("192.168.119.143:2181")
                .connectionTimeoutMs(3000)
                .sessionTimeoutMs(5000)
                .retryPolicy(getPolicy())
                .namespace("test")
                .build();
    }
}
