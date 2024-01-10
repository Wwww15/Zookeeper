package org.example;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 建立会话
 */
public class ZookeeperSession implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        //建立会话
        ZooKeeper zooKeeper = new ZooKeeper("192.168.119.142:2181", 5000, new ZookeeperSession());
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
    }

    /**
     * 回调方法：处理来自服务器的事件通知回调
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            System.out.println(event.getState());
            countDownLatch.countDown();
        }
    }
}
