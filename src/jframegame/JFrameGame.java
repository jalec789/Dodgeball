package jframegame;
//Jason Chan
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
//other imports for gradient
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class JFrameGame extends JPanel implements KeyListener, ActionListener, MouseListener {

    public static JFrame jf;
    public Toolkit tk = jf.getToolkit();
    private javax.swing.Timer gameTimer;
    private int sqy; //movement up or down
    private int sqx; //movement left or right
    private int size = 100; //size of player
    private int ballsize = 100; //size of balls
    private int[] BallY = new int[6]; //Y-axis array list of 6 balls
    private int[] BallX = new int[6]; //X-axis array list of 6 balls
    private static ActionListener Reset1;
    private static ActionListener Reset2;
    private int[] acc = new int[6]; //Balls accelerate after Speed up line(SpeedUpPt)
    private int initspeed;//inital speed adjusted by user
    private int speed; //speed of the game must be lowered when using a faster processor
    //change speed in game over section as well
    public int score = -100; //Changing score
    private int Fscore; //freezes score and finalies
    private Timer T1;
    private Timer T2;
    private boolean start = false;
    private int[] Hscore = new int[3];
    private String first = "J.A.S.O.N"; //first place winner. init jason(author)
    private String second = "J.A.S.O.N"; //second place winner
    private String third = "J.A.S.O.N"; //third place winner
    private int SpeedUpPt = 1050; //this will be an imaginary line where the balls speed up
//    private double speedrate = 0; //increases speed +1 after # of cycles
    private int time = 1700;  //milliseconds in cycles
    private boolean dark = true; // changes color of game
    //dark for color toggle
    Color drk;
    Color brt;
    // if 'q w' color toggle doesnt work delete dark boolean and other keys

    public JFrameGame() {
        //Constructs Game
        //dark for color toggle
        setBackground(Color.white);
        this.setPreferredSize(new Dimension(1250, 600));
        addKeyListener(this);
        addMouseListener(this);
        gameTimer = new javax.swing.Timer(1, this);
        gameTimer.start();
        //high score preset
        Hscore[0] = 3000; //first place
        Hscore[1] = 2000; //sceond place
        Hscore[2] = 1000; //third place
        //each set will have three balls
        //set 1
        Reset1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent w) {
                doaction1(w);
            }
        };
        T1 = new Timer(time, Reset1);
        //set 2
        Reset2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doaction2(e);
            }
        };
        T2 = new Timer(time, Reset2);
        sqy = 200; //init y-axis location
        sqx = 100; //init x-axis location
        T1.start();
        T2.stop();
        initspeed = Integer.parseInt(JOptionPane.showInputDialog("Input Speed 0-9"
                + "\n speed is highly dependent on your processor"
                + "\n 9 is recommended yet if you have a fast processor"
                + "\n put 0... First time here, try 9."));
        if (initspeed > 9 || initspeed < 0) {
            do {
                initspeed = Integer.parseInt(JOptionPane.showInputDialog(
                        "Put in a speed between 0 and 9 you twat!!!"));
            } while (initspeed > 9 || initspeed < 0);
        }
        speed = initspeed;
    }

    //Creates a new set of balls after Reset1 # of millisecs
    private void doaction1(ActionEvent e) {
        speed++;
        //ball start line (not visible till entry)
        BallX[0] = 1200;
        BallX[1] = 1500;
        BallX[2] = 1700;
        //chooses random row for each ball
        BallY[0] = (((int) (Math.random() * 5)) * 100);
        BallY[1] = (((int) (Math.random() * 5)) * 100);
        BallY[2] = (((int) (Math.random() * 5)) * 100);
        //Every Ball gets their own row per cycle
        if (BallY[1] == BallY[0]) {
            do {
                BallY[1] = (((int) (Math.random() * 5)) * 100);
            } while (BallY[1] == BallY[0]);
        }
        if (BallY[2] == BallY[0] || BallY[2] == BallY[1]) {
            do {
                BallY[2] = (((int) (Math.random() * 5)) * 100);
            } while (BallY[2] == BallY[0] || BallY[2] == BallY[1]);
        }
        repaint();
    }

    //Creastes a new set of balls after Reset2 # of millisecs
    private void doaction2(ActionEvent e) {
        BallX[3] = 1300;
        BallX[4] = 1500;
        BallX[5] = 1700;
        BallY[3] = (((int) (Math.random() * 5)) * 100);
        BallY[4] = (((int) (Math.random() * 5)) * 100);
        BallY[5] = (((int) (Math.random() * 5)) * 100);
        if (BallY[4] == BallY[3]) {
            do {
                BallY[4] = (((int) (Math.random() * 5)) * 100);
            } while (BallY[4] == BallY[3]);
        }
        if (BallY[5] == BallY[3] || BallY[5] == BallY[4]) {
            do {
                BallY[5] = (((int) (Math.random() * 5)) * 100);
            } while (BallY[5] == BallY[3] || BallY[5] == BallY[4]);
        }
        repaint();
    }

    //Moves balls to the left yet starts slow and speeds up inside the window
    @Override
    public void actionPerformed(ActionEvent w) {
        if (!dark) {
            drk = Color.white;
            brt = Color.black;
        } else if (dark = true) {
            drk = Color.black;
            brt = Color.white;
        }
        if (start == true && collision() == false) {
            T1.start();
            //when score reaches a certain number of points the game will
            //add a new set of balls
            if (score > 1000) {
                T2.start();
            } else {
                T2.stop();
            }
            //score will add during play through
            score++;
            //balls to the left
            if (BallX[0] >= SpeedUpPt) {
                acc[0] = speed;
//                acc[0] = 1 + initspeed;
            }
            if (BallX[1] >= SpeedUpPt) {
                acc[1] = speed;
            }
            if (BallX[2] >= SpeedUpPt) {
                acc[2] = speed;
            }
            if (BallX[3] >= SpeedUpPt) {
                acc[3] = speed;
            }
            if (BallX[4] >= SpeedUpPt) {
                acc[4] = speed;
            }
            if (BallX[5] >= SpeedUpPt) {
                acc[5] = speed;
            }
            BallX[0] -= acc[0];
            BallX[1] -= acc[1];
            BallX[2] -= acc[2];
            BallX[3] -= acc[3];
            BallX[4] -= acc[4];
            BallX[5] -= acc[5];
            if (BallX[0] < SpeedUpPt) {
                acc[0] = 10 + speed;
            }
            if (BallX[1] < SpeedUpPt) {
                acc[1] = 10 + speed;
            }
            if (BallX[2] < SpeedUpPt) {
                acc[2] = 10 + speed;
            }
            if (BallX[3] < SpeedUpPt) {
                acc[3] = 10 + speed;
            }
            if (BallX[4] < SpeedUpPt) {
                acc[4] = 10 + speed;
            }
            if (BallX[5] < SpeedUpPt) {
                acc[5] = 10 + speed;
            }
            repaint();

            //-------------------//
            // G A M E   O V E R //
            //-------------------//
            if (collision() == true && score > 0) {
                T1.restart();
                T1.stop();
                T2.restart();
                T2.stop();
                Fscore = score;
                JOptionPane.showMessageDialog(null, "Your score " + Fscore);
                //first place
                if (Hscore[0] < Fscore) {
                    Hscore[2] = Hscore[1]; //moves previous second place score down
                    Hscore[1] = Hscore[0]; //moves previous first place score down
                    Hscore[0] = Fscore; //updates first place score
                    third = second; //moves previous second place name down
                    second = first; //moves previous first place name down
                    first = JOptionPane.showInputDialog("Congrats!!!"
                            + " You got the highscore!!!"); //updates first place name
                } //second place
                else if (Hscore[1] < Fscore) {
                    Hscore[2] = Hscore[1]; //moves previous second place score down
                    Hscore[1] = Fscore; //updates second place score
                    third = second; //moves previous second place name down
                    second = JOptionPane.showInputDialog("Congrats!!!"
                            + " You got second place!!!"); //updates second place name
                } //third place
                else if (Hscore[2] < Fscore) {
                    Hscore[2] = Fscore;//updates third place score
                    third = JOptionPane.showInputDialog("Congrats!!! "
                            + "You got third place!!!");//updates third place name
                }
                //resets scoring, speed, and placement
                Fscore = 0;
                score = 0;
                speed = initspeed; //resets speed back to what it was initially
                BallX[0] = 1300;
                BallX[1] = 1500;
                BallX[2] = 1700;
                T1.start();
                T1.restart();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keytyped = e.getKeyCode();
        if (keytyped == KeyEvent.VK_ENTER) {
            start = true;
        }
        if (keytyped == KeyEvent.VK_ESCAPE) {
            start = false;
        }
        if (keytyped == KeyEvent.VK_W) {
            dark = false;
        }
        if (keytyped == KeyEvent.VK_Q) {
            dark = true;
        }
        if (start == true) {
            if (keytyped == KeyEvent.VK_UP) {
                sqy -= 100;
                if (sqy < 0) {
                    sqy += size;
                }
            } else if (keytyped == KeyEvent.VK_DOWN) {
                sqy += 100;
                if (sqy >= 500) {
                    sqy -= size;
                }
            } 
//            else if (keytyped == KeyEvent.VK_LEFT) {
//                sqx -= 100;
//                if (sqx < 0) {
//                    sqx += 100;
//                }
//            } 
//            else if (keytyped == KeyEvent.VK_RIGHT) {
//                sqx += 100;
//                if (sqx >= 1000) {
//                    sqx -= 100;
//                }
//            }
        }
        if (start == false) {
            if (keytyped == KeyEvent.VK_S) {
                initspeed = Integer.parseInt(JOptionPane.showInputDialog("Input Speed 0-9"
                        + "\n speed is highly dependent on your processor"
                        + "\n 9 is recommended yet if you have a fast processor"
                        + "\n put 0... First time here, try 9."));
                if (initspeed > 9 || initspeed < 0) {
                    do {
                        initspeed = Integer.parseInt(JOptionPane.showInputDialog(
                                "Put in a speed between 0 and 9 you twat!!!"));
                    } while (initspeed > 9 || initspeed < 0);
                    speed = initspeed;
                }
            }
        }
    }

    @ Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!dark) {
            g.setColor(Color.black);
            g.fillRect(0, 0, 1300, 700);
        }
        
        Font bigger = new Font("Arial", Font.ITALIC, 70); //for big stuff
        Font big = new Font("Arial", Font.BOLD, 50); //big font size 4 importance
        Font med = new Font("Arial", Font.ROMAN_BASELINE, 30); //shows stuff you might need
        Font medB = new Font("Arial", Font.BOLD, 40); //Bold medium
        Font small = new Font("Arial", Font.PLAIN, 20); //small for info
        Font smallB = new Font("Arial", Font.BOLD, 20); //small bold
        Font smaller = new Font("Arial", Font.ITALIC, 10); //little things
        //*******GRADIENT********//
        Graphics2D g2d = (Graphics2D) g;
        //creates transparent color
        double H = 0.0;
        double S = 0.0;
        double B = 0.0;
        int rgb = Color.HSBtoRGB((float) H, (float) S, (float) B);
        int red = rgb & 0xFF;
        int green = rgb & 0xFF;
        int blue = rgb & 0xFF;
        //colors used in gradient
        //Alpha color = blank
        Color s = new Color(red, green, blue, 0x1);
        //color of gradient consider changing(r,g,b,ALPHA)
        Color R = new Color(207, 66, 56, 255);
        Color O = new Color(216, 118, 61, 255);
        Color Y = new Color(253, 247, 155, 255);
        Color G = new Color(146, 192, 78, 255);
        Color C = new Color(82, 146, 205, 255);
        Color P = new Color(95, 37, 123, 255);

        //creates bright orange
        Color o = new Color(255, 153, 0);

//        Color r = Color.red;
//        Color O = Color.orange;
//        Color y = Color.yellow;
//        Color g = Color.green;
//        Color c = Color.cyan;
//        Color m = Color.magenta;
        //creates transparent green
//            Color e = new Color(0, 255, 0, 0x90);
        //start screen
        if (start == false) {
            //gradient
//            GradientPaint gradient = new GradientPaint(0, 600, C, 0, 0, P, true);
//            g2d.setPaint(gradient);
//            g2d.fillRect((((int) (Math.random() * 12)) * 100), 0, 100, 600);
            //info and stuff
            g.setFont(big);
            g.setColor(drk);
            g.drawString("D O D G E   B A L L", 410, 50);
            g.drawString("By: Jason Chan", 430, 130);
            g.setFont(med);
            g.drawString("Highscores:", 525, 180);
            g.drawString("1. " + first + ": " + Hscore[0], 525, 220);
            g.drawString("2. " + second + ": " + Hscore[1], 525, 260);
            g.drawString("3. " + third + ": " + Hscore[2], 525, 300);
            g.setFont(big);
            g.drawString("PRESS [ENTER] TO START", 280, 560);
            //show instrutions
            g.setFont(small);
            g.drawString("I N S T R U C T I O N S", 20, 370);
            g.drawString("User plays as a square and tries to avoid balls from right side", 20, 400);
            g.drawString("going in the left direction. Your score increases as long as the", 20, 430);
            g.drawString("square doesn't touch the ball. Speed will increase while playing.", 20, 460);
            g.drawString("To change speed press [s]. To pause back to this menu press [Esc].", 20, 490);
            g.drawString("[q] & [w] to toggle color. Enjoy.", 20, 520);
        }

        if (start == true) {
            //back boxes
            g.setColor(Color.CYAN);
            //speed box
            g.fillRect(940, 518, 140, 75);
            g.drawRect(935, 513, 150, 85);
            //controls box
            g.setColor(o);
            g.fillRect(290, 518, 300, 75);
            g.drawRect(285, 513, 310, 85);
            //score box
            g.fillRect(10, 518, 200, 75);
            g.drawRect(5, 513, 210, 85);
            //score box
            g.fillRect(650, 518, 200, 75);
            g.drawRect(645, 513, 210, 85);
            g.setColor(Color.black);
            g.fillRect(655, 525, 190, 30);
            g.setColor(o);
            g.setFont(smallB);
            g.drawString("S   C   O   R   E", 680, 545);
            g.drawRect(685, 550, 130, 1);
            //player
            g.setColor(drk);
            g.fillRect(sqx, sqy, size, size);
            //balls add 50 for viiblity reasons
            g.fillOval(BallX[0] + 50, BallY[0], ballsize, ballsize);
            g.fillOval(BallX[1] + 50, BallY[1], ballsize, ballsize);
            g.fillOval(BallX[2] + 50, BallY[2], ballsize, ballsize);
            g.fillOval(BallX[3] + 50, BallY[3], ballsize, ballsize);
            g.fillOval(BallX[4] + 50, BallY[4], ballsize, ballsize);
            g.fillOval(BallX[5] + 50, BallY[5], ballsize, ballsize);
            //lines
            g.fillRect(0, 100, 1250, 1);
            g.fillRect(0, 200, 1250, 1);
            g.fillRect(0, 300, 1250, 1);
            g.fillRect(0, 400, 1250, 1);
            g.fillRect(0, 500, 1250, 1);
            //display score and score
            g.setColor(drk);
            g.setFont(smaller);
            g.drawString("H  i  g  h     S  c  o  r  e  s", 20, 510);
            g.drawString("C  O  N  T  R  O  L  S", 380, 510);
            g.drawString("S   P   E   E   D", 980, 510);
            g.setFont(bigger);
            g.setColor(Color.BLACK);
            g.drawString("x" + acc[0], 950, 580);
            g.setFont(medB);
            g.drawString("" + score / 100 + "00", 660, 588);
            g.setFont(small);
            g.drawString("+" + score % 100, 810, 572);
            g.drawString("1. " + first + ": " + Hscore[0], 20, 540);
            g.drawString("2. " + second + ": " + Hscore[1], 20, 565);
            g.drawString("3. " + third + ": " + Hscore[2], 20, 590);
            g.drawString("[UP] & [DOWN] arrows to move", 300, 550);
            g.drawString("[ESC] to pause to Main Menu", 300, 580);
            //when you get hit game will diplay few options

            g.setColor(drk);
            if (!T1.isRunning()) {
                g.setFont(big);
                g.drawString("Press UP, DOWN, or ENTER to continue playing", 300, 100);
                g.drawString("Press ESC back to Main Menu", 500, 200);
            }

            //gradient pattern
            if (BallX[0] >= -100 && BallX[0] <= 150) {
                GradientPaint gradient = new GradientPaint(0, 0, P, 1000, 0, s, true);
                g2d.setPaint(gradient);
                g2d.fillRect(0, BallY[0], 1000, 100);
            }
            if (BallX[1] >= -100 && BallX[1] <= 150) {
                GradientPaint gradient = new GradientPaint(0, 0, C, 1000, 0, s, true);
                g2d.setPaint(gradient);
                g2d.fillRect(0, BallY[1], 1000, 100);
            }
            if (BallX[2] >= -100 && BallX[2] <= 150) {
                GradientPaint gradient = new GradientPaint(0, 0, G, 1000, 0, s, true);
                g2d.setPaint(gradient);
                g2d.fillRect(0, BallY[2], 1000, 100);
            }
            if (BallX[3] >= -100 && BallX[3] <= 150) {
                GradientPaint gradient = new GradientPaint(0, 0, R, 1000, 0, s, true);
                g2d.setPaint(gradient);
                g2d.fillRect(0, BallY[3], 1000, 100);
            }
            if (BallX[4] >= -100 && BallX[4] <= 150) {
                GradientPaint gradient = new GradientPaint(0, 0, O, 1000, 0, s, true);
                g2d.setPaint(gradient);
                g2d.fillRect(0, BallY[4], 1000, 100);
            }
            if (BallX[5] >= -100 && BallX[5] <= 150) {
                GradientPaint gradient = new GradientPaint(0, 0, Y, 1000, 0, s, true);
                g2d.setPaint(gradient);
                g2d.fillRect(0, BallY[5], 1000, 100);
            }
        }
    }

