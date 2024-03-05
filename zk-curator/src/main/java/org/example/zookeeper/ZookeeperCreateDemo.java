package org.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.concurrent.CountDownLatch;

public class ZookeeperCreateDemo {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            create0();
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个基于 test 目录的持久节点 /curator/1
     * 如果是创建临时节点，则父级为持久节点
     *
     * creatingParentsIfNeeded 帮助创建父类节点
     *
     * @throws Exception
     */
    public static void create0() throws Exception {
        CuratorFramework curatorFramework = ZookeeperSessionBuilder.buildClient2();
        curatorFramework.start();
        String path = getPath();
        String content = "curator2";
        String resultPath = curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath(path, content.getBytes());
        System.out.println("创建结果：" + resultPath);
    }

    public static String getPath() {
        return "/curator2/2";
    }
}
