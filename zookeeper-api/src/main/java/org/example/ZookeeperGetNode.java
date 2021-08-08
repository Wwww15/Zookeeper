package org.example;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 创建节点
 *
 */
public class ZookeeperGetNode implements Watcher
{

    private static ZooKeeper zooKeeper;

    public static void main( String[] args ) throws IOException, InterruptedException {
        //建立会话
        zooKeeper = new ZooKeeper("192.168.0.106:2181", 5000,new ZookeeperGetNode());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 回调方法：处理来自服务器的事件通知回调
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {

        //是否子节点变更通知
        if(event.getType()== Event.EventType.NodeChildrenChanged) {
            List<String> childNode = null;
            try {
               childNode = getChildNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(childNode);
        }

        if(event.getState()==Event.KeeperState.SyncConnected)
        {
            System.out.println(event.getState());
            //获取节点
            try {
                getNode();
                getChildNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取节点
     */
    private void getNode() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/zk-persistent", false, null);
        System.out.println(new String(data));
    }

    /**
     * 获取子节点列表
     */
    public List<String>  getChildNode() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/zk-persistent", true, null);
        System.out.println(children);
        return children;
    }
}
