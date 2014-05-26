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

package monitor.tree;

import shared.tree.basenode.BaseNode;

/**
 *
 * @author anderson
 */
public class LightNode extends BaseNode{

    public static final char[][] CONTENTS;
    
    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "setDiffuse".toCharArray(),
            "setAmbient".toCharArray(),
            "setSpecular".toCharArray()
        };
    }
    
    private String diffuse = "";
    private String ambient = "";
    private String specular = "";
    
    public LightNode() {
        super("LightNode");
    }
    
    @Override
    public void parseMessage() {
        
    }
    
    @Override
    public String getInfo (){
        return "< d: " + diffuse + ", a: " + ambient + ", s: " + specular + ">";
    }

    @Override
    public char[][] getContents() {
        return CONTENTS;
    }

    @Override
    public int getContentsLastFieldIndex() {
        return 3;
    }

    @Override
    protected void setField(int guess, int start, int end, char[] msg) {
        String str = new String(msg, start, (end - start));
        
        switch(guess){
            case 1:
                diffuse = str;
                break;
            case 2:
                ambient = str;
                break;
            case 3:
                specular = str;
                break;
            default:
                throw new UnsupportedOperationException("Invalid guess!");
        }
    }

    @Override
    protected BaseNode createChild(int guess) {
        throw new UnsupportedOperationException("Invalid guess!");
    }

}
