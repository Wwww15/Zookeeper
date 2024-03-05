package org.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

public class ZookeeperGetDemo implements CuratorWatcher {

    private final static CountDownLatch countDownLatch = new CountDownLatch(1);
    private final static CuratorFramework curatorFramework = ZookeeperSessionBuilder.buildClient1();
    private final static ZookeeperGetDemo watcher = new ZookeeperGetDemo();

    public static void main(String[] args) {
        try {
            get0();
            countDownLatch.await();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void get0() throws Exception {
        curatorFramework.start();
        byte[] bytes = curatorFramework.getData().usingWatcher(watcher).forPath("/zk-persistent_sequential-1");
        System.out.println(new String(bytes));

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged) {
            byte[] bytes = new byte[0];
            try {
                bytes = curatorFramework.getData().usingWatcher(watcher).forPath("/zk-persistent_sequential-1");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(new String(bytes));
        }
    }
}
