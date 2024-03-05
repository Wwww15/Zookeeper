package org.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;

public class ZookeeperUpdateDemo {

    public static void main(String[] args) {
        try {
            update0();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void update0() throws Exception {
        CuratorFramework curatorFramework = ZookeeperSessionBuilder.buildClient1();
        curatorFramework.start();
        curatorFramework.setData().forPath("/zk-persistent_sequential-1", "持久顺序节点回调已修改".getBytes());
        System.out.println("值已修改");
    }
}
