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

import com.jogamp.newt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 *
 * @author anderson
 */
public class SceneP3D extends PApplet {

    private int w = 100;
    private int h = 100;
    private int posX = 0;
    private int posY = 0;
    private float atX = 0;
    private float atY = 0;
    private float atZ = 0;
    private int atDist = 200;
    private float upX = 0;
    private float upY = 0;
    private float eyeZ = 100;
    private float theta = 250;
    int scale = 100;
    private static final int DEFAULT_MOVE = 10;
    private final ArrayList<Integer> keys = new ArrayList<>();

    protected SceneP3D() {
    }

    public void createFrame(int w, int h) {

        this.w = w;
        this.h = h;

        JFrame myFrame = new JFrame("Processing Applet");

        PApplet myApplet = this;
        myApplet.init();
        myFrame.getContentPane().add(myApplet);
        myFrame.pack();
        myFrame.setSize(w, h);//frame.setSize(teste.getSize());
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        mouseDragged();
    }
    
    @Override
    public void setup() {
        size(w, h, P3D);
    }

    @Override
    public void draw() {
        background(140, 170, 255);
        //lights();

        pushMatrix();
        camera(0, 0, eyeZ, atX, atY, atZ, upX, upY, 0);
        translate(posX, posY);
        
        //draw here
        
        popMatrix();

        synchronized (keys) {
            for (int code : keys) {
                switch (code) {
                    case KeyEvent.VK_S:
                    case DOWN:
                        posX -= DEFAULT_MOVE * upX;
                        posY -= DEFAULT_MOVE * upY;
                        break;
                    case KeyEvent.VK_W:
                    case UP:
                        posX += DEFAULT_MOVE * upX;
                        posY += DEFAULT_MOVE * upY;
                        break;
                    case KeyEvent.VK_D:
                    case RIGHT:
                        posX -= DEFAULT_MOVE * upY;
                        posY += DEFAULT_MOVE * upX;
                        break;
                    case KeyEvent.VK_A:
                    case LEFT:
                        posX += DEFAULT_MOVE * upY;
                        posY -= DEFAULT_MOVE * upX;
                        break;
                    case KeyEvent.VK_SPACE:
                    case KeyEvent.VK_PAGE_UP:
                        eyeZ += DEFAULT_MOVE;
                        atZ += DEFAULT_MOVE;
                        break;
                    case KeyEvent.VK_SHIFT:
                    case KeyEvent.VK_PAGE_DOWN:
                        if (eyeZ - DEFAULT_MOVE > 80) {
                            eyeZ -= DEFAULT_MOVE;
                            atZ -= DEFAULT_MOVE;
                        }
                        break;
                    case KeyEvent.VK_Q:
                        scale++;
                        break;
                    case KeyEvent.VK_E:
                        scale--;
                        break;
                }
            }
        }
    }

    @Override
    public void mousePressed() {
        if (mouseButton == RIGHT) {
            synchronized (keys) {
                if (!keys.contains(new Integer(KeyEvent.VK_PAGE_UP))) {
                    keys.add(new Integer(KeyEvent.VK_PAGE_UP));
                }
            }
            eyeZ += DEFAULT_MOVE;
        }
    }

    @Override
    public void mouseReleased() {
        if (mouseButton == RIGHT) {
            synchronized (keys) {
                keys.remove(new Integer(KeyEvent.VK_PAGE_UP));
            }
        }
    }

    @Override
    public void mouseDragged() {

        // Z
        int dy = (pmouseY - mouseY);
        atZ += (atZ + dy >= eyeZ) ? 0 : dy;

        // X e Y
        theta += (pmouseX - mouseX);

        float c = 2 * PI * theta / 1000;

        atX = atDist * sin(c);
        atY = atDist * cos(c);

        upX = -sin(c);
        upY = -cos(c);
    }

    @Override
    public void keyReleased() {
        synchronized (keys) {
            keys.remove(new Integer(keyCode));
        }
    }

    @Override
    public void keyPressed() {
        synchronized (keys) {
            if (!keys.contains(keyCode)) {
                keys.add(keyCode);
            }
        }
    }
    
    public static void drawAxis(int size, PGraphics g) {
        //eixo
        g.stroke(255, 0, 0);//R
        g.line(0, 0, 0, size, 0, 0);//X
        g.stroke(0, 255, 0);//G
        g.line(0, 0, 0, 0, -size, 0);//Y
        g.stroke(0, 0, 255);//B
        g.line(0, 0, 0, 0, 0, size);//Z

        g.stroke(100);
        g.pushMatrix();
        g.translate(size, 0, 0);
        g.rotateY(PI / 2);//X
        g.fill(255, 0, 0);//R
        piramid(5, g);
        g.popMatrix();

        g.pushMatrix();
        g.translate(0, -size, 0);
        g.rotateX(PI / 2);//Y
        g.stroke(0, 255, 0);//G
        piramid(5, g);
        g.popMatrix();

        g.pushMatrix();
        g.translate(0, 0, size);
        //Z
        g.stroke(0, 0, 255);//B
        piramid(5, g);
        g.popMatrix();
    }

    public static void piramid(int scale, PGraphics g) {
        g.scale(scale);
        g.beginShape(TRIANGLES);

        g.vertex(0, 0, 1);
        g.vertex(1, 1, -1);
        g.vertex(1, -1, -1);

        g.vertex(0, 0, 1);
        g.vertex(1, -1, -1);
        g.vertex(-1, -1, -1);

        g.vertex(0, 0, 1);
        g.vertex(-1, -1, -1);
        g.vertex(-1, 1, -1);

        g.vertex(0, 0, 1);
        g.vertex(-1, 1, -1);
        g.vertex(1, 1, -1);

        g.endShape(CLOSE);
    }
}
