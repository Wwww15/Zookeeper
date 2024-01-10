package org.example;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 创建节点
 */
public class ZookeeperDeleteNode implements Watcher {

    private static ZooKeeper zooKeeper;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        //建立会话
        zooKeeper = new ZooKeeper("192.168.119.142:2181", 100000, new ZookeeperDeleteNode());
        System.out.println(zooKeeper.getState());
        countDownLatch.await(100000, TimeUnit.MILLISECONDS);
    }

    /**
     * 回调方法：处理来自服务器的事件通知回调
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected && event.getType() != Event.EventType.NodeDeleted) {
            System.out.println(event.getState());
            //删除节点
            try {
                getNode();
                deleteNode();
                getNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除节点
     */
    private void deleteNode() throws KeeperException, InterruptedException {
        //删除
        zooKeeper.delete("/zk-persistent",7);
        System.out.println("删除/zk-persistent");
    }

    private void getNode() throws InterruptedException, KeeperException {
        Stat stat = zooKeeper.exists("/zk-persistent", true);
        if(Objects.isNull(stat)) {
            System.out.println("/zk-persistent已删除");
            return;
        }
        System.out.println(stat.getCversion());
        System.out.println(stat.getCtime());
    }
}
