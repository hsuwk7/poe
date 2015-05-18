package net.faintedge.poe;

import net.faintedge.poe.skilltree.Node;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsuwk7 on 5/17/2015.
 */
public class OptiFrame extends JFrame {
    JTextField output,input,search;
    String t;
    JPanel topleft,bottomleft, topright,bottomright,right, left,top;
    JButton add,oButton,removeButton,inputButton;
    JList addedNodes,keystoneSuggestBox;
    DefaultListModel aModel,ksbModel;
    List<Node> nodes;
    ArrayList<Node> matches;
    ArrayList<String> added;
    @Override
    public void paint(Graphics g) {
        //refresh sizes
        //System.out.println("asdf");
        //addedNodes = new JList(added.toArray());

        //addedNodes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggest();
        input.setColumns((int)((this.getWidth()-inputButton.getWidth())/15));
        search.setColumns((int)(this.getWidth()*0.5/15));
        addedNodes.setFixedCellWidth((int)(this.getWidth()/2));
        //addedNodes
        super.paint(g);
        //search.setSize((int)(this.getWidth()*0.5),output.getHeight()*2);
        //addedNodes.setSize((int)(this.getWidth()*0.5),output.getHeight()*2);
        //pack();
    }

    public OptiFrame(String s, List<Node> n) {
        super(s);
        t = "";
        nodes = n;
        top = new JPanel();
        topleft = new JPanel();
        bottomleft = new JPanel();
        topright = new JPanel();
        bottomright = new JPanel();
        left = new JPanel();
        right = new JPanel();
        output = new JTextField("");
        input = new JTextField("");
        search = new JTextField("");
        matches = new ArrayList<Node>();
        JTextPane notables = new JTextPane();
        search.setEditable(true);
        for(Node no:nodes){
            search.add(new JTextArea(no.getName()));
        }
        add = new JButton("+");
        inputButton = new JButton("Import Tree");
        oButton = new JButton("Create Optimized Tree");
        removeButton = new JButton("Remove Node");
        //add.setSize(new Dimension(100,100));
        ksbModel = new DefaultListModel<String>();
        keystoneSuggestBox = new JList(ksbModel);

        added = new ArrayList<String>();
        aModel = new DefaultListModel();
        aModel.addElement("");
        addedNodes = new JList(aModel);
        addedNodes.setFixedCellWidth((int) (this.getWidth() / 2));
        addedNodes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //an = new JPanel();
        //addedNodes.add(an);

        //right.setSize(400,500);
        topleft.setLayout(new FlowLayout());
        top.setLayout(new FlowLayout());
        topright.setLayout(new BorderLayout());
        bottomleft.setLayout(new BorderLayout());
        right.setLayout(new BorderLayout());
        left.setLayout(new BorderLayout());

        topright.add(addedNodes);
        bottomright.add(oButton,BorderLayout.EAST);
        bottomright.add(removeButton,BorderLayout.WEST);

        top.add(input);
        top.add(inputButton);
        topleft.add(search);
        topleft.add(add);
        bottomleft.add(keystoneSuggestBox,BorderLayout.CENTER);
        left.add(topleft,BorderLayout.NORTH);
        left.add(bottomleft,BorderLayout.CENTER);
        right.add(topright,BorderLayout.CENTER);
        right.add(bottomright,BorderLayout.SOUTH);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(7,7));
        contentPane.add(right,BorderLayout.EAST);
        contentPane.add(left,BorderLayout.WEST);
        contentPane.add(output,BorderLayout.SOUTH);
        contentPane.add(top,BorderLayout.NORTH);
        suggest();
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
                ksbModel.removeAllElements();
                t = e.getKeyChar()+"";
                if(e.getKeyChar()=='\n'){
                    //System.out.println("enter");
                    //System.out.println("set "+matches.size()+" "+typed);
                    if(search.getText().length()>1&&matches.size()>0){
                        //change index of 0 based on selected in suggest box
                        search.setText((String)ksbModel.get(keystoneSuggestBox.getSelectedIndex()));
                        //addedNodes.removeAll();
                    }
                }
                if(!matches.isEmpty()){
                    for(Node n:matches) {
                        if(n.isKeystone()) {
                            ksbModel.addElement(n.getName());
                        }
                    }
                    if(!ksbModel.isEmpty()){
                        keystoneSuggestBox.setSelectedIndex(0);
                    }
                    keystoneSuggestBox.repaint();
                }
            }

            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {
                suggest();

            }
        });
        output.addActionListener(new ActionListener(){
                                     public void actionPerformed(ActionEvent e){
                                         highlight();
                                     }
                                 }
        );
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //System.out.println();
                if(!aModel.contains((String)keystoneSuggestBox.getSelectedValue())) {
                    aModel.addElement((String) keystoneSuggestBox.getSelectedValue());
                    repaint();
                }

               /*
               if(matches.size()>0){
                    addedNodes.add(new JTextArea(matches.get(keystoneSuggestBox.getSelectedIndex()).getName()));
               }*/
            }
        });
        //output.setEditable(false);
        pack();
    }
    public void highlight(){
        output.selectAll();
    }
    public void addNode(){

    }
    public void suggest(){
        String typed = search.getText();
        ksbModel.removeAllElements();
        System.out.println("typed:"+typed+" "+typed.equals(""));
        for(Node n: nodes){
            try{
                if(n.getName().toLowerCase().startsWith(typed.toLowerCase())||typed.equals("")){
                    //searchedNode = n;
                    if(n.isKeystone()) {
                        ksbModel.addElement(n.getName());
                    }
                    //System.out.print(n.getName()+", ");
                }
            }
            catch(Exception ee){
            }
        }
        if(!ksbModel.isEmpty()){
            keystoneSuggestBox.setSelectedIndex(0);
        }

    }

}
