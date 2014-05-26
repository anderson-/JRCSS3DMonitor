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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import shared.observable.Observer;

/**
 *
 * @author anderson
 */
public class FakeConnection implements Connection {
    
    public static final int DELAY = 30;
    
    private File inputFile;
    private File outputFile;
    private BufferedReader in;
    private BufferedWriter out;
    private ArrayList<Observer<char[], int[]>> observers;
    private boolean shutdown;
    private boolean loop = true;
    
    public FakeConnection (String inputFileLoc, boolean loop){
        inputFile = new File(inputFileLoc);
        observers = new ArrayList<>();
        outputFile = null;
        this.loop = loop;
    }
    
    public FakeConnection (String inputFileLoc, String outputFileLoc, boolean loop){
        this.loop = loop;
        int i = 0;
        do {
            if (outputFileLoc.contains(".")){
                outputFileLoc = outputFileLoc.substring(0, outputFileLoc.indexOf("."));
            }
            if (i > 0){
                outputFileLoc += ".";
                outputFileLoc += i;
            }
            outputFileLoc += ".log";
            outputFile = new File(outputFileLoc);
            i++;
        } while (outputFile.exists());
        
    }

    @Override
    public boolean establishConnection() {
        try {
            in = new BufferedReader(new FileReader(inputFile));
            if (outputFile != null){
                out = new BufferedWriter(new FileWriter(outputFile));
            }
            return true;
        } catch (Exception ex) {
            
        }
        return false;
    }

    @Override
    public void sendMessage(String message) {
        try {
            if (outputFile != null){
                out.write(message, 0, message.length());
            }
        } catch (IOException ex) {
            
        }
    }

    @Override
    public String receiveMessage() {
        String msg = "";
        
        try {
            msg = in.readLine();
        } catch (IOException ex) {
            
        }
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException ex) {
            
        }
        
        return msg;
    }

    @Override
    public void closeConnection() {
        try {
            in.close();
            if (outputFile != null){
                out.close();
            }
        } catch (IOException ex) {
            
        }
    }

    @Override
    public boolean isConnected() {
        try {
            return in.ready();
        } catch (IOException ex) {
            
        }
        return false;
    }

    @Override
    public void receiveLoop() {
        String tmp;
        while (!shutdown) {
            
            tmp = receiveMessage();
            if (tmp != null && !tmp.isEmpty()) {
                for (Observer<char[], int[]> o : observers) {
                    o.update(tmp.toCharArray(), new int[]{0, tmp.length()});
                }
            } else {
                if (loop){
                    establishConnection();
                } else {
                    shutdown = true;
                }
            }
            
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
