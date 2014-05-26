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
import gifAnimation.GifMaker;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import monitor.MonitorPerception;
import shared.tree.Node;
import shared.tree.basenode.BaseNode;
import monitor.tree.StaticMesh;
import monitor.tree.StaticMeshNode;
import monitor.tree.TransformNode;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 *
 * @author anderson
 */
public class Drawing3D extends PApplet {

    public MonitorPerception p;
    private GifMaker gifExport = null;

    public Drawing3D(MonitorPerception p) {
        this.p = p;
    }

    public void createFrame() {

        JFrame myFrame = new JFrame("Processing Applet");

        PApplet myApplet = this;
        myApplet.init();
        myFrame.getContentPane().add(myApplet);
        myFrame.pack();
        myFrame.setSize(640, 360);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        mouseDragged();
    }
    
    int posX = 0;
    int posY = 0;
    float atX = 0;
    float atY = 0;
    float atZ = 0;
    int atDist = 200;
    float upX = 0;
    float upY = 0;
    float eyeZ = 100;
    float theta = 250;
    int scale = 100;
    PImage fieldTexture;

    @Override
    public void setup() {
        size(640, 360, P3D);
        fieldTexture = loadImage("rcs-naofield2.png");
        buffer = createGraphics(width, height, P3D);
        textFont(createFont("Dialog.plain",50,true));
        
//        for (String s : PFont.list()){
//            System.out.println(s);
//        }
        
//        hint(DISABLE_DEPTH_TEST);
//        hint(DISABLE_OPENGL_ERRORS);
//        hint(DISABLE_STROKE_PERSPECTIVE);
        
    }

    @Override
    public void draw() {
//        image(buffer,0,0);

        background(140, 170, 255);
        //lights();

        pushMatrix();
        camera(0, 0, eyeZ, atX, atY, atZ, upX, upY, 0);
        translate(posX, posY);

        drawField(-18.33f * scale, -11.66f * scale, 36.66f * scale, 23.33f * scale, fieldTexture);

        drawTree(p);

        drawAxis(130, this);
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
        
        if (gifExport != null) {
            saveFrame("line-######.png");
            //gifExport.addFrame();
        }

//        PGraphics h = this.createGraphics(width, height);
//        h.beginDraw();
//        h.noStroke();
//        h.color(10, 0, 100);
//        h.rect(10, 10, 200, 200);
//        h.endDraw();
////        hint(DISABLE_DEPTH_TEST);
//        image(h, 0, 0);

//        hint(DISABLE_DEPTH_TEST);
//        noStroke();
//        color(0,0,255);
//        rect(10,10,200,200);

    }

