package org.example;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 创建节点
 */
public class ZookeeperUpdateNode implements Watcher {

    private static ZooKeeper zooKeeper;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        //建立会话
        zooKeeper = new ZooKeeper("192.168.119.142:2181", 100000, new ZookeeperUpdateNode());
        countDownLatch.await(100000, TimeUnit.MILLISECONDS);
    }

    /**
     * 回调方法：处理来自服务器的事件通知回调
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {

        if (event.getType() == Event.EventType.NodeDataChanged) {
            System.out.println(event.getType());
            //根据事件获取节点
            try {
                getNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (event.getState() == Event.KeeperState.SyncConnected) {
            System.out.println(event.getState());
            //更新节点
            try {
                getNode();
                updateNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新节点
     */
    private void updateNode() throws KeeperException, InterruptedException {
        //修改
        zooKeeper.setData("/zk-persistent","持久节点修改6".getBytes(),6);
    }

    private void getNode() throws InterruptedException, KeeperException {
        //修改前
        byte[] data = zooKeeper.getData("/zk-persistent", true, null);
        System.out.println("获取到的数据：" + new String(data));
    }
}
