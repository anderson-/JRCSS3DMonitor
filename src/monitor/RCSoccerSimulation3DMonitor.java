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

package monitor;

import shared.processing.DrawingPanel3D;
import shared.connection.Connection;
import shared.connection.FakeConnection;
import shared.connection.TCPConnection;
import java.awt.Color;
import monitor.processing.RSGDrawingTool;
import shared.tree.basenode.BaseNode;
import monitor.tree.StaticMesh;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PGraphics3D;
import shared.processing.graph.QuickGraph;
import shared.processing.simplegraphics.Axis;
import shared.processing.tools.ObjectPicker;

/**
 *
 * @author anderson
 */
public class RCSoccerSimulation3DMonitor extends DrawingPanel3D {

    public static void main(String[] args) {

//        double[][] tmpMatrix = new double [][] {
//            {1,0,0,1},
//            {0,2,0,2},
//            {0,0,1,4},
//            {0,0,0,1}
//        };
//        
//        RealMatrix m = new Array2DRowRealMatrix(tmpMatrix);
//        RealVector v = new ArrayRealVector(new double[]{1, 2, 3, 1});
//        v = m.operate(v);
//        
//        System.out.println(v);

        boolean fakeConnection = true; //UNCOMMENT TO WORK
        Connection connection;

        if (fakeConnection) {
            connection = new FakeConnection("Monitor3.txt", false);
        } else {
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        Process rcssserver3d = Runtime.getRuntime().exec("rcssserver3d");
//                        Thread.sleep(5000);
//                        Process rcssagent3d = Runtime.getRuntime().exec("rcssagent3d");
//                        Thread.sleep(10000);
//                        rcssserver3d.destroy();
//                        rcssagent3d.destroy();
//                        System.exit(0);
//                    } catch (Exception ex) {
//                        System.out.println(ex);
//                    }
//
//                }
//            }.start();

            connection = new TCPConnection();
        }

        MonitorPerception perception = new MonitorPerception();
        connection.attach(perception);
        RCSoccerSimulation3DMonitor rcss3d = new RCSoccerSimulation3DMonitor(perception);
        rcss3d.createFrame("Robocup Soccer Simulation 3D Monitor");
        rcss3d.append(new Axis(0, 0, 0));

        if (connection.establishConnection()) {
            connection.sendMessage("(playMode PlayOn)");
            connection.receiveLoop();
            perception.getSceneGraph().print();
        }

//        System.exit(0);
    }
    private final MonitorPerception perception;
    private PImage fieldTexture;
    private RSGDrawingTool rsgDefaultDrawer;
    private ObjectPicker<BaseNode> picker;
    private RSGDrawingTool pickerBufferDrawer;
    private PGraphics3D pickerBuffer;
    private ObjectPicker.Selectable<BaseNode> pickerSource;
    String[] tempNastyTags = {"goal", "ball", "head", "body", "foot"};

    public RCSoccerSimulation3DMonitor(MonitorPerception perception) {
        super(800, 600);
        this.perception = perception;
        rsgDefaultDrawer = new RSGDrawingTool(perception) {
            @Override
            public void drawNode(BaseNode n, PGraphics g3d) {
                boolean printNodeInfo = false;
                if (n instanceof StaticMesh) {
                    StaticMesh sm = (StaticMesh) n;
                    for (String s : tempNastyTags) {
                        if (sm.getInfo().contains(s)) {
                            printNodeInfo = true;
                            break;
                        }
                    }
                }

                for (BaseNode bn : picker) {
                    if (bn.equals(n)) {
                        printNodeInfo = true;
                        break;
                    }
                }

                if (printNodeInfo) {
                    g3d.pushMatrix();
                    g3d.translate(0.05f, -0.05f, 0.05f);
                    g3d.fill(0);
                    g3d.scale(DrawingPanel3D.RESET_SCALE/10);
                    g3d.textSize(50);
                    g3d.text(n.getAddress() + " " + n.getInfo(), 0, 0, 0);
                    g3d.popMatrix();
                }

                //g3d.fill(Color.cyan.getRGB());
                g3d.fill(Color.HSBtoRGB(1f / 300 * n.getAddress(), 1, 1));

                for (BaseNode bn : picker) {
                    if (bn.equals(n)) {
                        g3d.fill(Color.red.getRGB());
                    }
                }
                super.drawNode(n, g3d);
            }
        };

        pickerBufferDrawer = new RSGDrawingTool(perception) {
            @Override
            public void drawNode(BaseNode n, PGraphics g3d) {
                g3d.fill(picker.getColor(n.getAddress()));
                super.drawNode(n, g3d);
            }
        };

        pickerSource = new ObjectPicker.Selectable<BaseNode>() {
            @Override
            public BaseNode select(int index) {
                synchronized (RCSoccerSimulation3DMonitor.this.perception) {
                    return (BaseNode) RCSoccerSimulation3DMonitor.this.perception.getSceneGraph().search(index);
                }
            }
        };
        
        append(new QuickGraph() {

            @Override
            public void draw(PGraphics3D g3d) {
                
            }

            @Override
            public void draw(PGraphics g2d) {
                
            }
        });

    }

    @Override
    public void setup(Scene3D scene3D) {
        super.setup(scene3D);
        fieldTexture = scene3D.loadImage("rcs-naofield2.png");
//        for (String s : PFont.list()) {
//            System.out.println(s);
//        }
        scene3D.textFont(scene3D.createFont("Ubuntu Light", 50, true));
        pickerBuffer = (PGraphics3D) scene3D.createGraphics(scene3D.width, scene3D.height, PApplet.P3D);
        picker = new ObjectPicker<>(pickerBuffer, pickerSource);
    }

    @Override
    public void draw(PGraphics3D g3d) {
        g3d.background(140, 170, 255);
        drawField(-18.33f, -11.66f, 36.66f, 23.33f, fieldTexture, g3d);
        rsgDefaultDrawer.drawTree(g3d);
        rsgDefaultDrawer.drawWeb(g3d);
    }

    @Override
    public void draw(PGraphics g2d) {
//        super.draw2D(g2d);
        g2d.clear();
//        g2d.fill(1, 0, 0);
//        g2d.rect(10, 10, 90, 30);
//        g2d.stroke(255);
        g2d.fill(255);
        g2d.textSize(20);
        g2d.text(perception.getEnvironmentInformation().getTime() + "", 15, 30);
    }

    @Override
    public void mouseClicked(PApplet applet) {
        pickerBuffer.beginDraw();
        pickerBuffer.background(Color.black.getRGB());
        scene3D.applyCameraTransform(pickerBuffer);
        pickerBuffer.scale(scale);
        pickerBufferDrawer.drawTree(pickerBuffer);
        pickerBuffer.endDraw();
        picker.select(applet.mouseX, applet.mouseY);
    }

    @Override
    public void keyTyped(PApplet applet) {
    }

    public static void sendServerCMD(int keyCode, Connection c) {
    }

    public static void drawField(float x, float y, float w, float h, PImage img, PGraphics g3d) {
        g3d.stroke(Color.GREEN.getRGB());
        g3d.beginShape();
        g3d.texture(img);
        g3d.vertex(x, y, 0, 0);
        g3d.vertex(x + w, y, img.width, 0);
        g3d.vertex(x + w, y + h, img.width, img.width);
        g3d.vertex(x, y + h, 0, img.width);
        g3d.endShape(PApplet.CLOSE);
    }
}
