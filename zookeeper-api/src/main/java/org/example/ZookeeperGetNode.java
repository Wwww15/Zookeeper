package org.example;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 创建节点
 *
 */
public class ZookeeperGetNode implements Watcher
{

    private static ZooKeeper zooKeeper;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main( String[] args ) throws IOException, InterruptedException {
        //建立会话
        zooKeeper = new ZooKeeper("192.168.119.142:2181", 1000000,new ZookeeperGetNode());
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
    }

    /**
     * 回调方法：处理来自服务器的事件通知回调
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {

        //异步正式连接
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

        //是否子节点变更通知
        if(event.getType()== Event.EventType.NodeChildrenChanged) {
            List<String> childNode = null;
            try {
               childNode = getChildNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(childNode);
//            countDownLatch.countDown();
        }

        //节点数据变更
        if(event.getType()==Event.EventType.NodeDataChanged){
            System.out.println(event.getType());
        }

    }

    /**
     * 获取节点
     */
    private void getNode() throws KeeperException, InterruptedException {
        //watch 为 true 表示对数据进行监听
        byte[] data = zooKeeper.getData("/zk-persistent", true, null);
        System.out.println(new String(data));
    }

    /**
     * 获取子节点列表
     */
    public List<String>  getChildNode() throws KeeperException, InterruptedException {
        //watch 为 true 表示对当前节点的子节点进行监听
        List<String> children = zooKeeper.getChildren("/zk-persistent/zk-persistent-1", true, null);
        System.out.println(children);
        return children;
    }
}
