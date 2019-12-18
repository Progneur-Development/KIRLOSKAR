package com.teamcenter.schedule.view;
import java.awt.*;
import java.awt.Graphics2D;
import javax.swing.JApplet;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
  
  
public class NewGanttChartPOC extends JApplet 
{
   
     
        JPanel panel;
        DefaultMutableTreeNode rootnode = new DefaultMutableTreeNode();
        JTree tree = new JTree();
  
     
  public DefaultMutableTreeNode createNodes()
     
  {
        DefaultMutableTreeNode rootnode = new DefaultMutableTreeNode("RESOURCE");
        DefaultMutableTreeNode resource1 = new DefaultMutableTreeNode("RESOURCE1");
        DefaultMutableTreeNode resource2 = new DefaultMutableTreeNode("RESOURCE2");
        DefaultMutableTreeNode resource3 = new DefaultMutableTreeNode("RESOURCE3");
        rootnode.add(resource1);
        rootnode.add(resource2);
        rootnode.add(resource3);
        resource1.add(new DefaultMutableTreeNode("task1"));
        resource1.add(new DefaultMutableTreeNode("task2"));
        resource1.add(new DefaultMutableTreeNode("task3"));
        resource2.add(new DefaultMutableTreeNode("task1"));
        resource2.add(new DefaultMutableTreeNode("task2"));
        resource2.add(new DefaultMutableTreeNode("task3"));
        resource3.add(new DefaultMutableTreeNode("task1"));
        resource3.add(new DefaultMutableTreeNode("task2"));
        resource3.add(new DefaultMutableTreeNode("task3"));
        return rootnode;
  }
   public void paint(Graphics g)
   {     super.paint(g);
        int x = 30;
        Graphics2D g2 = (Graphics2D)g;
        int h = getHeight();
        int w = getWidth();
        for(int i = 0;i<4;i++)
        {
            x= x+70;
             for (int j= 0;j<4 ;j++)
             {
                g2.drawString("1",x=15,25); 
             }
                g2.drawRect( x,0,70,30)  ;
        }
   }
   public void update(Graphics g)
   {
       paint(g);
   }
    
  public void init()
  {
        Container cont = getContentPane();
        JPanel panel = new JPanel();
        JScrollPane pane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.setLayout(new BorderLayout());
        cont.add(pane);
        NewGanttChartPOC Reg = new NewGanttChartPOC();
        pane.add(tree,"LEFT");
        pane.setViewportView(panel);
        pane.setVisible(true);
        pane.setSize(400,400);
        pane.setOpaque(true);
      
        panel.setPreferredSize(new Dimension(1000,1000));
         
  } 
} 