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

package monitor.processing.OLD;

import java.awt.Color;
import java.util.ArrayList;
import monitor.MonitorPerception;
import shared.tree.basenode.BaseNode;
import monitor.tree.StaticMesh;
import monitor.tree.StaticMeshNode;
import monitor.tree.TransformNode;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import processing.core.PImage;

/**
 *
 * @author anderson
 */
public class SoccerField extends SceneP3D {
    
    private PImage fieldTexture;
    private MonitorPerception perception;
    int scale = 100;
    
    public SoccerField (MonitorPerception perception){
        this.perception = perception;
        fieldTexture = loadImage("rcs-naofield2.png");
    }
    
    @Override
    public void draw(){
        super.draw();
        
        drawField(-1833, -1166, 3666, 2333, fieldTexture);
    }
    
    void drawField(int x, int y, int w, int h, PImage img) {

        beginShape();
        texture(img);
        vertex(x, y, 0, 0);
        vertex(x + w, y, img.width, 0);
        vertex(x + w, y + h, img.width, img.width);
        vertex(x, y + h, 0, img.width);
        endShape();

    }
    
    public void drawTree(MonitorPerception p) {
        synchronized (p) {
            drawTree(p.getSceneGraph(), new ArrayRealVector(new double[]{0, 0, 0, 1}));
        }
    }

    public void drawTree(BaseNode n, RealVector v) {

        Color c = Color.WHITE;

        if (n instanceof TransformNode) {
            v = ((TransformNode) n).getTrasformMatrix().operate(v);

            c = Color.ORANGE;
        } else if (n instanceof StaticMesh) {
            StaticMesh sm = (StaticMesh) n;

            if (sm.isTransparent()) {
                c = Color.BLUE;
            } else if (sm.isVisible()) {
//                System.out.println(n.getInfo());
//                System.out.println(v);
                c = Color.RED;
                if (sm.getModel().contains("naohead") || sm.getModel().contains("naobody")) {
                    c = Color.PINK;
                }
            } else {
                c = Color.CYAN;
            }

        } else if (n instanceof StaticMeshNode) {
            StaticMeshNode smn = (StaticMeshNode) n;

            if (smn.isTransparent()) {
                c = Color.GREEN;
//                cameraSceneX = (float)v.getEntry(0);
//                cameraSceneY = (float)v.getEntry(1);
//                cameraSceneZ = (float)v.getEntry(2);
            } else if (smn.isVisible()) {
                c = Color.YELLOW;
            } else {
                c = Color.MAGENTA;
            }

        }

        pushMatrix();
        fill(100);

//        c = Color.getHSBColor(n.getAddress()*0.05f, 1, 1);
        stroke(c.getRed(), c.getGreen(), c.getBlue());
        translate((float) (scale * v.getEntry(0)), (float) (-scale * v.getEntry(1)), (float) (scale * v.getEntry(2)));
        box(5);
        popMatrix();

        for (int a = n.getChildren().size() - 1; a >= 0; a--) {
            drawTree(((ArrayList<BaseNode>) n.getChildren()).get(a), v);
        }

    }
}
