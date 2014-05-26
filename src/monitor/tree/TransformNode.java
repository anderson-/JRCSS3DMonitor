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
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author anderson
 */
public class TransformNode extends BaseNode {

    public static final char[][] CONTENTS;

    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "SLT".toCharArray(),
            "TRF".toCharArray(),
            "Light".toCharArray(),
            "StaticMesh".toCharArray(),
            "SMN".toCharArray(),};
    }
    RealMatrix matrix;

    public TransformNode() {
        super("TransformNode");
    }

    public RealMatrix getTrasformMatrix() {
        return matrix;
    }

    @Override
    public void parseMessage() {
    }

    @Override
    public String getInfo() {
        return "<" + matrix + ">";
    }

    @Override
    public char[][] getContents() {
        return CONTENTS;
    }

    @Override
    public int getContentsLastFieldIndex() {
        return 1;
    }

    @Override
    protected void setField(int guess, int start, int end, char[] msg) {
        if (guess == 1) {
            String[] values = new String(msg, start, (end - start)).split(" ");
            double[][] tmpMatrix = new double[4][4];

            for (int i = 0; i < 4; i++){
                tmpMatrix[i][0] = Double.parseDouble(values[i]);
                tmpMatrix[i][1] = Double.parseDouble(values[i+4]);
                tmpMatrix[i][2] = Double.parseDouble(values[i+8]);
                tmpMatrix[i][3] = Double.parseDouble(values[i+12]);
            }
            
            
//            tmpMatrix[0][0] = -Double.parseDouble(values[0]);
//            tmpMatrix[2][0] = Double.parseDouble(values[1]);
//            tmpMatrix[1][0] = Double.parseDouble(values[2]);
//
//            tmpMatrix[0][1] = Double.parseDouble(values[4]);
//            tmpMatrix[2][1] = Double.parseDouble(values[5]);
//            tmpMatrix[1][1] = Double.parseDouble(values[6]);
//
//            tmpMatrix[0][2] = Double.parseDouble(values[8]);
//            tmpMatrix[2][2] = Double.parseDouble(values[9]);
//            tmpMatrix[1][2] = Double.parseDouble(values[10]);
//
//            tmpMatrix[0][3] = Double.parseDouble(values[12]);
//            tmpMatrix[1][3] = Double.parseDouble(values[13]);
//            tmpMatrix[2][3] = Double.parseDouble(values[14]);
//
//            tmpMatrix[3][0] = 0;
//            tmpMatrix[3][1] = 0;
//            tmpMatrix[3][2] = 0;
//            tmpMatrix[3][3] = 1;
            
//            System.out.println("--");
//            System.out.println(values[10]);
//            System.out.println(values[5]);
//            System.out.println(values[0]int address = getAddress();
//            for (int i : tempNastyPrintNodes){
//                if (i == address){
//                    showNode = true;
//                    break;
//                }
//            }
//            );
//            System.out.println("--");
            
            double sum = 0;for (int i = 0; i < 4; i++){
                for (int j = 0; j < 4; j++){
                    sum += tmpMatrix[j][i];
                }
            }
            
            boolean showNode = false;
            
            if(this.getAddress() == 171){
                System.out.println("A: " + this.getAddress() + " SUM: " + sum);
            }
            
            if (sum < 0 && sum > -3){
                
                showNode = true;
            }
            
            if (showNode){
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        tmpMatrix[j][i] = 0;
                    }
                }
                tmpMatrix[0][0] = 1;
                tmpMatrix[1][1] = 1;
                tmpMatrix[2][2] = 1;
                tmpMatrix[3][3] = 1;
            }
            
            matrix = new Array2DRowRealMatrix(tmpMatrix);

//            for (int i = 0; i < 4; i++){
//                for (int j = 0; j < 4; j++){
//                    tmpMatrix[j][(3-i)] = Double.parseDouble(values[4*i+j]);
//                }
//            }
            

//            System.out.println(new String(msg, start, (end - start)) + " => " + matrix + " " + matrix.getEntry(0, 0) + " " + matrix.getEntry(3, 3));
        } else {
            throw new UnsupportedOperationException("Invalid guess!");
        }
    }

    @Override
    protected BaseNode createChild(int guess) {
        switch (guess) {
            case 2:
                return new TransformNode();
            case 3:
                return new LightNode();
            case 4:
                return new StaticMesh();
            case 5:
                return new StaticMeshNode();
            default:
                throw new UnsupportedOperationException("Invalid guess!");
        }
    }
}
