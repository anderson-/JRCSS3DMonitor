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

package monitor.perceptors;

import shared.tree.basenode.BaseNode;
import monitor.tree.TransformNode;

/**
 *
 * @author anderson
 */
public class SceneGraph extends BaseNode{

    public static final char[][] CONTENTS;
    
    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "RSG".toCharArray(),
            "RDS".toCharArray(),
            "TRF".toCharArray()
        };
    }
    
    public SceneGraph() {
        super("SceneGraph");
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
        return 2;
    }

    @Override
    protected void setField(int guess, int start, int end, char[] msg) {
        if (guess == 1){
            //newTree
            this.clear();
            update = false;
        } else if (guess == 2) {
            //update
            update = true;
        } else {
            throw new UnsupportedOperationException("Invalid guess!");
        }
    }

    @Override
    public void update(char[] msg, int[] info) {
        super.update(msg, info);
        if (!update){
            
        }
    }

    @Override
    protected BaseNode createChild(int guess) {
        if (guess == 3){
            return new TransformNode();
        }
        throw new UnsupportedOperationException("Invalid guess!");
    }

}
