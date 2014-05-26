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
public class StaticMesh extends BaseNode {

    public static final char[][] CONTENTS;

    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "load".toCharArray(),
            "sSc".toCharArray(),
            "setVisible".toCharArray(),
            "setTransparent".toCharArray(),
            "resetMaterials".toCharArray()
        };
    }
    
    private String model;
    private String scale;
    private boolean visible;
    private boolean transparent = false;
    private String materials;

    public StaticMesh() {
        super("StaticMesh");
    }

    @Override
    public void parseMessage() {
    }

    @Override
    public String getInfo() {
        return "<"+model+","+scale+","+visible+","+transparent+","+materials+">";
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
    protected void setField(int guess, int start, int end, char[] msg) {
        String str = new String(msg, start, (end - start));
        
        switch (guess) {
            case 1:
                //load
                model = str;
                break;
            case 2:
                //sSc
                scale = str;
                break;
            case 3:
                //setVisible
                visible = str.equals("1");
                break;
            case 4:
                //setTransparent
                transparent = true;
                break;
            case 5:
                //resetMaterials
                materials = str;
                break;
            default:
                throw new UnsupportedOperationException("Invalid guess!");
        }
    }
    
    @Override
    protected BaseNode createChild(int guess) {
        throw new UnsupportedOperationException("Invalid guess!");
    }
    
    

//    public void AgentUpdate(char[] msg, int[] info) {
//        int open = 0;
//        int close = 0;
//        int guess = -1;   //não sabe que campo está lendo
//        int guesses = 0;  //não sabe quais podem ser os campos que está lendo
//        int fieldIt = 0;
//        StringBuilder buffer = new StringBuilder();
//        for (int i = info[0]; i < info[1]; i++) {
//            if (msg[i] == '(') {
//                open++;
//                guess = -1;
//                fieldIt = 0;
//                guesses = 0; //não sabe quais podem ser os campos que está lendo
//            } else if (msg[i] == ')') {
//                if (guesses == -1) {
//                    if (buffer.length() > 1) {
//                        String tmp = buffer.deleteCharAt(0).toString();
//                        setField(guess, tmp);
//                    } else {
//                        setField(guess, "");
//                    }
//                    buffer = new StringBuilder();
//                    guesses = 0;
//                }
//                open--;
//                close++;
//            } else {
//                if (guesses == -1) { //sabe qual campo é
//
//                    buffer.append(msg[i]);
//                } else {
//                    if (guesses == 1) {
//                        if (LEAFS[guess].length - 1 == fieldIt) {
//                            guesses = -1; //já leu e sabe qual campo é
//                        }
//                    } else {
//                        // verifica quais podem ser os campos
//                        guesses = 0;
//                        for (int f = 0; f < LEAFS.length; f++) {
//                            if (fieldIt < LEAFS[f].length && msg[i] == LEAFS[f][fieldIt]) {
//                                guess = f;
//                                guesses++;
//                            }
//                        }
//                        if (guesses == 0 && msg[i] != ' ') {
//                            throw new IllegalStateException("Wrong String?");
//                        }
//                    }
//                    fieldIt++;
//                }
//            }
//        }
//
//        if (open != 0) {
//            throw new IllegalStateException("parentesis not match");
//        }
//
//    }

    public String getModel() {
        return model;
    }

    public String getScale() {
        return scale;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public String getMaterials() {
        return materials;
    }

}
