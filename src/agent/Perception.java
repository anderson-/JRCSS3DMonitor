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

import agent.perceptors.RestrictedVisionPerceptor;
import shared.tree.basenode.BaseNode;

/**
 *
 * @author anderson
 */
public class Perception extends BaseNode {
    
    public static final char[][] CONTENTS;
    
    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "time".toCharArray(),
            "GYR".toCharArray(),
            "HJ".toCharArray(),
            "FRP".toCharArray(),
            "ACC".toCharArray(),
            "See".toCharArray(),
            "GS".toCharArray(),
            "AgentState".toCharArray(),
            "hear".toCharArray()
        };
    }
    
    private RestrictedVisionPerceptor vision;

    public RestrictedVisionPerceptor getVisionPerceptor() {
        return vision;
    }
    
    public Perception() {
        super("Perception");
        update = true;
        
        vision = new RestrictedVisionPerceptor();
    }
    
    @Override
    public void parseMessage() {
        
    }
    
    @Override
    public String getInfo (){
        return "<no info avaliable>";
    }

    @Override
    public char[][] getContents() {
        return CONTENTS;
    }

    @Override
    public int getContentsLastFieldIndex() {
        return 9;
    }

    @Override
    protected void setField(int guess, int start, int end, char[] msg) {
//        System.out.println(new String(CONTENTS[guess]) + " : " + new String(msg,start,(end - start)));
        switch (guess){
            case 1:
                //time
                
                break;
            case 2:
                //GYR
                
                break;
            case 3:
                //HJ
                
                break;
            case 4:
                //FRP
                
                break;
            case 5:
                //ACC
                
                break;
            case 6:
                //See
                vision.update(msg, new int [] {start,end});
                break;
            case 7:
                //GS
                
                break;
            case 8:
                //AgentState
                
                break;
            case 9:
                //hear
                
            default:
                throw new UnsupportedOperationException("Invalid guess!");
        }
    }

    @Override
    public void update(char[] msg, int[] info) {
        super.update(msg, info);
    }

    @Override
    protected BaseNode createChild(int guess) {
        throw new UnsupportedOperationException("Invalid guess!");
    }
}
