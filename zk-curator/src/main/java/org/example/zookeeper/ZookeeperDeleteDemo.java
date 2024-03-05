package org.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;

public class ZookeeperDeleteDemo {

    public static void main(String[] args) {
        try {
            delete0();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void delete0() throws Exception {
        CuratorFramework curatorFramework = ZookeeperSessionBuilder.buildClient2();
        curatorFramework.start();
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator2");
    }
}
