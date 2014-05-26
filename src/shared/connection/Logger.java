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

package shared.connection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import shared.observable.Observer;

/**
 *
 * @author anderson
 */
public class Logger implements Observer<char [],int []>{

    private File outputFile;
    private BufferedWriter out;
    
    public Logger (String outputFileLoc){
        int i = 0;
        do {
            if (outputFileLoc.contains(".")){
                outputFileLoc = outputFileLoc.substring(0, outputFileLoc.indexOf("."));
            }
            if (i > 0){
                outputFileLoc += ".";
                outputFileLoc += i;
            }
            outputFileLoc += ".log";
            outputFile = new File(outputFileLoc);
            i++;
        } while (outputFile.exists());
        
        try {
            out = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void update(char[] msg, int[] info) {
        try {
            out.write(msg, info[0], info[1]);
            out.append("\n");
        } catch (IOException ex) {
            
        }
    }
    
    public void close (){
        try {
            out.close();
        } catch (IOException ex) {
            
        }
    }
    
}
