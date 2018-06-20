package com.example.yuyu.wlbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

class MySocket extends Socket {
    private static MySocket socket = null;
    private static final String host = "120.79.237.210";
    private static final int port = 5555;

    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;

    private static InputStream ins = null;
    private static OutputStream ous = null;
    static MySocket getInstance() throws IOException {
        if (socket == null) {
            socket = new MySocket(host, port);
        }
        return socket;
    }

    private MySocket(String host, int port) throws IOException {
        super(host,port);
    }

    public static ObjectInputStream getOis() throws IOException {
        if (ois == null) {
            ois = new ObjectInputStream(socket.getInputStream());
        }
        return ois;
    }

    public static ObjectOutputStream getOos() throws IOException {
        if (oos==null) {
            oos = new ObjectOutputStream(socket.getOutputStream());
        }
        return oos;
    }

    public static InputStream getIns() throws IOException {
        if (ins==null) {
            ins = socket.getInputStream();
        }
        return ins;
    }

    public static OutputStream getOus() throws IOException {
        if (ous==null) {
            ous = socket.getOutputStream();
        }
        return ous;
    }
}
