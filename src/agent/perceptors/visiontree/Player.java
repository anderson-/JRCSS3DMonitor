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

package agent.perceptors.visiontree;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import shared.tree.basenode.BaseNode;
import static java.lang.Math.*;

/**
 *
 * @author anderson
 */
public class Player extends BaseNode {

    public static final char[][] CONTENTS;

    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "head".toCharArray(),
            "rlowerarm".toCharArray(),
            "llowerarm".toCharArray(),
            "rfoot".toCharArray(),
            "lfoot".toCharArray()
        };
    }

    public Player() {
        super("PlayerLoc");
        double[] dVec = new double[]{0, 0, 0};
        headLocation = new ArrayRealVector(dVec);
        rightLowerArmLocation = new ArrayRealVector(dVec);
        leftLowerArmLocation = new ArrayRealVector(dVec);
        rightFootLocation = new ArrayRealVector(dVec);
        leftFootLocation = new ArrayRealVector(dVec);
    }
    private RealVector headLocation;
    private RealVector rightLowerArmLocation;
    private RealVector leftLowerArmLocation;
    private RealVector rightFootLocation;
    private RealVector leftFootLocation;

    public synchronized RealVector getHeadLocation() {
        return headLocation;
    }

    public synchronized RealVector getRightLowerArmLocation() {
        return rightLowerArmLocation;
    }

    public synchronized RealVector getLeftLowerArmLocation() {
        return leftLowerArmLocation;
    }

    public synchronized RealVector getRightFootLocation() {
        return rightFootLocation;
    }

    public synchronized RealVector getLeftFootLocation() {
        return leftFootLocation;
    }

    @Override
    public char[][] getContents() {
        return CONTENTS;
    }

    @Override
    public int getContentsLastFieldIndex() {
        return 5;
    }

    @Override
    protected synchronized void setField(int guess, int start, int end, char[] msg) {
        String[] str = new String(msg, start, (end - start)).split(" ");
        RealVector v = null;
        switch (guess) {
            case 1:
                v = headLocation;
                break;
            case 2:
                v = rightLowerArmLocation;
                break;
            case 3:
                v = leftLowerArmLocation;
                break;
            case 4:
                v = rightFootLocation;
                break;
            case 5:
                v = leftFootLocation;
                break;
        }

        if (v == null) {
            throw new UnsupportedOperationException("Invalid guess!");
        }
        
        double r = Double.parseDouble(str[1]);
        double a = Double.parseDouble(str[2]);
        double b = Double.parseDouble(str[3]);
        
        v.setEntry(0, r*sin(a)*cos(b));
        v.setEntry(1, r*sin(a)*sin(b));
        v.setEntry(2, r*cos(a));
        
//        for (int i = 0; i < 3; i++) {
//            v.setEntry(i, Double.parseDouble(str[i+1]));
//        }
    }

    @Override
    protected BaseNode createChild(int guess) {
        throw new UnsupportedOperationException("Invalid guess!");
    }

    @Override
    public void parseMessage() {
    }
}
