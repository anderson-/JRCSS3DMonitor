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

package monitor.perceptors;

import shared.tree.basenode.BaseNode;

/**
 *
 * @author anderson
 */
public class EnvironmentInformation extends BaseNode {

    public static final char[][] CONTENTS;

    static {
        CONTENTS = new char[][]{
            "nd".toCharArray(),
            "FieldLength".toCharArray(),
            "FieldWidth".toCharArray(),
            "FieldHeight".toCharArray(),
            "GoalWidth".toCharArray(),
            "GoalDepth".toCharArray(),
            "GoalHeight".toCharArray(),
            "BorderSize".toCharArray(),
            "FreeKickDistance".toCharArray(),
            "WaitBeforeKickOff".toCharArray(),
            "AgentRadius".toCharArray(),
            "BallRadius".toCharArray(),
            "BallMass".toCharArray(),
            "RuleGoalPauseTime".toCharArray(),
            "RuleKickInPauseTime".toCharArray(),
            "RuleHalfTime".toCharArray(),
            "play_modes".toCharArray(),
            "time".toCharArray(),
            "team_left".toCharArray(),
            "team_right".toCharArray(),
            "half".toCharArray(),
            "score_left".toCharArray(),
            "score_right".toCharArray(),
            "play_mode".toCharArray()
        };
    }
    private double fieldLength = 0;
    private double fieldWidth = 0;
    private double fieldHeight = 0;
    private double goalWidth = 0;
    private double goalDepth = 0;
    private double goalHeight = 0;
    private double borderSize = 0;
    private double freeKickDistance = 0;
    private double waitBeforeKickOff = 0;
    private double agentRadius = 0;
    private double ballRadius = 0;
    private double ballMass = 0;
    private int ruleGoalPauseTime = 0;
    private int ruleKickInPauseTime = 0;
    private int ruleHalfTime = 0;
    private String play_modes = "";
    private double time = 0;
    private String team_left = "";
    private String team_right = "";
    private double half = 0;
    private int score_left = 0;
    private int score_right = 0;
    private int play_mode = 0;

    public EnvironmentInformation() {
        super("EnvironmentInformation");
    }

    @Override
    public void parseMessage() {
    }

    @Override
    public char[][] getContents() {
        return CONTENTS;
    }

    @Override
    public int getContentsLastFieldIndex() {
        return 23;
    }

    @Override
    protected void setField(int guess, int start, int end, char[] msg) {
        String str = new String(msg, start, (end - start));
        switch (guess) {
            case 1:
                fieldLength = Double.parseDouble(str);
                break;
            case 2:
                fieldWidth = Double.parseDouble(str);
                break;
            case 3:
                fieldHeight = Double.parseDouble(str);
                break;
            case 4:
                goalWidth = Double.parseDouble(str);
                break;
            case 5:
                goalDepth = Double.parseDouble(str);
                break;
            case 6:
                goalHeight = Double.parseDouble(str);
                break;
            case 7:
                borderSize = Double.parseDouble(str);
                break;
            case 8:
                freeKickDistance = Double.parseDouble(str);
                break;
            case 9:
                waitBeforeKickOff = Double.parseDouble(str);
                break;
            case 10:
                agentRadius = Double.parseDouble(str);
                break;
            case 11:
                ballRadius = Double.parseDouble(str);
                break;
            case 12:
                ballMass = Double.parseDouble(str);
                break;
            case 13:
                ruleGoalPauseTime = Integer.parseInt(str);
                break;
            case 14:
                ruleKickInPauseTime = Integer.parseInt(str);
                break;
            case 15:
                ruleHalfTime = Integer.parseInt(str);
                break;
            case 16:
                play_modes = str;
                break;
            case 17:
                time = Double.parseDouble(str);
                break;
            case 18:
                team_left = str;
                break;
            case 19:
                team_right = str;
                break;
            case 20:
                half = Double.parseDouble(str);
                break;
            case 21:
                score_left = Integer.parseInt(str);
                break;
            case 22:
                score_right = Integer.parseInt(str);
                break;
            case 23:
                play_mode = Integer.parseInt(str);
                break;
            default:
                throw new UnsupportedOperationException("Invalid guess!" + guess);
        }
    }

    @Override
    protected BaseNode createChild(int guess) {
        throw new UnsupportedOperationException("Invalid guess!");
    }
    
    
    public double getFieldLength() {
        return fieldLength;
    }

    public double getFieldWidth() {
        return fieldWidth;
    }

    public double getFieldHeight() {
        return fieldHeight;
    }

    public double getGoalWidth() {
        return goalWidth;
    }

    public double getGoalDepth() {
        return goalDepth;
    }

    public double getGoalHeight() {
        return goalHeight;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public double getFreeKickDistance() {
        return freeKickDistance;
    }

    public double getWaitBeforeKickOff() {
        return waitBeforeKickOff;
    }

    public double getAgentRadius() {
        return agentRadius;
    }

    public double getBallRadius() {
        return ballRadius;
    }

    public double getBallMass() {
        return ballMass;
    }

    public int getRuleGoalPauseTime() {
        return ruleGoalPauseTime;
    }

    public int getRuleKickInPauseTime() {
        return ruleKickInPauseTime;
    }

    public int getRuleHalfTime() {
        return ruleHalfTime;
    }

    public String getPlay_modes() {
        return play_modes;
    }

    public double getTime() {
        return time;
    }

    public String getTeam_left() {
        return team_left;
    }

    public String getTeam_right() {
        return team_right;
    }

    public double getHalf() {
        return half;
    }

    public int getScore_left() {
        return score_left;
    }

    public int getScore_right() {
        return score_right;
    }

    public int getPlay_mode() {
        return play_mode;
    }
    
}
