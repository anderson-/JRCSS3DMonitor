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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author anderson
 */
public abstract class MonitorBaseNode extends BaseNode{
    public enum Tag {
        // NAO
        NAO,
        NAO_HEAD,
        NAO_BODY,
        NAO_UPPER_ARM,
        NAO_LOWER_ARM,
        NAO_THIGH,
        NAO_SHANK,
        NAO_FOOT,
        
        //PLAYER
        PLAYER_NUM,
        PLAYER_TEAM,
        
        //OBJECT
        OBJ_MODEL,
        OBJ_COLOR,
        OBJ_TRANSPARENT,
        OBJ_VISIBLE,
        
        //SOCCER
        SOCCER_BALL,
        SOCCER_FIELD,
        SOCCER_GOAL,
        
        //PRIMITIVE MESH
        MESH_BOX,
        MESH_SPHERE,
        
        //LIGHT
        LIGHT_DIFFUSE,
        LIGHT_AMBIENT,
        LIGHT_SPECULAR,
        
        //NODE TYPE
        NODE_TRANSFORM,
        NODE_STATIC_MESH,
        NODE_STATIC_MESH_NODE,
        NODE_LIGHT,
    }
    
    protected HashMap<Tag,String> tags;
    protected double [] values = null;
    
    public MonitorBaseNode (String name) {
        super(name);
        tags = new HashMap<>();
    }
    
    
    public double getRealValue (int index){
        return (values != null && index >= 0 && index < values.length)? values[index] : null;
    }
    
    public void addTag (Tag tag, String value){
        tags.put(tag, value);
    }
    
    public boolean containsTag (Tag tag){
        return tags.containsKey(tag);
    }
    
    public void clearTags(){
        tags.clear();
    }
    
    public String getTag(Tag tag){
        return tags.get(tag);
    }
    
    public List<BaseNode> search (Tag tag){
        MonitorBaseNode it = this;

        while (it.getParent() != null) {
            it = (MonitorBaseNode)it.getParent();
        }

        Stack<BaseNode> stk = new Stack<>();
        ArrayList<BaseNode> list = new ArrayList<>();
        stk.push(it);
        while (!stk.empty()) {
            it = (MonitorBaseNode)stk.pop();
            
            if (it.containsTag(tag)) {
                list.add(it);
            }
            
            for (BaseNode n : it.getChildren()){
                stk.push(n);
            }
        }
        
        return list;
    }
    
}
