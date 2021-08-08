package org.example;

import org.I0Itec.zkclient.ZkClient;

/**
 * 创建会话
 *
 */
public class CreateNode
{
    public static void main( String[] args )
    {
        //创建连接和会话
        ZkClient zkClient = new ZkClient("192.168.0.106:2181");
        //创建节点
        zkClient.createPersistent("/zk-client/zk-child-client",true);
    }
}
