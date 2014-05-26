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

package shared.tree.basenode;

import shared.tree.Node;
import shared.perceptors.Perceptor;

/**
 *
 * @author anderson
 */
public abstract class BaseNode extends Node<BaseNode> implements Perceptor<char[], int[]> {

    protected static boolean update = false;

    public BaseNode(String name) {
        super(name);
    }

    public abstract char[][] getContents();

    public abstract int getContentsLastFieldIndex();

    protected abstract void setField(int guess, int start, int end, final char[] msg);

    protected abstract BaseNode createChild(int guess);

    @Override
    public void update(final char[] msg, final int[] info) {
        char[][] CONTENTS = getContents();
        int lastFieldIndex = getContentsLastFieldIndex();
        int open = 0;           //parenteses abertos
        int close = 0;          //grupo de parenteses () fechados
        int guess = -1;         //não sabe que campo está lendo
        int start = 0;          //inicio da sub arvore
        int end = 0;            //fim da sub arvore
        int childIt = 0;        //contador de filhos

//        System.out.print("[");
//        for (int i = info[0]; i < info[1]; i++) {
//            System.out.print(msg[i]);
//        }
//        System.out.println("]");

        for (int i = info[0]; i < info[1]; i++) {
            
            if (msg[i] == '(') {
                open++;
            } else if (msg[i] == ')') {
                open--;
                close++;
                if (guess != -1 && open == end) {
                    end = i;
                    // agora você tem o tipo, inicio e o fim da sub arvore

                    if (guess == 0) {
                        if (update) {
                            if (--start > end) {
                                start = 0;
                                end = 0;
                            }
                            getChildren().get(childIt).update(msg, new int[]{start, end});
                            childIt++;
                        } else {
                            throw new IllegalStateException("Node ID equals nd!");
                        }
                    } else if (guess <= lastFieldIndex) {
                        if (start > end) {
                            start = 0;
                            end = 0;
                        }
                        
                        setField(guess, start, end, msg);
                    } else {
                        BaseNode child = createChild(guess);
                        if (child != null) {
                            super.addChild(child);
                        } else {
                            System.out.println("null");
                            continue;
                        }
                        child.update(msg, new int[]{start, end});
                    }

                    start = 0;
                    end = 0;
                    guess = -1;
                }
            } else {
                if (guess != -1) {
                    if (guess == 0 && !update) { // é um filho, ignorar
                        start = 0;
                        end = 0;
                        guess = -1;
                    }
                    // percorre tentando achar o fim (open == end)
                } else {
                    // tenta encontrar o identificador
                    
                    if (msg[i] == ' '){
                        // se for espaço, anda mais um pouco
                        continue;
                    }
                    
                    guess = -1;
                    for (int j = 0; j < CONTENTS.length; j++) {
                        int k = 0;
                        for (; k < CONTENTS[j].length && k < info[1]; k++) {
                            if (msg[i+k] == CONTENTS[j][k]) {
                                guess = j;
                            } else {
                                guess = -1;
                                break;
                            }
                        }
                        
                        if (guess != -1 && k == CONTENTS[j].length && " ()".indexOf(msg[i + k]) != -1) {
                            i += --k;
                            break;
                        }
                    }

                    if (guess == -1 && !update) {
                        throw new IllegalStateException("ID not found! "+ this + " " + msg[i] + " " + (i-info[0]));
                    } else {
                        start = i + 2;  // define o inicio dos dados
                        // define o ponto de parada usando end como
                        // uma variavel temporaria
                        end = open - 1;
                    }
                }

            }
        }

        if (open > 0) {
            throw new IllegalStateException("Parentesis not match!");
        }
    }
    
//    
//    @Override
//    public final void update(final char[] msg, final int[] info) {
//        char[][] CONTENTS = getContents();
//        int lastFieldIndex = getContentsLastFieldIndex();
//        int open = 0;           //parenteses abertos
//        int close = 0;          //grupo de parenteses () fechados
//        int guess = -1;         //não sabe que campo está lendo
//        int guesses = 0;        //não sabe quais podem ser os campos que está lendo
//        int fieldIt = 0;        //contador para fields
//        int start = 0;          //inicio da sub arvore
//        int end = 0;            //fim da sub arvore
//        int childIt = 0;        //contador de filhos
//
////        System.out.print("[");
////        for (int i = info[0]; i < info[1]; i++) {
////            System.out.print(msg[i]);
////        }
////        System.out.println("]");
//
//        for (int i = info[0]; i < info[1]; i++) {
//            System.out.print(msg[i]);         
//            if (msg[i] == '(') {
//                open++;
//                if (guesses != -1) { //se você estiver tentando adivinhar, esquece? / acho que é desnecessario
//                    guess = -1;
//                    fieldIt = 0;
//                    guesses = 0;
//                } else if (update){
//                    // (nd(
////                    childIt--;
//                }
//            } else if (msg[i] == ')') {
//                open--;
//                close++;
////                System.out.println("open: " + open + " close: " + close);
//                if (guesses == -1 && open == end) {
//                    end = i;
//                    // agora você tem o tipo, inicio e o fim da sub arvore
//                    
//                    if (guess == 0){
//                        if (update){
//                            // (nd)
//                            start--;
//                            if (start > end){
//                                start = 0;
//                                end = 0;
//                            }
//                            //String str = new String(msg, start, (end - start));
//                            //System.out.println("get: " + childIt + " info: [" +str + "]");
//                            
//                            getChildren().get(childIt).update(msg, new int [] {start,end});
//                            
//                            childIt++;
//                        } else {
//                            throw new IllegalStateException("Node ID equals nd!");
//                        }
//                    } else if (guess <= lastFieldIndex){
//                        if (start > end){
//                            start = 0;
//                            end = 0;
//                        }
//                        setField(guess, start, end, msg);
//
//                    } else {
//                        BaseNode child = createChild(guess);
//                        if (child != null){
//                            super.addChild(child);
//                        } else {
//                            System.out.println("null");
//                            continue;
//                        }
//                        child.update(msg, new int[]{start, end});
////                        System.out.println(getChildren().size() + " " + childIt);
//                    }
//                    
//                    start = 0;
//                    end = 0;
//                    guess = -1;
//                    guesses = 0;
//                }
//            } else {
//                if (guesses == -1) { //sabe qual campo é
//                    if (guess == 0 && !update) { // é um filho, ignorar
//                        start = 0;
//                        end = 0;
//                        guess = -1;
//                        fieldIt = 0;
//                        guesses = 0;
//                    }
//                    // percorre tentando achar o fim (open == end)
//                } else {
//                    if (guesses == 1) {
//                        // percorre até o fim do identificador do campo
//                        // se chegar até o fim do identificador
//                        if (CONTENTS[guess].length - 1 == fieldIt) {
//
//                            if (start == 0 && msg[i] == ' ') { //?????
//                                continue;
//                            } else {
//                                start = i + 2;  // define o inicio dos dados
//                                // define o ponto de parada usando end como
//                                // uma variavel temporaria
//                                end = open - 1; 
////                                System.out.println("fecha em: " + end);
//                            }
//
//                            // define que o identificador já foi reconhecido e
//                            // já está pronto para a interpretação dos dados
//                            guesses = -1;
//                        }
//                    } else {
//                        // verifica quais podem ser os campos
//                        guesses = 0;
//                        
//                        for (int f = 0; f < CONTENTS.length; f++) {
//                            if (fieldIt < CONTENTS[f].length && msg[i] == CONTENTS[f][fieldIt]) {
//                                guess = f;
//                                guesses++;
//                                System.out.print("*");
//                            }
//                        }
//                        if (guesses == 0 && msg[i] != ' ' && !update) {
//                            throw new IllegalStateException("ID not found!" + msg[i]);
//                        }
//                    }
//                    fieldIt++;
//                }
//            }
//            System.out.println("");
//        }
//
//        if (open > 0) {
//            throw new IllegalStateException("Parentesis not match!");
//        }
//    }
}
