package org.example;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * 创建节点
 *
 */
public class ZookeeperUpdateNode implements Watcher
{

    private static ZooKeeper zooKeeper;

    public static void main( String[] args ) throws IOException, InterruptedException {
        //建立会话
        zooKeeper = new ZooKeeper("192.168.0.106:2181", 5000,new ZookeeperUpdateNode());
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
            //更新节点
            try {
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
        //修改前
        //修改
        //修改后
    }
}
