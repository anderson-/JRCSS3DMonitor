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

package shared.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author anderson
 */
public class Node<T extends Node> {

    public final String name;
    private T parent = null;
    private ArrayList<T> children;

    protected Node(String name) {
        this.name = name;
        parent = null;
        children = new ArrayList<>();
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public T getParent() {
        return parent;
    }

    public void addChild(T n) {
        if (n != null) {
            children.add(n);
            n.setParent(this);
        }
    }

    public void addChild(Node... ns) {
        for (Node n : ns) {
            addChild(n);
        }
    }

    public List<T> getChildren() {
        return children;
    }

    public void clear() {
        children.clear();
    }

    public String getInfo() {
        return "<no info avaliable>";
    }

    public final void print() {
        print("", true);
    }

    protected final void print(String prefix, boolean isTail) {
        System.out.println(prefix + "|");
        System.out.print(prefix + (isTail ? "└── " : "├── ") + "(" + getAddress() + ")  " + name + " " + getInfo());

        StringBuilder addr = new StringBuilder();

        addr.append(" [");

        for (int s : getFullAddress()) {
            addr.append(",").append(s);
        }
        addr.append("]");
        addr.deleteCharAt(2);

        System.out.println(addr.toString());

        if (!children.isEmpty()) {
            for (int i = 0; i < children.size() - 1; i++) {
                children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
            }
            if (children.size() >= 1) {
                children.get(children.size() - 1).print(prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    public int countNodes() {
        return countNodes(this);
    }

    private int countNodes(Node n) {
        if (n.children.isEmpty()) {
            return 1;
        } else {
            int i = 0;
            for (Node c : (ArrayList<Node>) n.children) {
                i += c.countNodes();
            }
            return i;
        }
    }

    public int getAddress() {
        if (parent != null) {
            int i = 0;
            // não é raiz
            Node it = this;

            while (it.parent != null) {
                it = it.parent;
            }

            Stack<Node> stk = new Stack<>();
            while (it != this) {
                for (int a = it.children.size() - 1; a >= 0; a--) {
                    stk.push(((ArrayList<Node>) it.children).get(a));
                }
                it = stk.pop();
                i++;
            }
            return i;
        }
        return 0;
    }

    public Node search(int address) {
        int i = 0;
        Node it = this;

        while (it.parent != null) {
            it = it.parent;
        }

        Stack<Node> stk = new Stack<>();
        while (i != address) {
            for (int a = it.children.size() - 1; a >= 0; a--) {
                stk.push(((ArrayList<Node>) it.children).get(a));
            }

            if (stk.empty()) {
                return null;
            }
            
            it = stk.pop();
            i++;
        }

        return it;
    }

    private ArrayList<Integer> getFullAddress() {
        ArrayList<Integer> fullAddress = new ArrayList<>();
        Node n = this;
        while (n.getParent() != null) {
            fullAddress.add(0, n.getParent().getChildren().indexOf(n));
            n = n.getParent();
        }
        return fullAddress;
    }
}
