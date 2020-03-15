/*
package com.taotao.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

*/
/**
 * @author 张剑锋
 * @date 2020/2/29 - 15:31
 *
 * Jedis测试
 *//*

public class TestJedis {

    @Test
    public void testJedis() throws Exception {
        //创建一个jedis对象，需要指定服务的id和端口
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //直接操作数据库
        jedis.set("zhang","1234");
        String result = jedis.get("zhang");
        System.out.println(result);
        System.out.println(jedis);
        //关闭jedis
        jedis.close();
    }

    */
/*@Test
    public void testJedisCluster() throws Exception {
        //创建一个JedisCluster对象，构造参数Set类型，集合中每个元素是HostAndPort类型
        Set<HostAndPort> nodes = new HashSet<>();
        //向集合中添加节点
        nodes.add(new HostAndPort("192.168.25.128",7001));
        nodes.add(new HostAndPort("192.168.25.128",7002));
        nodes.add(new HostAndPort("192.168.25.128",7003));
        nodes.add(new HostAndPort("192.168.25.128",7004));
        nodes.add(new HostAndPort("192.168.25.128",7005));
        nodes.add(new HostAndPort("192.168.25.128",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        System.out.println(jedisCluster);
        //使用JedisCluster操作Redis，自带连接池。JedisCluster可以是单例的
        jedisCluster.set("cluster-test","hello jedis cluster");
        String result = jedisCluster.get("cluster-test");
        System.out.println(result);
        //系统关闭前，关闭JedisCluster
        jedisCluster.close();
    }*//*


}
*/