    void drawField(float x, float y, float w, float h, PImage img) {

        beginShape();
        texture(img);
        vertex(x, y, 0, 0);
        vertex(x + w, y, img.width, 0);
        vertex(x + w, y + h, img.width, img.width);
        vertex(x, y + h, 0, img.width);
        endShape(CLOSE);

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
    
//    @Override
//    public void mouseWheelMoved(MouseWheelEvent mwe) {
//        System.out.println("sfd");
//    }
    
    private static final int DEFAULT_MOVE = 10;
    private final ArrayList<Integer> keys = new ArrayList<>();

    @Override
    public void keyReleased() {
        synchronized (keys) {
            keys.remove(new Integer(keyCode));
        }
    }

    @Override
    public void keyPressed() {
        if (keyCode == KeyEvent.VK_O) {
            if (gifExport == null) {
                gifExport = new GifMaker(this, "MyGig.gif", 5);
                System.out.println("start");
            } else {
                System.out.println("fim");
                gifExport.finish();
            }
        }
        synchronized (keys) {
            if (!keys.contains(keyCode)) {
                keys.add(keyCode);
            }
        }
    }

//    
//    public void drawTree (BaseNode n, RealVector v, RealVector v2){
//                
//        if (n instanceof TransformNode){
//            
//            v = ((TransformNode)n).getTrasformMatrix().operate(v);
//            v2 = ((TransformNode)n).getTrasformMatrix().operate(v2);
////            System.out.println(v);
//            pushMatrix();
//            fill(100);
//            stroke(155, 155, 0);
//            translate((float)(S*v.getEntry(0)),(float)(S*v.getEntry(1)),(float)(S*v.getEntry(2)));
//            box(2);
//            popMatrix();
//            
//            pushMatrix();
//            fill(100);
//            stroke(155, 155, 0);
//            translate((float)(S*v2.getEntry(0)),(float)(S*v2.getEntry(1)),(float)(S*v2.getEntry(2)));
//            box(2);
//            popMatrix();
//            
//            stroke(0, 0, 255);            
//            line((float)(S*v.getEntry(0)),(float)(S*v.getEntry(1)),(float)(S*v.getEntry(2)),(float)(S*v2.getEntry(0)),(float)(S*v2.getEntry(1)),(float)(S*v2.getEntry(2)));
//        }
//        
//        for (int a = n.getChildren().size()-1; a >= 0; a--){
//            drawTree(((ArrayList<BaseNode>)n.getChildren()).get(a),v,v2);
//        }
//        
//    }
    public void drawTree(MonitorPerception p) {
        synchronized (p) {
            drawTree(p.getSceneGraph(), new ArrayRealVector(new double[]{0, 0, 0, 1}));
        }
    }
    int selected = -1;
    float selectedTheta = 0;
    float selectedAlpha = 0;
    ArrayList<RealVector> path = new ArrayList<>();

    public void drawTree(BaseNode n, RealVector v) {

        Color c = Color.WHITE;

        if (n instanceof TransformNode) {
            //v = ((TransformNode) n).getTrasformMatrix().getColumnVector(3);
            v = ((TransformNode) n).getTrasformMatrix().operate(v);
            //stroke(0, 0, 255);
//            line((float) bef[0] * scale, (float) bef[1] * scale, (float) bef[2] * scale, (float) att[0] * scale, (float) att[1] * scale, (float) att[2] * scale);

            c = Color.BLACK;
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

//        if (n.getInfo().contains("naohead")){
//            c = Color.CYAN;
//            pushMatrix();
//            fill(c.getRed(), c.getGreen(), c.getBlue());
//
//            noStroke();
//            translate((float) (scale * v.getEntry(0)), (float) (-scale * v.getEntry(1)), (float) (scale * v.getEntry(2)));
//            box(50);
//            popMatrix();
//        }

        pushMatrix();
        fill(c.getRed(), c.getGreen(), c.getBlue());
        noStroke();
        translate((float) (scale * v.getEntry(0)), (float) (-scale * v.getEntry(1)), (float) (scale * v.getEntry(2)));
        box(0.05f * scale);
        popMatrix();




        if (n.getAddress() == selected) {
            path.add(v);
            pushMatrix();
            fill(0, 255, 255, selectedAlpha);
            selectedAlpha += (selectedAlpha >= 255) ? -255 : 10;
            noStroke();
            translate((float) (scale * v.getEntry(0)), (float) (-scale * v.getEntry(1)), (float) (scale * v.getEntry(2)));
            pushMatrix();
            //rotateX(t);
            rotateZ(selectedTheta);
            selectedTheta += 1 / 8f;
            box(0.1f*scale);
            popMatrix();
            // info
            rotateZ(PI/2);
            rotateX(-PI/2);
            translate(0.1f*scale,-0.1f*scale);
            fill(0);
            textSize(0.1f*scale);
            text(n.getInfo(), 0, 0, 0);
            
            popMatrix();

            RealVector ant = null;
            int o = 0;

            for (RealVector w : path) {
                if (ant != null) {
                    stroke(o, 255 - o, 0, 200);
//                    System.out.println(ant);
//                    System.out.println(w);
                    line((float) ant.getEntry(0) * scale, (float) ant.getEntry(1) * -scale, (float) ant.getEntry(2) * scale, (float) w.getEntry(0) * scale, (float) w.getEntry(1) * -scale, (float) w.getEntry(2));
                    o += (o >= 255) ? -200 : 10;
                }
                ant = w;

                pushMatrix();
                stroke(255, 255, 0, 200);
                translate((float) (scale * w.getEntry(0)), (float) (-scale * w.getEntry(1)), (float) (scale * w.getEntry(2)));

                box(2);
                popMatrix();
            }
        }

        for (int a = n.getChildren().size() - 1; a >= 0; a--) {
            drawTree(((ArrayList<BaseNode>) n.getChildren()).get(a), v);
        }

    }
    PGraphics buffer;

    @Override
    public void mouseClicked() {
        synchronized (p) {
            drawTree(p, buffer);
            int add = -buffer.get(mouseX, mouseY) - 2;
            Node n = p.getSceneGraph().search(add);
            if (n != null) {
                System.out.println(n.getInfo() + " n: " + add + "a:" + n.getAddress());
                selected = add;
            } else {
                System.out.println("Não foi possivel encontrar nodo " + add);
                selected = -1;
                path.clear();
            }
        }
    }

    public void drawTree(MonitorPerception p, PGraphics g) {
        g.beginDraw();
        g.background(0);
        g.noStroke();
        g.camera(0, 0, eyeZ, atX, atY, atZ, upX, upY, 0);
        g.translate(posX, posY);
        synchronized (p) {
            drawTree(p.getSceneGraph(), new ArrayRealVector(new double[]{0, 0, 0, 1}), g);
        }
        g.endDraw();
    }

    public void drawTree(BaseNode n, RealVector v, PGraphics g3) {

        if (n instanceof TransformNode) {
            //v = ((TransformNode) n).getTrasformMatrix().operate(v);
            v = ((TransformNode) n).getTrasformMatrix().getColumnVector(3);
        }

        int argb = -(n.getAddress() + 2);
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
        int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
        int b = argb & 0xFF;          // Faster way of getting blue(argb)
//        System.out.println("gravou: " + r + " " + g + " " + b + " " + a + " :: " + -(argb+2));

        g3.fill(r, g, b, a);

        g3.pushMatrix();
        g3.translate((float) (scale * v.getEntry(0)), (float) (-scale * v.getEntry(1)), (float) (scale * v.getEntry(2)));
        g3.box(0.05f * scale);
        g3.popMatrix();


        for (int i = n.getChildren().size() - 1; i >= 0; i--) {
            drawTree(((ArrayList<BaseNode>) n.getChildren()).get(i), v, g3);
        }

    }

//    public void drawww() {
//
//        RealVector origin = new ArrayRealVector(new double[]{1, 1, 1, 1});
//
//        Queue<BaseNode> stk = new LinkedList<>();
//
//        stk.offer(sg);
//
//        BaseNode it;
//
//        while (stk.peek() != null) {
//            it = stk.poll();
//
//            if (it instanceof TransformNode) {
//                origin = ((TransformNode) it).getTrasformMatrix().operate(origin);
////                System.out.println(origin);
//                pushMatrix();
//                fill(100);
//                stroke(155, 155, 0);
//                translate((float) origin.getEntry(0), (float) origin.getEntry(1), (float) origin.getEntry(2));
//                box(2);
//                popMatrix();
//            }
//
//            // ... 
//            for (int a = it.getChildren().size() - 1; a >= 0; a--) {
//                stk.offer(((ArrayList<BaseNode>) it.getChildren()).get(a));
//            }
//        }
//    }
    public static void drawAxis(int size, PApplet applet) {
        //eixo
        applet.stroke(255, 0, 0);//R
        applet.line(0, 0, 0, size, 0, 0);//X
        applet.stroke(0, 255, 0);//G
        applet.line(0, 0, 0, 0, -size, 0);//Y
        applet.stroke(0, 0, 255);//B
        applet.line(0, 0, 0, 0, 0, size);//Z

        applet.stroke(100);
        applet.pushMatrix();
        applet.translate(size, 0, 0);
        applet.rotateY(PI / 2);//X
        applet.fill(255, 0, 0);//R
        piramid(5, applet);
        applet.popMatrix();

        applet.pushMatrix();
        applet.translate(0, -size, 0);
        applet.rotateX(PI / 2);//Y
        applet.stroke(0, 255, 0);//G
        piramid(5, applet);
        applet.popMatrix();

        applet.pushMatrix();
        applet.translate(0, 0, size);
        //Z
        applet.stroke(0, 0, 255);//B
        piramid(5, applet);
        applet.popMatrix();
    }

    public static void piramid(int scale, PApplet applet) {
        applet.scale(scale);
        applet.beginShape(TRIANGLES);

        applet.vertex(0, 0, 1);
        applet.vertex(1, 1, -1);
        applet.vertex(1, -1, -1);

        applet.vertex(0, 0, 1);
        applet.vertex(1, -1, -1);
        applet.vertex(-1, -1, -1);

        applet.vertex(0, 0, 1);
        applet.vertex(-1, -1, -1);
        applet.vertex(-1, 1, -1);

        applet.vertex(0, 0, 1);
        applet.vertex(-1, 1, -1);
        applet.vertex(1, 1, -1);

        applet.endShape(CLOSE);
    }
}
