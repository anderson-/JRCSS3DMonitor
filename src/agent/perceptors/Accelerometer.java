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

package agent.perceptors;

import shared.tree.basenode.BaseNode;

/**
 *
 * @author anderson
 */
public class Accelerometer  extends BaseNode {
    
    public static final char[][] CONTENTS;
    
    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "n".toCharArray(),
            "rt".toCharArray()
        };
    }
    
    public Accelerometer(){
        super("Accelerometer");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected BaseNode createChild(int guess) {
        throw new UnsupportedOperationException("Invalid guess!");
    }

    @Override
    public void parseMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
