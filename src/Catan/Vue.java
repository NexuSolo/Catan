package Catan;

import Catan.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Vue extends JFrame {

    Vue() {
        this.setVisible(true);
        setSize(1240,720);
       //setLayout(new GridLayout(4,4));
        plateauToPanel(null);
        
    }

    public class RouteGraphique extends JPanel implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

    }

    public JPanel plateauToPanel(Plateau plateau) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));
        JPanel j1 = new JPanel();
        j1.setLayout(new BorderLayout());
        j1.setBackground(Color.BLUE);
        JButton nord = new JButton();
        nord.setBorderPainted(false);
        nord.setBackground(Color.BLACK);
        j1.add(nord,BorderLayout.NORTH);
        JPanel j2 = new JPanel();
        j2.setBackground(Color.RED);
        JPanel j3 = new JPanel();
        j3.setBackground(Color.YELLOW);
        JPanel j4 = new JPanel();
        j4.setBackground(Color.GREEN);
        JPanel j5 = new JPanel();
        j5.setBackground(Color.PINK);
        panel.add(j1);
        panel.add(j2);
        panel.add(j3);
        panel.add(j4);
        panel.add(j5);
        add(panel);
        return null;
    }

    public static void main(String[] args) {
        new Vue();
    }

}