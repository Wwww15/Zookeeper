package org.example;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 创建节点
 *
 */
public class ZookeeperDeleteNode implements Watcher
{

    private static ZooKeeper zooKeeper;

    public static void main( String[] args ) throws IOException, InterruptedException {
        //建立会话
        zooKeeper = new ZooKeeper("192.168.0.106:2181", 5000,new ZookeeperDeleteNode());
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
            //删除节点
            try {
                deleteNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除节点
     */
    private void deleteNode() throws KeeperException, InterruptedException {
        //判断节点是否存在
        //删除
        //判断节点是否存在
    }
}
