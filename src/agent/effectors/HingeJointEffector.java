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

package agent.effectors;

import shared.effectors.Effector;

/**
 *
 * @author anderson
 */
public class HingeJointEffector implements Effector<String>{

    private String name;
    private double speed;
    private boolean newMessage = true;
    
    public HingeJointEffector (String name){
        this.name = name;
    }
    
    public void setSpeed (double speed){
        this.speed = speed;
    }
    
    @Override
    public String getMessage() {
        return "(" + name + " " + speed + ")";
    }
    
    @Override
    public boolean hasMessage() {
        return newMessage;
    }

}