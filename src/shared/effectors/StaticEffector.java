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

package shared.effectors;

import shared.effectors.Effector;

/**
 *
 * @author anderson
 */
public class StaticEffector implements Effector<String>{

    private String message;
    private boolean newMessage = true;
    
    public StaticEffector (String format, Object ... data){
        message = cprintf(format, data);
    }
    
    public static String cprintf(String format, Object ... data){
        StringBuilder sb = new StringBuilder();
        boolean special = false;
        int it = 0;
        
        for (char c : format.toCharArray()){
            if (c == '%'){
                special = true;
            } else if (special){
                
                if (c == '%'){
                    sb.append(c);
                } else {
                    
                    if (it >= data.length){
                        throw new UnsupportedOperationException("Out of bounds!");
                    }
                    
                    switch (c){
                        case 'd':
                        case 'i':
                            if (data[it] instanceof Integer){
                                sb.append(((Integer)data[it]).toString());
                            }
                            break;
                        case 'f':
                            if (data[it] instanceof Float){
                                sb.append(((Float)data[it]).toString());
                            }
                            if (data[it] instanceof Double){
                                sb.append(((Double)data[it]).toString());
                            }
                            break;
                        case 'c':
                            if (data[it] instanceof Character){
                                sb.append(((Character)data[it]).toString());
                            }
                            break;
                        case 's':
                            if (data[it] instanceof String){
                                sb.append(((String)data[it]).toString());
                            }
                            break;
                        default:
                            throw new UnsupportedOperationException("Invalid specifier!");
                    }
                    it++;
                }
                
                special = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean hasMessage() {
        return newMessage;
    }
    
    public void reset(){
        newMessage = true;
    }
}
