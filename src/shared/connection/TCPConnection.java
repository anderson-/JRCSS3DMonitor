/**
 * Copyright (C) 2012 Anderson de Oliveira Antunes <anderson.utf@gmail.com>
 *
 * This file is part of JRCSS3DMonitor.
 *
 * JRCSS3DMonitor is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JRCSS3DMonitor is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * JRCSS3DMonitor. If not, see http://www.gnu.org/licenses/.
 */

package shared.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import shared.observable.Observer;

/**
 *
 * @author anderson
 */
public class TCPConnection implements Connection {

    private static final String DEFAULT_SERVER_IP = "localhost";
    private static final int DEFAULT_MONITOR_PORT = 3200;
    private final String host;
    private final int port;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ArrayList<Observer<char[], int[]>> observers;
    private boolean shutdown;

    public TCPConnection() {
        this(DEFAULT_SERVER_IP, DEFAULT_MONITOR_PORT);
    }

    public TCPConnection(String host, int port) {
        this.host = host;
        this.port = port;
        shutdown = false;
        observers = new ArrayList<>();
    }

    @Override
    public boolean establishConnection() {
        try {
            socket = new Socket(host, port);
            socket.setTcpNoDelay(true);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public void sendMessage(String message) {
        //message = message + "(syn)";
        byte[] body = message.getBytes();

        int len = body.length;
        int byte0 = (len >> 24) & 0xFF;
        int byte1 = (len >> 16) & 0xFF;
        int byte2 = (len >> 8) & 0xFF;
        int byte3 = len & 0xFF;

        try {
            out.writeByte((byte) byte0);
            out.writeByte((byte) byte1);
            out.writeByte((byte) byte2);
            out.writeByte((byte) byte3);
            out.write(body);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error writing to socket, shuting down...");
            shutdown = true;
            closeConnection();
        }
    }

    @Override
    public String receiveMessage() {
        String msg;
        byte[] result;
        int length;

        try {
            int byte0 = in.read();
            int byte1 = in.read();
            int byte2 = in.read();
            int byte3 = in.read();
            length = byte0 << 24 | byte1 << 16 | byte2 << 8 | byte3; // analyzes

            int total = 0;

            if (length < 0) {
                return null;
            }

            result = new byte[length];
            while (total < length) {
                total += in.read(result, total, length - total);
            }

            msg = new String(result, 0, length, "UTF-8");
        } catch (IOException e) {
            System.out.println("Error reading from socket, shuting down...");
            shutdown = true;
            closeConnection();
            return null;
        }
        return msg;
    }

    @Override
    public void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        if (socket != null) {
            return !socket.isClosed();
        }
        return false;
    }

    @Override
    public void receiveLoop() {
        if (!isConnected()){
            return;
        }
        String tmp;
        while (!shutdown) {
//            long startTime = System.nanoTime();
            tmp = receiveMessage();
            if (tmp != null) {
                for (Observer<char[], int[]> o : observers) {
                    o.update(tmp.toCharArray(), new int[]{0, tmp.length()});
                }
            }
//            double timeElapsed = (System.nanoTime()-startTime)/1000000000.0;
//            System.out.println("Te:" + timeElapsed); //~ 0.4s
        }
        closeConnection();
        shutdown = false;
    }

    @Override
    public void stopReceiveLoop() {
        shutdown = true;
    }

    @Override
    public void attach(Observer<char[], int[]> observer) {
        observers.add(observer);
    }
}