//    public Color getAlpha() {
//        double H = 0.1;
//        double S = 0.1;
//        double B = 0.1;
//        int rgb = Color.HSBtoRGB((float) H, (float) S, (float) B);
//        int red = (rgb >> 16) & 0xFF;
//        int green = (rgb >> 8) & 0xFF;
//        int blue = rgb & 0xFF;
//        Color color = new Color(red, green, blue, 0x33);
//        return color;
//    }
    private boolean collision() {
        Polygon a = new Polygon();
        Polygon b = new Polygon();
        Polygon c = new Polygon();
        Polygon d = new Polygon();
        Polygon e = new Polygon();
        Polygon f = new Polygon();
        a.addPoint(BallX[0] + 50, BallY[0]);
        a.addPoint(BallX[0] + 50, BallY[0] + 50);
        a.addPoint(BallX[0] + 50, BallY[0] + 100);
        a.addPoint(BallX[0], BallY[0]);
        a.addPoint(BallX[0], BallY[0] + ballsize);
        a.addPoint(BallX[0] + ballsize, BallY[0]);
        a.addPoint(BallX[0] + ballsize, BallY[0] + ballsize);
        b.addPoint(BallX[1] + 50, BallY[1]);
        b.addPoint(BallX[1] + 50, BallY[1] + 50);
        b.addPoint(BallX[1] + 50, BallY[1] + 100);
        b.addPoint(BallX[1], BallY[1]);
        b.addPoint(BallX[1], BallY[1] + ballsize);
        b.addPoint(BallX[1] + ballsize, BallY[1]);
        b.addPoint(BallX[1] + ballsize, BallY[1] + ballsize);
        c.addPoint(BallX[2] + 50, BallY[2]);
        c.addPoint(BallX[2] + 50, BallY[2] + 50);
        c.addPoint(BallX[2] + 50, BallY[2] + 100);
        c.addPoint(BallX[2], BallY[2]);
        c.addPoint(BallX[2], BallY[2] + ballsize);
        c.addPoint(BallX[2] + ballsize, BallY[2]);
        c.addPoint(BallX[2] + ballsize, BallY[2] + ballsize);
        d.addPoint(BallX[3] + 50, BallY[3]);
        d.addPoint(BallX[3] + 50, BallY[3] + 50);
        d.addPoint(BallX[3] + 50, BallY[3] + 100);
        d.addPoint(BallX[3], BallY[3]);
        d.addPoint(BallX[3], BallY[3] + ballsize);
        d.addPoint(BallX[3] + ballsize, BallY[3]);
        d.addPoint(BallX[3] + ballsize, BallY[3] + ballsize);
        e.addPoint(BallX[4] + 50, BallY[4]);
        e.addPoint(BallX[4] + 50, BallY[4] + 50);
        e.addPoint(BallX[4] + 50, BallY[4] + 100);
        e.addPoint(BallX[4], BallY[4]);
        e.addPoint(BallX[4], BallY[4] + ballsize);
        e.addPoint(BallX[4] + ballsize, BallY[4]);
        e.addPoint(BallX[4] + ballsize, BallY[4] + ballsize);
        f.addPoint(BallX[5] + 50, BallY[5]);
        f.addPoint(BallX[5] + 50, BallY[5] + 50);
        f.addPoint(BallX[5] + 50, BallY[5] + 100);
        f.addPoint(BallX[5], BallY[5]);
        f.addPoint(BallX[5], BallY[5] + ballsize);
        f.addPoint(BallX[5] + ballsize, BallY[5]);
        f.addPoint(BallX[5] + ballsize, BallY[5] + ballsize);
        return a.contains(sqx, sqy)
                || a.contains(sqx + size, sqy)
                || a.contains(sqx, sqy + size)
                || a.contains(sqx + size, sqy + size)
                || a.contains(sqx + 50, sqy + 100)
                || a.contains(sqx + 50, sqy + 50)
                || a.contains(sqx + 50, sqy)
                || b.contains(sqx, sqy)
                || b.contains(sqx + size, sqy)
                || b.contains(sqx, sqy + size)
                || b.contains(sqx + size, sqy + size)
                || b.contains(sqx + 50, sqy + 100)
                || b.contains(sqx + 50, sqy + 50)
                || b.contains(sqx + 50, sqy)
                || c.contains(sqx, sqy)
                || c.contains(sqx + size, sqy)
                || c.contains(sqx, sqy + size)
                || c.contains(sqx + size, sqy + size)
                || c.contains(sqx + 50, sqy + 100)
                || c.contains(sqx + 50, sqy + 50)
                || c.contains(sqx + 50, sqy)
                || d.contains(sqx + size, sqy)
                || d.contains(sqx, sqy + size)
                || d.contains(sqx + size, sqy + size)
                || d.contains(sqx + 50, sqy + 100)
                || d.contains(sqx + 50, sqy + 50)
                || d.contains(sqx + 50, sqy)
                || e.contains(sqx, sqy)
                || e.contains(sqx + size, sqy)
                || e.contains(sqx, sqy + size)
                || e.contains(sqx + size, sqy + size)
                || e.contains(sqx + 50, sqy + 100)
                || e.contains(sqx + 50, sqy + 50)
                || e.contains(sqx + 50, sqy)
                || f.contains(sqx, sqy)
                || f.contains(sqx + size, sqy)
                || f.contains(sqx, sqy + size)
                || f.contains(sqx + size, sqy + size)
                || f.contains(sqx + 50, sqy + 100)
                || f.contains(sqx + 50, sqy + 50)
                || f.contains(sqx + 50, sqy);
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    //not using the rest but must be here to override
    public void mouseClicked(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        //creates JFrame and adds Game to it
        jf = new JFrame("Dodge Ball");
        JFrameGame s = new JFrameGame();
        jf.add(s);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.pack();
        jf.setVisible(true);
        jf.getContentPane().setCursor(null);
        s.requestFocus(true);
    }
}
