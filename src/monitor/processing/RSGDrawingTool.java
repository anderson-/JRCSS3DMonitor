/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor.processing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import monitor.MonitorPerception;
import shared.tree.basenode.BaseNode;
import monitor.tree.StaticMesh;
import monitor.tree.StaticMeshNode;
import monitor.tree.TransformNode;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import processing.core.PGraphics;
import processing.opengl.PGraphics3D;

/**
 *
 * @author anderson
 */
public class RSGDrawingTool {

    private final MonitorPerception perception;

    public RSGDrawingTool(MonitorPerception perception) {
        this.perception = perception;
    }

    public void drawNode(BaseNode n, PGraphics g3d) {
        g3d.pushMatrix();
        g3d.noStroke();
        g3d.box(0.05f);
        g3d.popMatrix();
    }

    public void drawTree(PGraphics g3d) {
        System.out.println("--- START ---");
        synchronized (perception) {
            drawTree(perception.getSceneGraph(), new ArrayRealVector(new double[]{0, 0, 0, 1}), g3d);
        }
        System.out.println("--- END ---");
    }
    
    int [] tempNastyPrintNodes = new int [] {60,162,170,171,172};
    
    private void drawTree(BaseNode n, RealVector v, PGraphics g3d) {

        boolean showNode = false;
        int address = n.getAddress();
        for (int i : tempNastyPrintNodes){
            if (i == address){
                showNode = true;
                break;
            }
        }
        
        if (showNode){
            System.out.println("--- node: " + address);
            System.out.println("info: " + n.getInfo());
            System.out.println("posBefore: " + v);
        }
        
        if (n instanceof TransformNode) {
            //v = ((TransformNode) n).getTrasformMatrix().getColumnVector(3);
            v = ((TransformNode) n).getTrasformMatrix().operate(v);
            
            if (showNode){
                System.out.println("Transf: " + ((TransformNode) n).getTrasformMatrix());
            }
            //stroke(0, 0, 255);
//            line((float) bef[0] * scale, (float) bef[1] * scale, (float) bef[2] * scale, (float) att[0] * scale, (float) att[1] * scale, (float) att[2] * scale);
        }
        
        if (showNode){
            System.out.println("posAfter: " + v);
        }
//        g3d.stroke(Color.HSBtoRGB(1f/300*n.getAddress(),1,1));
//        drawVector(v,g3d);

        g3d.pushMatrix();
        g3d.translate((float) v.getEntry(0), (float) v.getEntry(1), (float) v.getEntry(2));
        drawNode(n, g3d);
        g3d.popMatrix();
        
        for (BaseNode bn : n.getChildren()){
            drawTree(bn,v.copy(),g3d);
        }
        
//        for (int a = n.getChildren().size() - 1; a >= 0; a--) {
//            drawTree(((ArrayList<BaseNode>) n.getChildren()).get(a), v.copy(), scale, g3d);
//        }

    }

    public void drawWeb(PGraphics g3d) {
//        g3d.stroke(Color.red.getRGB());
//        synchronized (perception) {
//            BaseNode it = perception.getSceneGraph();
//            
//            RealVector v1 = new ArrayRealVector(new double[]{0, 0, 0, 1});
//            RealVector v2 = new ArrayRealVector(new double[]{0, 0, 0, 1});
//            
//            Stack<BaseNode> stkNode = new Stack();
//            Stack<RealVector> stkVector = new Stack();
//            stkNode.push(it);
//            try{
//            while (!stkNode.isEmpty()) {
//                stkNode.push(null);
//                for (BaseNode bn : it.getChildren()){
//                    stkNode.push(bn);
//                }
//                
//                if (it instanceof TransforRESET_SCALEmNode){
//                    stkVector.add(v1.copy());
//                    //v2 = ((TransformNode) it).getTrasformMatrix().getColumnVector(3);
//                    v2 = ((TransformNode)iRESET_SCALEt).getTrasformMatrix().operate(v1);
//                    drawVector(v1,v2,scale,g3d);
//                }
//                
//                it = stkNode.pop();
//                while (it == null && !stkVector.empty()){
//                    v1 = stkVector.pop();
//                    it = stkNode.pop();
//                }
//            }
//            } catch (Exception e){
//                0
//            }
//        }
    }

    public void drawVector(RealVector v, PGraphics g3d) {
        if (!g3d.stroke){
            g3d.stroke(255);
        }
        g3d.line(0, 0, 0, (float) v.getEntry(0),
                (float) v.getEntry(1),
                (float) v.getEntry(2));
    }

    public void drawVector(RealVector v1, RealVector v2, PGraphics g3d) {
        if (!g3d.stroke){
            g3d.stroke(255);
        }
        g3d.line((float) v1.getEntry(0),
                (float) v1.getEntry(1),
                (float) v1.getEntry(2),
                (float) v2.getEntry(0),
                (float) -v2.getEntry(1),
                (float) v2.getEntry(2));
    }
}
