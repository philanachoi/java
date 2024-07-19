import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GuiLabel_fillOval extends JLabel{
    private Color theColor;
    public void getColor(Color x){
        theColor=x;
    }
    public void paint(Graphics g){
        Graphics2D g2D=(Graphics2D)g;
        super.paint(g);
        g2D.setColor(theColor);
        g2D.fillOval(0,0,100,100);
    }
}

class GuiLabel_drawOval extends JLabel{
    private Color theColor;
    public void getColor(Color x){
        theColor=x;
    }
    public void paint(Graphics g){
        Graphics2D g2D=(Graphics2D)g;
        super.paint(g);
        g2D.setColor(theColor);
        Stroke sss=new BasicStroke(7f);
        g2D.setStroke(sss);
        g2D.drawOval(0,0,100,100);
        //g2D.draw(new Line2D.Double(50,50,10,10));
    }
}

class multiTmp6 extends JLabel implements Runnable{
    public JFrame myFrame;
    public GuiLabel_fillOval myLabel;
    public JLabel getLabel;
    
    public void run(){
        int x=(int)getLabel.getLocation().getX();
        int y=(int)getLabel.getLocation().getY();

        for (int i=0; i<y-200; i=i+2){
            myLabel.setLocation(x,i+200);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }
}

class ClickEvent14 implements ActionListener{
    public int xxx;
    public JFrame myFrame;
    public JProgressBar myBar;
    public JLabel labels[][];
    public JButton buttons[];
    public JDialog msgDialog;

    public int xArray[]=new int[7];
    public GuiLabel_fillOval moveLabel[][]=new GuiLabel_fillOval[6][7];
    public void actionPerformed(ActionEvent e){
        if (xArray[xxx]<6){
            String theName=null;
            Color theColor = null;              //new Color(0xff5076);  //Color.decode("#b8ff4b"); //Color.decode("0xb8ff4b");    

            int total=myBar.getValue()+1;
            myBar.setValue(total);
            int tmpNum=42-total;
            myBar.setString("Remaining Step: "+ tmpNum +"/42");

            int yyy=5-xArray[xxx];

            if ((total % 2)==1){
                theName="X";
                theColor=new Color(0xff5076);

                GuiLabel_fillOval p1=new GuiLabel_fillOval();
                p1.getColor(theColor);
                p1.setLocation(labels[yyy][xxx].getLocation());
                p1.setSize(100,100);
            }else{
                theName="O";
                theColor=new Color(0xb8ff4b);

                GuiLabel_fillOval p2=new GuiLabel_fillOval();
                p2.getColor(theColor);
                p2.setLocation(labels[yyy][xxx].getLocation());
                p2.setSize(100,100);
            }

            moveLabel[yyy][xxx]=new GuiLabel_fillOval();
            moveLabel[yyy][xxx].setLocation(10,100);
            moveLabel[yyy][xxx].setSize(100,100);
            moveLabel[yyy][xxx].setOpaque(false);
            myFrame.add(moveLabel[yyy][xxx]); 
            moveLabel[yyy][xxx].getColor(theColor);
            labels[yyy][xxx].setName(theName);


            multiTmp6 mmm=new multiTmp6();
            mmm.myFrame=myFrame;
            mmm.myLabel=moveLabel[yyy][xxx];
            mmm.getLabel=labels[yyy][xxx];

            Thread theThread=new Thread(mmm);
            theThread.start();

            for (int i=0; i<buttons.length;i++){
                if (xArray[xxx]<6){
                    buttons[i].setBackground(theColor);
                    buttons[i].setBorderPainted(false);
                } 
            }

            xArray[xxx]++;
            if (xArray[xxx]==6){
                buttons[xxx].setOpaque(false);
                buttons[xxx].setText("");
            }

            Check ccc=new Check();
            ccc.msgDialog=msgDialog;
            ccc.labels=labels;
            ccc.theColor=theColor;   
            ccc.run();
        }
    }
}


class Check{
    public JDialog msgDialog;
    public JLabel labels[][];
    public Color theColor;

