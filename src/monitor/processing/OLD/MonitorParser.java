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

import shared.connection.Connection;
import shared.connection.FakeConnection;
import java.lang.reflect.Field;
import monitor.MonitorPerception;
import shared.tree.Node;
import monitor.perceptors.EnvironmentInformation;
import monitor.processing.OLD.Drawing3D;

/**
 *
 * @author anderson
 */
public class MonitorParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
//        new Thread(){
//            @Override
//            public void run (){
//                try {
//                    Process rcssserver3d = Runtime.getRuntime().exec("rcssserver3d");
//                    Thread.sleep(10000);
//                    rcssserver3d.destroy();
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//                
//            }
//        }.start();
        
        Connection c = new FakeConnection("Monitor3.txt",false);
//        Connection c = new TCPConnection();
        MonitorPerception p = new MonitorPerception();
//        Logger l = new Logger("teste");
        
        c.attach(p);
//        c.attach(l);
        
        Drawing3D applet = new Drawing3D(p);
        applet.createFrame();
        
        if (c.establishConnection()){
            c.receiveLoop();
            p.getSceneGraph().print();
            System.exit(0);
        } else {
            System.exit(0);
        }
        
//        String msg = "((FieldLength 30)(FieldWidth 20)(FieldHeight 40)(GoalWidth 2.1)(GoalDepth 0.6)(GoalHeight 0.8)(BorderSize 0)(FreeKickDistance 2)(WaitBeforeKickOff 30)(AgentRadius 0.4)(BallRadius 0.042)(BallMass 0.026)(RuleGoalPauseTime 3)(RuleKickInPauseTime 1)(RuleHalfTime 300)(play_modes BeforeKickOff KickOff_Left KickOff_Right PlayOn KickIn_Left KickIn_Right corner_kick_left corner_kick_right goal_kick_left goal_kick_right offside_left offside_right GameOver Goal_Left Goal_Right free_kick_left free_kick_right)(time 0)(team_left magmaOffenburg)(half 1)(score_left 0)(score_right 0)(play_mode 0))(RSG 0 1)((nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 -10 10 10 1)(nd Light (setDiffuse 1 1 1 1) (setAmbient 0.8 0.8 0.8 1) (setSpecular 0.1 0.1 0.1 1)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 10 -10 10 1)(nd Light (setDiffuse 1 1 1 1) (setAmbient 0 0 0 1) (setSpecular 0.1 0.1 0.1 1)))(nd TRF (SLT -1 -8.74228e-08 -3.82137e-15 0 0 -4.37114e-08 1 0 -8.74228e-08 1 4.37114e-08 -0 0 0 0 1)(nd StaticMesh (setVisible 1) (load models/naosoccerfield.obj) (sSc 2.5 1 2.5)(resetMaterials None_rcs-naofield.png)))(nd TRF (SLT -1 -8.74228e-08 -3.82137e-15 0 0 -4.37114e-08 1 0 -8.74228e-08 1 4.37114e-08 -0 0 0 0 1)(nd StaticMesh (setVisible 1) (load models/skybox.obj) (sSc 10 10 10)(resetMaterials Material_skyrender_0001.tif Material_skyrender_0002.tif Material_skyrender_0003.tif Material_skyrender_0004.tif Material_skyrender_0005.tif Material_skyrender_0006.tif)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 -15.3 0 0.4 1)(nd TRF (SLT -4.37114e-08 1 4.37114e-08 0 0 -4.37114e-08 1 0 1 4.37114e-08 1.91069e-15 0 0.3 0 -0.4 1)(nd StaticMesh (setVisible 1) (setTransparent) (load models/leftgoal.obj) (sSc 2.18 0.88 0.68)(resetMaterials grey_naogoalnet.png yellow)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 -1.07 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 1.07 0 1))(nd TRF (SLT 0.866025 0 -0.5 0 0 1 0 0 0.5 0 0.866025 0 -0.06 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0.3 1.05 0.4 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0.3 -1.05 0.4 1)))(nd TRF (SLT -1 -8.74228e-08 -0 -0 8.74228e-08 -1 0 0 0 0 1 0 15.3 0 0.4 1)(nd TRF (SLT -4.37114e-08 1 4.37114e-08 0 0 -4.37114e-08 1 0 1 4.37114e-08 1.91069e-15 0 0.3 0 -0.4 1)(nd StaticMesh (setVisible 1) (setTransparent) (load models/rightgoal.obj) (sSc 2.18 0.88 0.68)(resetMaterials grey_naogoalnet.png sky-blue white)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 -1.07 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 1.07 0 1))(nd TRF (SLT 0.866025 0 -0.5 0 0 1 0 0 0.5 0 0.866025 0 -0.06 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0.3 1.05 0.4 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0.3 -1.05 0.4 1)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 -24.99 0 0 1)(nd SMN (setVisible 1) (load StdUnitBox) (sSc 1 51 1) (sMat matGrey)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 24.99 0 0 1)(nd SMN (setVisible 1) (load StdUnitBox) (sSc 1 51 1) (sMat matGrey)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 25 0 1)(nd SMN (setVisible 1) (load StdUnitBox) (sSc 50.98 1 1) (sMat matGrey)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 -25 0 1)(nd SMN (setVisible 1) (load StdUnitBox) (sSc 50.98 1 1) (sMat matGrey)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 -15 10 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 -15 -10 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 15 10 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 15 -10 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0.0417605 1)(nd StaticMesh (setVisible 1) (load models/soccerball.obj) (sSc 0.042 0.042 0.042)(resetMaterials soccerball_rcs-soccerball.png)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1)(nd TRF (SLT -3.37596e-08 -1 6.35012e-09 0 1 -3.37596e-08 9.93346e-08 0 -9.93346e-08 6.35013e-09 1 0 -14.2 9.2 0.388233 1)(nd TRF (SLT -1 3.82137e-15 8.74228e-08 -0 8.74228e-08 4.37114e-08 1 0 0 1 -4.37114e-08 0 0 0 0 1)(nd StaticMesh (setVisible 1) (load models/naobody.obj) (sSc 0.1 0.1 0.1)(resetMaterials matNum9 matLeft naoblack naowhite)))(nd TRF (SLT 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1)(nd SMN (setVisible 0) (setTransparent) (load StdUnitCylinder 1 1) (sSc 0.2 0.2 0.02) (sMat matSelect))))(nd TRF (SLT -3.37596e-08 -1 6.35011e-09 0 1 -3.37596e-08 9.9329e-08 0 -9.9329e-08 6.35012e-09 1 0 -14.2 9.2 0.478233 1)(nd SMN (setVisible 1) (load StdCapsule 0.015 0.08) (sSc 1 1 1) (sMat matDarkGrey)))(nd TRF (SLT -3.37596e-08 -1 6.35011e-09 0 1 -3.37596e-08 9.9329e-08 0 -9.9329e-08 6.35012e-09 1 0 -14.2 9.2 0.543233 1)(nd TRF (SLT -1 3.82137e-15 8.74228e-08 -0 8.74228e-08 4.37114e-08 1 0 0 1 -4.37114e-08 0 0 0 0 1)(nd StaticMesh (setVisible 1) (load models/naohead.obj) (sSc 0.1 0.1 0.1)(resetMaterials matLeft naoblack naogrey naowhite))))(nd TRF (SLT -3.37586e-08 -1 6.35261e-09 0 1 -3.37586e-08 9.93349e-08 0 -9.93349e-08 6.35261e-09 1 0 -14.2 9.102 0.463233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37586e-08 -1 6.3551e-09 0 1 -3.37586e-08 9.94106e-08 0 -9.94106e-08 6.3551e-09 1 0 -14.18 9.092 0.463233 1)(nd StaticMesh (setVisible 1) (load models/rupperarm.obj) (sSc 0.07 0.07 0.07)(resetMaterials matLeft naoblack naowhite)))(nd TRF (SLT -3.37584e-08 -1 6.3551e-09 0 1 -3.37584e-08 9.94073e-08 0 -9.94073e-08 6.3551e-09 1 0 -14.11 9.102 0.472233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37584e-08 -1 6.3551e-09 0 1 -3.37584e-08 9.94039e-08 0 -9.94039e-08 6.3551e-09 1 0 -14.06 9.102 0.472233 1)(nd StaticMesh (setVisible 1) (load models/rlowerarm.obj) (sSc 0.05 0.05 0.05)(resetMaterials matLeft naowhite)))(nd TRF (SLT -3.37607e-08 -1 6.34763e-09 0 1 -3.37607e-08 9.93349e-08 0 -9.93349e-08 6.34763e-09 1 0 -14.2 9.298 0.463233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37607e-08 -1 6.34514e-09 0 1 -3.37607e-08 9.94106e-08 0 -9.94106e-08 6.34514e-09 1 0 -14.18 9.308 0.463233 1)(nd StaticMesh (setVisible 1) (load models/lupperarm.obj) (sSc 0.07 0.07 0.07)(resetMaterials matLeft naoblack naowhite)))(nd TRF (SLT -3.37609e-08 -1 6.34514e-09 0 1 -3.37609e-08 9.94073e-08 0 -9.94073e-08 6.34514e-09 1 0 -14.11 9.298 0.472233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37609e-08 -1 6.34514e-09 0 1 -3.37609e-08 9.94039e-08 0 -9.94039e-08 6.34514e-09 1 0 -14.06 9.298 0.472233 1)(nd StaticMesh (setVisible 1) (load models/llowerarm.obj) (sSc 0.05 0.05 0.05)(resetMaterials matLeft naowhite)))(nd TRF (SLT -3.37784e-08 -1 6.35243e-09 0 1 -3.37784e-08 9.9316e-08 0 -9.9316e-08 6.35243e-09 1 0 -14.21 9.145 0.273233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37786e-08 -1 6.35243e-09 0 1 -3.37786e-08 9.92788e-08 0 -9.92788e-08 6.35244e-09 1 0 -14.21 9.145 0.273233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37789e-08 -1 6.35474e-09 0 1 -3.37789e-08 9.92787e-08 0 -9.92787e-08 6.35474e-09 1 0 -14.2 9.145 0.233233 1)(nd StaticMesh (setVisible 1) (load models/rthigh.obj) (sSc 0.07 0.07 0.07)(resetMaterials matNum9 matLeft naowhite)))(nd TRF (SLT -3.3779e-08 -1 6.35565e-09 0 1 -3.3779e-08 9.92785e-08 0 -9.92785e-08 6.35566e-09 1 0 -14.195 9.145 0.108233 1)(nd StaticMesh (setVisible 1) (load models/rshank.obj) (sSc 0.08 0.08 0.08)(resetMaterials matLeft naoblack naowhite)))(nd TRF (SLT -3.37792e-08 -1 6.3558e-09 0 1 -3.37792e-08 9.92785e-08 0 -9.92785e-08 6.3558e-09 1 0 -14.205 9.145 0.0532329 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matRed)))(nd TRF (SLT -3.37793e-08 -1 6.3558e-09 0 1 -3.37793e-08 9.92749e-08 0 -9.92749e-08 6.3558e-09 1 0 -14.175 9.145 0.0132329 1)(nd StaticMesh (setVisible 1) (load models/rfoot.obj) (sSc 0.08 0.08 0.08)(resetMaterials matLeft naowhite)))(nd TRF (SLT -3.37409e-08 -1 6.34773e-09 0 1 -3.37409e-08 9.9316e-08 0 -9.9316e-08 6.34774e-09 1 0 -14.21 9.255 0.273233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37406e-08 -1 6.34773e-09 0 1 -3.37406e-08 9.92787e-08 0 -9.92787e-08 6.34773e-09 1 0 -14.21 9.255 0.273233 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matYellow)))(nd TRF (SLT -3.37404e-08 -1 6.34534e-09 0 1 -3.37404e-08 9.92787e-08 0 -9.92787e-08 6.34535e-09 1 0 -14.2 9.255 0.233233 1)(nd StaticMesh (setVisible 1) (load models/lthigh.obj) (sSc 0.07 0.07 0.07)(resetMaterials matLeft naowhite)))(nd TRF (SLT -3.37402e-08 -1 6.34439e-09 0 1 -3.37402e-08 9.92785e-08 0 -9.92785e-08 6.3444e-09 1 0 -14.195 9.255 0.108233 1)(nd StaticMesh (setVisible 1) (load models/lshank.obj) (sSc 0.08 0.08 0.08)(resetMaterials matLeft naoblack naowhite)))(nd TRF (SLT -3.37401e-08 -1 6.34424e-09 0 1 -3.37401e-08 9.92785e-08 0 -9.92785e-08 6.34425e-09 1 0 -14.205 9.255 0.0532329 1)(nd SMN (setVisible 1) (load StdUnitSphere) (sSc 0.01 0.01 0.01) (sMat matRed)))(nd TRF (SLT -3.374e-08 -1 6.34424e-09 0 1 -3.374e-08 9.92749e-08 0 -9.92749e-08 6.34425e-09 1 0 -14.175 9.255 0.0132329 1)(nd StaticMesh (setVisible 1) (load models/lfoot.obj) (sSc 0.08 0.08 0.08)(resetMaterials matLeft naowhite)))))";
//        Perception p = new Perception();
//        p.update(msg.toCharArray(), new int[]{0, msg.length()});
        
        
//        Drawing3D applet = new Drawing3D(p.getSceneGraph());
//        applet.createFrame();
        //applet.draw(p.getSceneGraph());
    }
    
    public void parse (String msg, Node root, EnvironmentInformation ei){
        
        // Java Reflection
        char [] str = null;
        try {
            Field field = String.class.getDeclaredField("value");
            field.setAccessible(true);
            str = (char []) field.get(str);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw new IllegalStateException("Reflection Fail: " + ex.toString()); 
        }
        
        int open = 0;
        int close = 0;
        
        boolean environmentInfo = true;
        
        for (int i = 0; i < msg.length(); i++){
            
        }
        
    }
}
