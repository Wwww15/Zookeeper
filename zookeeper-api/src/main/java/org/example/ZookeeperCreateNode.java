package org.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 创建节点
 *
 */
public class ZookeeperCreateNode implements Watcher
{

    private static ZooKeeper zooKeeper;

    public static void main( String[] args ) throws IOException, InterruptedException {
        //建立会话
        zooKeeper = new ZooKeeper("192.168.119.142:2181", 5000,new ZookeeperCreateNode());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 回调方法：处理来自服务器的事件通知回调
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        if(event.getState()==Event.KeeperState.SyncConnected)
        {
            System.out.println(event.getState());
            //创建节点
            try {
                createNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点
     */
    private void createNode() throws KeeperException, InterruptedException {
        //持久节点
        String persistent = zooKeeper.create("/zk-persistent", "持久节点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("persistent:"+persistent);
        //临时节点
        String ephemeral = zooKeeper.create("/zk-ephemeral","临时节点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
        System.out.println("ephemeral:"+ephemeral);
        //持久顺序节点
        String persistent_sequential = zooKeeper.create("/zk-persistent_sequential","持久顺序节点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("persistent_sequential:"+persistent_sequential);
        //持久顺序节点创建回调，自己根据返回code处理，如果存在导致没创建上，需要自己处理
        zooKeeper.create("/zk-persistent_sequential-1", "持久顺序节点回调".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL, new AsyncCallback.Create2Callback() {
            @Override
            public void processResult(int i, String s, Object o, String s1, Stat stat) {
                System.out.println(1);
            }
        },new Object());
    }
}
