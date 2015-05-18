package net.faintedge.poe;

import net.faintedge.poe.skilltree.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * Created by hsuwk7 on 5/17/2015.
 */
public class OptiFrame extends JFrame {
    JTextField output,search,input;
    JPanel left, right;
    JButton add;
    JComboBox<String> keystoneMenu;
    JScrollPane addedNodes;
    List<Node> nodes;
    Node searchedNode;
    @Override
    public void paint(Graphics g) {

        super.paint(g);
    }

    public OptiFrame(String s, List<Node> n) {
        super(s);
        nodes = n;
        JTextField output = new JTextField("");
        JTextField input = new JTextField("");
        JTextField search = new JTextField("eldritch battery");

        JTextPane notables = new JTextPane();
        search.setColumns(20);
        add = new JButton("+");
        keystoneMenu = new JComboBox<String>();
        addedNodes = new JScrollPane();;
        left = new JPanel();
        right = new JPanel();
        left.setLayout(new BorderLayout());
        right.setLayout(new BorderLayout());
        left.add(search,BorderLayout.NORTH);
        left.add(add,BorderLayout.EAST);
        right.add(addedNodes);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(right,BorderLayout.EAST);
        contentPane.add(left,BorderLayout.WEST);
        contentPane.add(output,BorderLayout.SOUTH);
        contentPane.add(input,BorderLayout.NORTH);

    /*
    addKeystone.addActionListener(new ActionListener(){

    });
    ArrayList<String> keystoneOptions = new ArrayList<String>();
    for(Node n:nodes){
      if(n.isKeystone()) {
        keystoneOptions.add(n.getName());
      }
    }*/
        search.addKeyListener(new KeyListener(){

            public void keyTyped(KeyEvent e) {
                suggest(e);
            }

            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {

            }
        });
        output.addActionListener(new ActionListener(){
                                     public void actionPerformed(ActionEvent e){
                                         highlight();
                                     }
                                 }
        );
        add.addActionListener(new ActionListener(){
                                  public void actionPerformed(ActionEvent e){
                                      addNode();
                                  }
                              }
        );
        output.setEditable(false);
    }
    public void highlight(){
        output.selectAll();
    }
    public void addNode(){

    }
    public void suggest(KeyEvent e){
        String typed="";
        if(search.isValid()) {
            typed = search.getText();
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(typed.length()>1){
                addedNodes.add(new JTextArea(typed));
            }

        }
        for(Node n: nodes){
            if(n.getName().substring(0,typed.length()).equalsIgnoreCase(typed)){
                searchedNode = n;
            }
        }
    }
}
