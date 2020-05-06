package com.quangh;

import com.quangh.impl.MD5;
import com.quangh.impl.MyNode;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MyNode node1 = new MyNode("127.0.0.1", 8080);
        MyNode node2 = new MyNode("127.0.0.2", 8080);
        MyNode node3 = new MyNode("127.0.0.3", 8080);
        MyNode node4 = new MyNode("127.0.0.4", 8080);

        List nodes = Arrays.asList(node1, node2, node3, node4);
        HashFunction md5 = new MD5();
        ConsistentHash<MyNode> consistentHash = new ConsistentHash<>(md5, nodes, 5);
        consistentHash.print();

        String requestIP1 = "192.168.0.1";
        String requestIP2 = "192.168.0.2";
        String requestIP3 = "192.168.0.3";
        String requestIP4 = "192.168.0.4";
        String requestIP5 = "192.168.0.5";

        routeRequest(consistentHash, requestIP1);
        routeRequest(consistentHash, requestIP2);
        routeRequest(consistentHash, requestIP3);
        routeRequest(consistentHash, requestIP4);
        routeRequest(consistentHash, requestIP5);

        consistentHash.removeNode(node4);
        routeRequest(consistentHash, requestIP1);
    }

    private static void routeRequest(ConsistentHash<MyNode> consistentHash, String requestIp) {
        System.out.println(requestIp + " is routed to " + consistentHash.route(requestIp).getKey());
    }
}
