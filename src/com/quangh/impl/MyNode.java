package com.quangh.impl;

import com.quangh.Node;

public class MyNode implements Node {
    private String ip;
    private int port;

    public MyNode(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override public String getKey() {
        return this.port + ":" + this.ip;
    }
}
