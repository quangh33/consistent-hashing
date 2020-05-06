package com.quangh;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ConsistentHash<T extends Node> {
    private TreeMap<Long, VirtualNode<T>> ring = new TreeMap<>();
    private HashFunction hashFunction;
    private int numberOfVirtualNode;

    public ConsistentHash(HashFunction hashFunction, Collection<T> pNodes, int numberOfVirtualNodePerPhysicalNode) {
        this.hashFunction = hashFunction;
        this.numberOfVirtualNode = numberOfVirtualNodePerPhysicalNode;
        for (T pNode : pNodes) {
            addNode(pNode);
        }
    }

    public void addNode(T newNode) {
        for (int i = 0; i < numberOfVirtualNode; i++) {
            VirtualNode<T> vNode = new VirtualNode<>(newNode, i);
            ring.put(hashFunction.hash(vNode.getKey()), vNode);
        }
    }

    public void removeNode(T node) {
        Iterator<Long> it = ring.keySet().iterator();
        while (it.hasNext()) {
            Long key = it.next();
            VirtualNode<T> virtualNode = ring.get(key);
            if (virtualNode.isVirtualNodeOf(node)) {
                it.remove();
            }
        }
    }

    public T route(String requestKey) {
        if (ring.isEmpty()) {
            return null;
        }
        Long requestKeyHash = hashFunction.hash(requestKey);
        // find the least key greater than or equal to requestKeyHash
        Long nodeKey = ring.ceilingKey(requestKeyHash);
        System.out.println("request key hash: " + requestKeyHash + " --> " + nodeKey);

        if (nodeKey == null) {
            return ring.firstEntry().getValue().getPhysicalNode();
        }
        return ring.get(nodeKey).getPhysicalNode();
    }

    public void print() {
        for(Map.Entry<Long, VirtualNode<T>> entry: ring.entrySet()) {
            Long key = entry.getKey();
            System.out.println(key + " " + entry.getValue().getPhysicalNode().getKey());
        }
    }
}
