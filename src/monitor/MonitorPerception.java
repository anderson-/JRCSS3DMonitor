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

package monitor;

import monitor.perceptors.EnvironmentInformation;
import monitor.perceptors.SceneGraph;
import shared.observable.Observer;

/**
 *
 * @author anderson
 */
public class MonitorPerception implements Observer<char[],int[]>{

    EnvironmentInformation ei;
    SceneGraph sg;
    
    public MonitorPerception (){
        ei = new EnvironmentInformation();
        sg = new SceneGraph();
    }
    
    @Override
    public synchronized void update(char[] msg, int[] info) {
        int temp = 0;
        
        for (int i = info[0]; i < info[1]; i++){
            if (msg[i] == '(') {
                temp++;
            } else if (msg[i] == ')') {
                temp--;
                if (temp == 0){
                    temp = i;
                    break;
                }
            }
        }
        
        ei.update(msg, new int[] {0,temp+1});
        sg.update(msg, new int[] {temp+1,info[1]});
        
//        String [] playModes = ei.getPlay_modes().split(" ");
//        int playMode = ei.getPlay_mode();
//        System.out.println("Time: " + ei.getTime() + " PlayMode: " + playModes[playMode]);
//        sg.print();
    }
    
    public synchronized EnvironmentInformation getEnvironmentInformation(){
        return ei;
    }
    
    public synchronized SceneGraph getSceneGraph(){
        return sg;
    }
    
}
