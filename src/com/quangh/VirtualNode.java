package com.quangh;

public class VirtualNode<T extends Node> implements Node {
    private T physicalNode;
    private int replicaIndex;

    public VirtualNode(T physicalNode, int replicaIndex) {
        this.replicaIndex = replicaIndex;
        this.physicalNode = physicalNode;
    }

    @Override
    public String getKey() {
        return physicalNode.getKey() + "-" + replicaIndex;
    }

    public T getPhysicalNode() {
        return physicalNode;
    }

    public boolean isVirtualNodeOf(T physicalNode) {
        return this.physicalNode.getKey().equals(physicalNode.getKey());
    }
}