    public void run(){    
        int tic_X;
        int toe_X;

        int tic_Y[]=new int[7];
        int toe_Y[]=new int[7];

        int tic_slash[]=new int[12];
        int toe_slash[]=new int[12];

        int tic_backSlash[]=new int[12];
        int toe_backSlash[]=new int[12];

        String msg=null;
        boolean bool=false;
        for (int y=5, i=0; y>=0;y--,i++){
            tic_X=0;
            toe_X=0;

            for (int x=0; x<7;x++){
                String thisName=labels[y][x].getName();

                int a=x-i;         //Slash
                if (a<0){
                    a=(a*-1)+6;
                }

                int b=x+i;         //backSlash

                if (thisName=="X"){
                    tic_X++;
                    toe_X=0;

                    tic_Y[x]++;
                    toe_Y[x]=0;

                    tic_slash[a]++;
                    toe_slash[a]=0;

                    tic_backSlash[b]++;
                    toe_backSlash[b]=0;

                    if (tic_X==4||tic_Y[x]==4||tic_slash[a]==4||tic_backSlash[b]==4){
                        bool=true;
                        msg="Red Win";    //X Win
                        break;
                    }

                }else if (thisName=="O"){
                    toe_X++;
                    tic_X=0;

                    toe_Y[x]++;
                    tic_Y[x]=0;

                    toe_slash[a]++;
                    tic_slash[a]=0;

                    toe_backSlash[b]++;
                    tic_backSlash[b]=0;

                    if (toe_X==4||toe_Y[x]==4||toe_slash[a]==4||toe_backSlash[b]==4){
                        bool=true;
                        msg="Green Win";    //O Win
                        break;
                    }
                }else{
                    tic_X=0;
                    toe_X=0;
                }
                // System.out.println(y + "," + x + thisName);
                // System.out.println("X:" + tic_slash[y] + "Y:" + toe_slash[y]);
                // System.out.println();
            }
        }

        if (bool){
            JLabel winner=new JLabel();
            winner.setHorizontalAlignment(SwingConstants.CENTER);
            winner.setLocation(0,0);
            winner.setSize(500,100);
            winner.setFont(new Font("Chalkboard SE", Font.BOLD, 60));
            winner.setText(msg);

            JLabel msgColor=new JLabel();
            msgColor.setLocation(0,0);
            msgColor.setSize(msgDialog.getSize());
            msgColor.setBackground(theColor);
            msgColor.setOpaque(true);

            msgDialog.add(winner);
            msgDialog.add(msgColor);
            msgDialog.setVisible(true); 
        }
    }
}


public class HW_swing3_1 {
    public static void main(String args[]){
        JFrame frame=new JFrame("Connect 4");
        frame.setSize(780,910);
        frame.getContentPane().setBackground(Color.decode("#005bff"));
        frame.setLayout(null);

        JDialog msgDialog=new JDialog(frame, "Result");
        msgDialog.setModal(true);       //必須關閉目前視窗，先可以Click原本個視窗
        msgDialog.setSize(500,150);
        msgDialog.setLayout(null);
        msgDialog.setLocationRelativeTo(null);
        msgDialog.setTitle("Result");
        msgDialog.setVisible(false); 
        
        JProgressBar bar=new JProgressBar();
        bar.setLocation(40,0);
        bar.setSize(700,100);
        bar.setMaximum(42);               //default=100
        bar.setString("Remaining Step: 42/42");
        bar.setStringPainted(true);
        frame.add(bar); 
        
        JButton buttons[]=new JButton[7];
        GuiLabel_drawOval labels[][]=new GuiLabel_drawOval[6][7];
        for (int k=0; k<6;k++){
            for (int i=0; i<7;i++){
                int x=110;
                if (k==1){
                    buttons[i]=new JButton("click me");
                    buttons[i].setLocation(x*i+10,100);
                    buttons[i].setSize(100,100);
                    buttons[i].setOpaque(true);
                    frame.add(buttons[i]);

                    ClickEvent14 clickButton=new ClickEvent14();
                    buttons[i].addActionListener(clickButton);
                    clickButton.labels=labels;
                    clickButton.myBar=bar;
                    clickButton.xxx=i;
                    clickButton.buttons=buttons;
                    clickButton.myFrame=frame;
                    clickButton.msgDialog=msgDialog;
                }

                labels[k][i]=new GuiLabel_drawOval();
                labels[k][i].getColor(Color.decode("#0048ff"));
                labels[k][i].setHorizontalAlignment(SwingConstants.CENTER);
                labels[k][i].setLocation(x*i+10,110*k+220);
                labels[k][i].setSize(100,100);
                labels[k][i].setFont(new Font("Chalkboard SE", Font.BOLD, 60));
                labels[k][i].setVisible(true);
                frame.add(labels[k][i]);
            }
        }

        JLabel bg=new JLabel();
        bg.setLocation(0,0);
        bg.setSize(780,205);
        bg.setOpaque(true);
        bg.setBackground(Color.WHITE);
        frame.add(bg);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);         //一定放最後
    }
}






