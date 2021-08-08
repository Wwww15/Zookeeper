package org.example;

import org.I0Itec.zkclient.ZkClient;

/**
 * 删除节点
 *
 */
public class DeleteNode
{
    public static void main( String[] args )
    {
        //创建连接和会话
        ZkClient zkClient = new ZkClient("192.168.0.106:2181");
        //创建节点
        zkClient.createPersistent("/zk-client/zk-child-client/zk-client1",true);
        //删除节点
    }
}
