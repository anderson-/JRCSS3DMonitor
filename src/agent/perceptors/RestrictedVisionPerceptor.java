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

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import shared.tree.basenode.BaseNode;
import static java.lang.Math.*;
import processing.opengl.PGraphics3D;
import shared.processing.graph.Graph3D;
import shared.processing.simplegraphics.Axis;

/**
 *
 * @author anderson
 */
public class RestrictedVisionPerceptor extends BaseNode implements Graph3D{

    public static final char[][] CONTENTS;

    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "P".toCharArray(),
            "L".toCharArray(),
            "B".toCharArray(),
            "F1L".toCharArray(),
            "F1R".toCharArray(),
            "F2L".toCharArray(),
            "F2R".toCharArray(),
            "G1L".toCharArray(),
            "G1R".toCharArray(),
            "G2L".toCharArray(),
            "G2R".toCharArray()
        };
    }
    private RealVector ball;
    private RealVector flag1L;
    private RealVector flag1R;
    private RealVector flag2L;
    private RealVector flag2R;
    private RealVector goalPost1L;
    private RealVector goalPost1R;
    private RealVector goalPost2L;
    private RealVector goalPost2R;

    public RestrictedVisionPerceptor() {
        super("RestrictedVisionPerceptor");
        double[] dVec = new double[]{0, 0, 0};
        ball = new ArrayRealVector(dVec);
        flag1L = new ArrayRealVector(dVec);
        flag1R = new ArrayRealVector(dVec);
        flag2L = new ArrayRealVector(dVec);
        flag2R = new ArrayRealVector(dVec);
        goalPost1L = new ArrayRealVector(dVec);
        goalPost1R = new ArrayRealVector(dVec);
        goalPost2L = new ArrayRealVector(dVec);
        goalPost2R = new ArrayRealVector(dVec);
    }

    @Override
    public char[][] getContents() {
        return CONTENTS;
    }

    @Override
    public int getContentsLastFieldIndex() {
        return 11;
    }

    @Override
    protected synchronized void setField(int guess, int start, int end, char[] msg) {
        String[] str = new String(msg, start, (end - start-1)).split(" ");
        RealVector v = null;
        switch (guess) {
            case 1:
                
                return;
            case 2:
                
                return;
            case 3:
                v = ball;
                break;
            case 4:
                v = flag1L;
                break;
            case 5:
                v = flag1R;
                break;
            case 6:
                v = flag2L;
                break;
            case 7:
                v = flag2R;
                break;
            case 8:
                v = goalPost1L;
                break;
            case 9:
                v = goalPost1R;
                break;
            case 10:
                v = goalPost2L;
                break;
            case 11:
                v = goalPost2R;
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

    public synchronized RealVector getBall() {
        return ball;
    }

    public synchronized RealVector getFlag1L() {
        return flag1L;
    }

    public synchronized RealVector getFlag1R() {
        return flag1R;
    }

    public synchronized RealVector getFlag2L() {
        return flag2L;
    }

    public synchronized RealVector getFlag2R() {
        return flag2R;
    }

    public synchronized RealVector getGoalPost1L() {
        return goalPost1L;
    }

    public synchronized RealVector getGoalPost1R() {
        return goalPost1R;
    }

    public synchronized RealVector getGoalPost2L() {
        return goalPost2L;
    }

    public synchronized RealVector getGoalPost2R() {
        return goalPost2R;
    }

    @Override
    public void draw(PGraphics3D g3d) {
        Axis.drawAxis(100, g3d);
        g3d.pushMatrix();
        g3d.fill(255,100,0);
        g3d.translate(100*(float)ball.getEntry(0),100*(float)ball.getEntry(1),100*(float)ball.getEntry(2));
        g3d.box(5);
        g3d.popMatrix();
    }

//    @Override
//    public void draw(PGraphics g2d) {
//        
//    }
    
}
