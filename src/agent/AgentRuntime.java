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

package agent;

import processing.opengl.PGraphics3D;
import shared.connection.Connection;
import shared.connection.FakeConnection;
import shared.connection.TCPConnection;
import shared.processing.DrawingPanel3D;

/**
 *
 * @author anderson
 */
public class AgentRuntime {
    
    public static void main (String [] args){
        
        boolean fakeConnection = true;
        Connection connection;

        if (fakeConnection) {
            connection = new FakeConnection("agent.txt",true);
        } else {
            connection = new TCPConnection();
        }

        Perception perception = new Perception();
        connection.attach(perception);
        
        DrawingPanel3D p = new DrawingPanel3D(800,600){

            @Override
            public void draw(PGraphics3D g3d) {
                g3d.background(140, 170, 255);
            }
            
        };
        p.createFrame("hello");
        p.append(perception.getVisionPerceptor());

        if (connection.establishConnection()) {
            connection.sendMessage("(playMode PlayOn)");
            connection.receiveLoop();
        }
        
        System.exit(0);
    }
    
}
