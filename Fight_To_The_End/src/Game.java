import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends JPanel implements Runnable,KeyListener,MouseListener  {
      //Game state variable
    //gs0 - Menu
    //gs1 - Character selection
    //gs2 - Map selection
    //gs3 - Forest
    //gs4 - City
    //gs5 - Win
    //gs6 - Credits
    public static BufferedImage gs0;
    public static BufferedImage gs1;
    public static BufferedImage gs2;
    public static BufferedImage gs3;
    public static BufferedImage gs4;
    public static BufferedImage gs5;
    public static BufferedImage gs6;
    public static BufferedImage gs7;
    public static int gamestate = 0;

    //Character display
    public static BufferedImage assassin;
    public static BufferedImage fighter;
    public static BufferedImage mage;
    public static BufferedImage tank;
    //bullet
    public static BufferedImage redBullet;
    public static BufferedImage yellowBullet;
    public static BufferedImage greenBullet;
    //character in the game
    public static BufferedImage P1;
    public static BufferedImage P2;


    //character selection edge
    public static BufferedImage choose1;
    public static BufferedImage choose2;
    public static int numberOfCharacterChosen =0;
    public static boolean finishChoosing=false;
    public static BufferedImage ok;
    public static String p1Character="", p2Character="";

    // divide all the action image into left and right
    public static BufferedImage[] aLeft= new BufferedImage[8],aRight= new BufferedImage[8];
    public static BufferedImage[] mLeft= new BufferedImage[8],mRight=new BufferedImage[8];

    // initialize direction
    public static BufferedImage[] p1 ;
    public static BufferedImage[] p2 ;


    // keyboard setting
    public static boolean aPressed = false;
    public static boolean dPressed = false;
    public static boolean wPressed=false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean upPressed = false;


    // in game state
    public static boolean isRunning1 = false;
    public static boolean isRunning2 = false;
    public static boolean isAttacking1 = false;
    public static boolean isAttacking2 = false;
    public static boolean isJumping1=false;
    public static boolean isJumping2=false;
    public static boolean isFalling1=false;
    public static boolean isFalling2 = false;
    public static boolean freeFalling1 =false;
    public static boolean freeFalling2=false;

    //collision detection
    public static boolean standOnGround = false;
    public static boolean standOnObject1= false;
    public static boolean standOnObject2= false;
    public static boolean standOnObject3= false;
    public static boolean standOnObject4= false;
    public static boolean standOnGround2 = false;
    public static boolean standOnObject12= false;
    public static boolean standOnObject22= false;
    public static boolean standOnObject32= false;
    public static boolean standOnObject42= false;

    //initial position
    public static int p1x = 147;
    public static int p1y = 402;
    public static int p2x=768;
    public static int p2y=402;
    // in game action index
    public static int p1Index = 0;
    public static int p2Index = 0;
    //frame counter
    public static int p1FrameCounter = 0;
    public static int p2FrameCounter = 0;

    // jumpFalling velocity
    public static int velocity1 = -20;
    public static int velocity2 = -20;
    //freeFalling velocity
    public static int velocity3=0;
    public static int velocity4=0;
    //gravity
    public static int gravity1 =2;
    public static int gravity2=2;

    //bullets
    public static int p1AttackFrameCounter=0;
    public static int p2AttackFrameCounter=0;

    public static ArrayList<Bullet> bullets1 = new ArrayList<>();
    public static ArrayList<Bullet> bullets2 = new ArrayList<>();
    public static int bulletSpeed = 8;
    //health
    public static double p1health=5,p2health=5;
    // text File streaming
    public static boolean p1win=false;
    public static boolean p2win=true;

    public static String nameOfFile = "winningRate.txt";

    public Game(){
        setPreferredSize(new Dimension(900,600));
        //Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        //Add thread
        Thread thread = new Thread(this);
        thread.start();
    }


    public static void main(String[]args) throws IOException{
        //Image Importation
        gs0 =ImageIO.read(new File("gs0.png"));
        gs1 =ImageIO.read(new File("gs1.png"));
        gs2 =ImageIO.read(new File("gs2.png"));
        gs3 =ImageIO.read(new File("gs3.png"));
        gs4 =ImageIO.read(new File("gs4.png"));
        gs5 =ImageIO.read(new File("gs5.png"));
        gs6 =ImageIO.read(new File("gs6.png"));
        gs7=ImageIO.read(new File("gs7.png"));
        assassin =ImageIO.read(new File("assassin.png"));
        fighter =ImageIO.read(new File("fighter.png"));
        mage =ImageIO.read(new File("mage.png"));
        tank =ImageIO.read(new File("tank.png"));
        ok= ImageIO.read(new File("ok.png"));
        redBullet =ImageIO.read(new File("redBullet.png"));
        yellowBullet =ImageIO.read(new File("yellowbullet.png"));
        greenBullet =ImageIO.read(new File("greenbullet.png"));

        P1=ImageIO.read(new File("P1.png"));
        P2=ImageIO.read(new File("P2.png"));

        //0-not moving not attacking
        //1-not moving attacking
        //2-moving 1 not attacking
        //3-moving 2 not attacking
        //4-moving 1 attacking
        //5-moving 2 attacking
        //6-jumping not attacking
        //7-jumping attacking
        aLeft[0]=ImageIO.read(new File("aln0n.png"));
        aLeft[1]=ImageIO.read(new File("aln0a.png"));
        aLeft[2]=ImageIO.read(new File("alr1n.png"));
        aLeft[3]=ImageIO.read(new File("alr2n.png"));
        aLeft[4]=ImageIO.read(new File("alr1a.png"));
        aLeft[5]=ImageIO.read(new File("alr2a.png"));
        aLeft[6]=ImageIO.read(new File("alj0n.png"));
        aLeft[7]=ImageIO.read(new File("alj0a.png"));

        aRight[0]=ImageIO.read(new File("arn0n.png"));
        aRight[1]=ImageIO.read(new File("arn0a.png"));
        aRight[2]=ImageIO.read(new File("arr1n.png"));
        aRight[3]=ImageIO.read(new File("arr2n.png"));
        aRight[4]=ImageIO.read(new File("arr1a.png"));
        aRight[5]=ImageIO.read(new File("arr2a.png"));
        aRight[6]=ImageIO.read(new File("arj0n.png"));
        aRight[7]=ImageIO.read(new File("arj0a.png"));

        mLeft[0]=ImageIO.read(new File("mln0n.png"));
        mLeft[1]=ImageIO.read(new File("mln0a.png"));
        mLeft[2]=ImageIO.read(new File("mlr1n.png"));
        mLeft[3]=ImageIO.read(new File("mlr2n.png"));
        mLeft[4]=ImageIO.read(new File("mlr1a.png"));
        mLeft[5]=ImageIO.read(new File("mlr2a.png"));
        mLeft[6]=ImageIO.read(new File("mlj0n.png"));
        mLeft[7]=ImageIO.read(new File("mlj0a.png"));

        mRight[0]=ImageIO.read(new File("mrn0n.png"));
        mRight[1]=ImageIO.read(new File("mrn0a.png"));
        mRight[2]=ImageIO.read(new File("mrr1n.png"));
        mRight[3]=ImageIO.read(new File("mrr2n.png"));
        mRight[4]=ImageIO.read(new File("mrr1a.png"));
        mRight[5]=ImageIO.read(new File("mrr2a.png"));
        mRight[6]=ImageIO.read(new File("mrj0n.png"));
        mRight[7]=ImageIO.read(new File("mrj0a.png"));

        JFrame frame = new JFrame("The Endless Fight");
        Game panel = new Game();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }


    public void paintComponent (Graphics g){
        super.paintComponent(g);
        if(gamestate ==0){
            g.drawImage(gs0,0,0,null);
            p1health=5;
            p2health=5;
            bullets1.clear();
            bullets2.clear();
            p1x=147;
            p1y=402;
            p2x=768;
            p2y=402;
            p1=null;
            p2=null;

        }
        else if(gamestate ==1){
            g.drawImage(gs1,0,0,null);
            g.drawImage(assassin,315,211,null);
            g.drawImage(mage,532,215,null);
            g.drawImage(tank,313,434,null);
            g.drawImage(fighter,527,436,null);
            if(numberOfCharacterChosen>=1){
                g.drawImage(choose1,81,300,null);
            }
            if(numberOfCharacterChosen>=2){
                g.drawImage(choose2,771,307,null);
            }
            if(finishChoosing){
                g.drawImage(ok,727,465,null);
            }
        }
        else if(gamestate ==2){
            g.drawImage(gs2,0,0,null);
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if(gamestate ==3){
            if(p1==null&&p1Character.equals("assassin")){
                p1=aRight;
            }
            else if(p1==null&&p1Character.equals("mage")){
                p1=mRight;
            }
            if(p2==null&&p2Character.equals("assassin")){
                p2=aLeft;
            }
            else if(p2==null&&p2Character.equals("mage")){
                p2=mLeft;
            }

            g.drawImage(gs3,0,0,null);
            p1AttackFrameCounter++;
            if(p1AttackFrameCounter>15) p1AttackFrameCounter=15;
            if (isAttacking1){
                if (p1AttackFrameCounter ==15) {
                    if(p1Character.equals("assassin")){
                        if(p1==aRight) bullets1.add(new Bullet(p1x + 32, p1y + 30, p1 == aRight));
                        else bullets1.add(new Bullet(p1x -26, p1y + 30, p1 == aRight));
                    }
                    else if(p1Character.equals("mage")){
                        if(p1==mRight) bullets1.add(new Bullet(p1x + 32, p1y + 30, p1 == mRight));
                        else bullets1.add(new Bullet(p1x -26, p1y + 30, p1 == mRight));
                    }

                    p1AttackFrameCounter=0;
                }
            }

            isRunning1= aPressed || dPressed;
            //movement image selection
            if(wPressed){
                if(isAttacking1){
                    p1Index=7;
                }
                else p1Index=6;
            }
            else if(isRunning1) {
                p1FrameCounter++;
                if(p1FrameCounter==4)
                {
                    if(isAttacking1)
                    {
                        if(p1Index!=4&&p1Index!=5)p1Index = 4;
                        p1Index++;
                        p1FrameCounter = 0;
                        if(p1Index==6) p1Index=4;
                    }
                    else {
                        if(p1Index!=2&&p1Index!=3)p1Index = 2;
                        p1Index++;
                        p1FrameCounter = 0;
                        if(p1Index==4) p1Index=2;
                    }
                }

            }
            else if (isAttacking1){
                p1Index=1;
            }
            else {
                p1Index=0;
            }
            //horizontal movement and the change of left and right action image array.
            if(aPressed) {
                p1x-=5;
                if(p1Character.equals("assassin"))p1=aLeft;
                if(p1Character.equals("mage"))p1=mLeft;
            }
            if(dPressed) {
                p1x+=5;
                if(p1Character.equals("assassin"))p1=aRight;
                if(p1Character.equals("mage"))p1=mRight;
            }

            //jumping and falling system
                // the four obstacles in the middle of the map
            standOnGround=p1y + 78 > 478;
            standOnObject1= p1y + 78 <= 404&&p1y + 78 >= 374&&p1x+34>=138 && p1x<=752;
            standOnObject2= p1y + 78 <= 314&&p1y + 78 >= 283&&p1x+34>=350 && p1x<=540;
            standOnObject3= p1y + 78 <= 209&&p1y + 78 >= 179&&p1x+34>=350 && p1x<=540;
            standOnObject4= p1y + 78 <= 107&&p1y + 78 >= 77&&p1x+34>=244 && p1x<=644;

            if(wPressed){
               isJumping1=true;
            }

            if(isJumping1){
                p1y+=velocity1;
                velocity1+=gravity1;
                if(velocity1>20) velocity1=20;
                isFalling1 = velocity1>0;
            }
            if (standOnGround&&isFalling1) {
                p1y = 402;
                velocity1 = -20;
                velocity3=0;
                isJumping1=false;
                freeFalling1=false;

            }
            else if (standOnObject1&&isFalling1) {
                p1y = 312;
                velocity1 = -20;
                velocity3=0;
                isJumping1=false;
                freeFalling1=false;
            }
            else if (standOnObject2&&isFalling1) {
                isJumping1=false;
                p1y = 222;
                velocity1 = -20;
                velocity3=0;
                freeFalling1=false;
            }
            else if (standOnObject3&&isFalling1) {
                isJumping1=false;
                p1y = 120;
                velocity1 = -20;
                velocity3=0;
                freeFalling1=false;

            }
            else if (standOnObject4&&isFalling1) {
                isJumping1=false;
                p1y = 16;
                velocity1 = -20;
                velocity3=0;
                freeFalling1=false;
            }
            else if((p1x<224&&p1x>=57||p1x+33>=667&&p1x+33<=835)&&p1y+78>=254&&p1y+78<=279&&isFalling1){
                isJumping1=false;
                p1y=186;
                velocity1=-20;
                velocity3=0;
                freeFalling1=false;
            }
            else if((p1x+33>=60&&p1x<131||p1x+33>=763&&p1x<=832)&&p1y+78>=92&&p1y+78<=117&&isFalling1){
                isJumping1=false;
                p1y=26;
                velocity1=-20;
                velocity3=0;
                freeFalling1=false;
            }
            // freeFalling system
            else if(!isJumping1&&!standOnGround)freeFalling1=true;


            if(freeFalling1){
                isJumping1=false;
                p1y+=velocity3;
                // velocity1=-20
                velocity3+=gravity1;
                if(velocity3>20) velocity3=20;
                freeFalling1=false;
            }

            //boundary of left and right
            if(p1x<0){
                p1x=0;
            }
            if(p1x+34>900){
                p1x=866;
            }
            // resistance when character are outsides the two woods
            if(p1x+33>53&&p1x+33<63&&p1y>26&&p1y<300){
                p1x=20;
            }
            if(p1x>830&&p1x<840&&p1y>26&&p1y<300){
                p1x=836;
            }
            // resistance when characters are inside the two wood
            if(p1x<=68&&p1x>=58&&p1y+78<=264&&p1y>=117){
                p1x=67;
            }
            if(p1x+33>820&&p1x+33<830&&p1y+78<=264&&p1y>=115){
                p1x=791;
            }
            // resistance when the characters collide the top in the two wood.
            if((p1x+33>=60&&p1x<131||p1x+33>=763&&p1x<=832)&&p1y<=119&&p1y>=110&&!isFalling1){
                velocity1=0;
            }
            if((p1x<=218&&p1x>=20||p1x+33>=667&&p1x+33<=865)&&p1y<=280&&p1y>=273&&!isFalling1){
                velocity1=0;
            }
            // resistance of the small side of the wood from the lower left wood.
            if(p1x<=228&&p1x>=219&&p1y<273&&p1y>180){
                p1x=224;
            }
            // resistance of the small side of the wood from the upper left wood.
            if(p1x<=135&&p1x>=126&&p1y<110&&p1y>33){
                p1x=131;
            }
            // resistance of the small side of the wood from the lower right wood.
            if(p1x+33<=681&&p1x+33>=667&&p1y<273&&p1y>191){
                p1x=640;
            }
            // resistance of the small side of the wood from the upper right wood.
            if(p1x+33<=776&&p1x+33>=763&&p1y<110&&p1y>33){
                p1x=734;
            }

            //shooting bullets


            g.drawImage(p1[p1Index],p1x,p1y,null);
            g.drawImage(P1,p1x,p1y-20,null);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            isRunning2= leftPressed || rightPressed;
            p2AttackFrameCounter++;
            if(p2AttackFrameCounter>15) p2AttackFrameCounter=15;
            if (isAttacking2) {
                if (p2AttackFrameCounter == 15) {
                    if(p2Character.equals("assassin")){
                        if(p2==aRight) bullets2.add(new Bullet(p2x + 32, p2y + 30, p2 == aRight));
                        else bullets2.add(new Bullet(p2x -26, p2y + 30, p2 == aRight));
                    }
                    else if(p2Character.equals("mage")){
                        if(p2==mRight) bullets2.add(new Bullet(p2x + 32, p2y + 30, p2 == mRight));
                        else bullets2.add(new Bullet(p2x -26, p2y + 30, p2 == mRight));
                    }
                    p2AttackFrameCounter=0;
                }
            }
            //movement
            if(upPressed){
                if(isAttacking2){
                    p2Index=7;
                }
                else p2Index=6;
            }
            else if(isRunning2) {
                p2FrameCounter++;
                if(p2FrameCounter==4)
                {
                    if(isAttacking2)
                    {
                        if(p2Index!=4&&p2Index!=5)p2Index = 4;
                        p2Index++;
                        p2FrameCounter = 0;
                        if(p2Index==6) p2Index=4;
                    }
                    else {
                        if(p2Index!=2&&p2Index!=3)p2Index = 2;
                        p2Index++;
                        p2FrameCounter = 0;
                        if(p2Index==4) p2Index=2;
                    }
                }

            }
            else if (isAttacking2){
                p2Index=1;
            }
            else {
                p2Index = 0;
            }
            //horizontal movement
            if(leftPressed) {
                p2x-=5;

                if(p2Character.equals("assassin"))
                {
                    p2=aLeft;
                }
                else if (p2Character.equals("mage")){
                    p2=mLeft;
                }
            }
            if(rightPressed) {
                p2x+=5;
                if(p2Character.equals("assassin"))
                {
                    p2=aRight;
                }
                else if (p2Character.equals("mage")){
                    p2=mRight;
                }
            }




            //jumping
            standOnGround2=p2y + 78 > 478;
            standOnObject12= p2y + 78 <= 404&&p2y + 78 >= 374&&p2x+34>=138 && p2x<=752;
            standOnObject22= p2y + 78 <= 314&&p2y + 78 >= 283&&p2x+34>=350 && p2x<=535;
            standOnObject32= p2y + 78 <= 209&&p2y + 78 >= 179&&p2x+34>=350 && p2x<=531;
            standOnObject42= p2y + 78 <= 107&&p2y + 78 >= 77&&p2x+34>=244 && p2x<=644;
            if(upPressed){
                isJumping2=true;
            }

            if(isJumping2){
                p2y+=velocity2;
                velocity2+=gravity2;
                if(velocity2>25) velocity2=25;
                isFalling2 = velocity2>0;

            }
            if (standOnGround2&&isFalling2) {
                p2y = 402;
                velocity2 = -20;
                velocity4=0;
                isJumping2=false;
                freeFalling2=false;

            }
            else if (standOnObject12&&isFalling2) {
                p2y = 312;
                velocity2 = -20;
                velocity4=0;
                isJumping2=false;
                freeFalling2=false;
            }
            else if (standOnObject22&&isFalling2) {
                isJumping2=false;
                p2y = 222;
                velocity2 = -20;
                velocity4=0;
                freeFalling2=false;

            }
            else if (standOnObject32&&isFalling2) {
                isJumping2=false;
                p2y = 120;
                velocity2 = -20;
                velocity4=0;
                freeFalling2=false;

            }
            else if (standOnObject42&&isFalling2) {
                isJumping2=false;
                p2y = 16;
                velocity2 = -20;
                velocity4=0;
                freeFalling2=false;
            }
            else if((p2x<224&&p2x>=57||p2x+33>=667&&p2x+33<=835)&&p2y+78>=254&&p2y+78<=279&&isFalling2){
                isJumping2=false;
                p2y=186;
                velocity2=-20;
                velocity4=0;
                freeFalling2=false;
            }
            else if((p2x+33>=60&&p2x<=131||p2x+33>=763&&p2x<=832)&&p2y+78>=92&&p2y+78<=117&&isFalling2){
                isJumping2=false;
                p2y=26;
                velocity2=-20;
                velocity4=0;
                freeFalling2=false;
            }
            else if(!isJumping2&&!standOnGround2)freeFalling2=true;

            if(freeFalling2){
                isJumping2=false;
                //velocity2=-20;
                p2y+=velocity4;
                velocity4+=gravity2;
                if(velocity4>25) velocity4=25;
                freeFalling2=false;
            }


            //boundary
            if(p2x<0){
                p2x=0;
            }
            if(p2x+34>900){
                p2x=866;
            }
            // two woods on the sides

            if(p2x+33>53&&p2x+33<63&&p2y>26&&p2y<300){
                p2x=20;
            }
            if(p2x>830&&p2x<840&&p2y>26&&p2y<300){
                p2x=836;
            }
            if(p2x<=68&&p2x>=58&&p2y+78<=264&&p2y>=117){
                p2x=67;
            }
            if(p2x+33>820&&p2x+33<830&&p2y+78<=264&&p2y>=115){
                p2x=791;
            }
            //upper wood supporting
            if((p2x+33>=60&&p2x<131||p2x+33>=763&&p2x<=832)&&p2y<=119&&p2y>=110&&!isFalling2){
                velocity2=0;
            }
            //lower wood supporting
            if((p2x<=218&&p2x>=20||p2x+33>=667&&p2x+33<=865)&&p2y<=280&&p2y>=273&&!isFalling2){
                velocity2=0;
            }

            if(p2x<=228&&p2x>=219&&p2y<273&&p2y>180){
                p2x=224;
            }
            if(p2x<=135&&p2x>=126&&p2y<110&&p2y>33){
                p2x=131;
            }
            if(p2x+33<=681&&p2x+33>=667&&p2y<273&&p2y>191){
                p2x=640;
            }
            if(p2x+33<=776&&p2x+33>=763&&p2y<110&&p2y>33){
                p2x=734;
            }

            g.drawImage(p2[p2Index],p2x,p2y,null);
            g.drawImage(P2,p2x,p2y-20,null);
            //bullet shooting logic


            // Update and draw bullets

//////////////////////////////////////////////////////////////////////////////////////////////////////////
            for (int i = 0; i < bullets2.size(); i++) {

                Bullet b = bullets2.get(i);
                b.move();
                //bounce back collision detection
                if(b.x<=0||b.x>=900) b.meetCounter++;
                if(p2Character.equals("assassin")) {
                    if(b.meetCounter>=3){
                        bullets2.remove(i);
                    }
                }
                else if(b.meetCounter>=2) bullets2.remove(i);

                g.drawImage(greenBullet, b.x, b.y, null);
                // Check collision with P1
                if (b.x + greenBullet.getWidth() >= p1x && b.x <= p1x + p1[p1Index].getWidth() &&
                        b.y + greenBullet.getHeight() >= p1y && b.y <= p1y + p1[p1Index].getHeight()) {
                    // Collision detected with P1
                    bullets2.remove(i);
                    i--;
                    if(p2Character.equals("mage")){
                        p1health-=1.5;
                    }
                    else p1health--;
                }

            }
            // Collision detection for bullets fired by P1
            for (int i = 0; i < bullets1.size(); i++) {
                Bullet b = bullets1.get(i);
                b.move();
                if(b.x<=0||b.x>=900) b.meetCounter++;
                if(p1Character.equals("assassin")) {
                    if(b.meetCounter>=3)
                    {
                    bullets1.remove(i);
                    }
                }
                else if(b.meetCounter>=2) bullets1.remove(i);
                g.drawImage(redBullet, b.x, b.y, null);
                // Check collision with P2
                if (b.x + redBullet.getWidth() >= p2x && b.x <= p2x + p2[p2Index].getWidth() &&
                        b.y + redBullet.getHeight() >= p2y && b.y <= p2y + p2[p2Index].getHeight()) {
                    // Collision detected with P2
                    bullets1.remove(i);
                    i--;
                    if(p1Character.equals("mage")){
                        p2health-=1.5;
                    }
                    else p2health--;

                    // Handle damage to P2 (for example, decrease health)
                    // Example: p2Health -= bulletDamage;
                }

            }
            g.setColor(new Color(138, 3, 3));
            g.fillRect(40,550,(int)(p1health*40),30);
            g.fillRect((int)(860-p2health*40),550,(int)(p2health*40),30);
            g.setColor(new Color(250,250,250));
            g.drawRect(40,550,200,30);
            g.drawRect(660,550,200,30);
            g.setFont(new Font("Dense Typeface", Font.BOLD,25));
            g.drawString("P1",40,535);
            g.drawString("P2",660,535);

        }
////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if(gamestate ==4){
            g.drawImage(gs4,0,0,null);

        }
        else if(gamestate ==5){
            g.drawImage(gs5,0,0,null);
            g.setFont(new Font("Dense Typeface", Font.BOLD,75));
            g.setColor(new Color(250,250,250));
            if(p1health<=0&&p2health<=0) {
                g.drawString("No One",300,270);
            }
            else if(p2health<=0) {
                g.drawString("p1",300,270);
                p1win=true;
            }
            else if(p1health<=0) {
                g.drawString("p2",300,270);
                p2win=true;
            }

        }
        else if(gamestate ==6){
            g.drawImage(gs6,0,0,null);

        }
        else if(gamestate==7){
            g.drawImage(gs7,0,0,null);
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(gamestate==3||gamestate==4){
            if(e.getKeyChar()=='-'){
                gamestate=0;
                repaint();
            }
            if(e.getKeyChar()=='='){
                gamestate=5;
                repaint();
            }
            //p1 movement
            if(e.getKeyCode()==32) {
                isAttacking1 = true;
            }
            if(e.getKeyChar()=='a')
            {
                aPressed = true;
            }
            else if(e.getKeyChar()=='d')
            {
                dPressed = true;
            }
            if(e.getKeyChar()=='w')
            {
                wPressed = true;
            }
            //p2 movement
            if(e.getKeyCode()==37)//ArrowLeft
            {
                leftPressed=true;
            }
            if(e.getKeyCode()==38)//Arrowup
            {
                upPressed=true;
            }
            if(e.getKeyCode()==39)//ArrowRight
            {
                rightPressed=true;
            }
            if(e.getKeyChar()==10)//enter
            {
                isAttacking2=true;
            }
        }
        else if(gamestate==5){
            if(e.getKeyChar()=='n'){
                gamestate = 0;
                repaint();
            }
        }
        else if(gamestate==6){
            if(e.getKeyCode()==27){
                gamestate = 0;
                repaint();
            }
        }
        else if(gamestate==7){
            if(e.getKeyCode()==27){
                gamestate = 0;
                repaint();
            }
        }

    }
    public void keyReleased(KeyEvent e) {
        //p1 movement
        if(e.getKeyChar()=='a')
        {
            aPressed = false;
        }
        if(e.getKeyChar()=='d')
        {
            dPressed = false;
        }
        if(e.getKeyChar()=='w')
        {
            wPressed = false;
        }

        if(e.getKeyCode()==32)//space
        {
            isAttacking1 = false;
        }
        //p2 movement
        if(e.getKeyCode()==37)//Arrow left
        {
            leftPressed = false;
        }
        if(e.getKeyCode()==39) //Arrow right
        {
            rightPressed = false;
        }

        if(e.getKeyCode()==10) //enter
        {
            isAttacking2 = false;
        }
        if(e.getKeyCode()==38)//Arrow up
        {
            upPressed=false;
        }
    }

    public void run() {
        while (true) {
            //Set up frame rate
            try {
                Thread.sleep(20); // 20 ms per frame <=> 50 frame per second
                if((p1health<=0||p2health<=0)&&(gamestate==3)){
                    Thread.sleep(600);
                    gamestate=5;
                }
            }
            catch (Exception e){}
            repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        if(gamestate == 0){
            if(e.getX() >=313 &&e.getX() <=585&& e.getY()>=315&& e.getY() <=406&&e.getButton()==1){
                gamestate = 1;
                repaint();
            }
            if(e.getX() >=313&&e.getX() <=439&& e.getY()>=444&& e.getY() <=534&&e.getButton()==1){
                gamestate = 6;
                repaint();
            }
            if(e.getX() >=459&&e.getX() <=585&& e.getY()>=444&& e.getY() <=534&&e.getButton()==1){
                gamestate = 7;
                repaint();
            }
        }
        else if(gamestate==1){
            if(numberOfCharacterChosen==0){
                p1Character="";
                p2Character="";
            }
            if(e.getX() >=235 &&e.getX() <=440&& e.getY()>=160&& e.getY() <=360&&e.getButton()==1){
                numberOfCharacterChosen++;
                if(numberOfCharacterChosen==1) {
                    choose1=assassin;
                    p1Character="assassin";
                }
                if(numberOfCharacterChosen==2) {
                    choose2=assassin;
                    p2Character="assassin";
                }
            }
            else if(e.getX() >=450 &&e.getX() <=660&& e.getY()>=160&& e.getY() <=360&&e.getButton()==1){
                numberOfCharacterChosen++;
                if(numberOfCharacterChosen==1) {
                    choose1=mage;
                    p1Character="mage";
                }
                if(numberOfCharacterChosen==2) {
                    choose2=mage;
                    p2Character="mage";
                }
            }

            else if(e.getX() >=235 &&e.getX() <=440&& e.getY()>=378&& e.getY() <=582&&e.getButton()==1){
                numberOfCharacterChosen++;
                if(numberOfCharacterChosen==1) {
                    choose1=tank;
                    p1Character="tank";
                }
                if(numberOfCharacterChosen==2) {
                    choose2=tank;
                    p2Character="tank";
                }
            }
            else if(e.getX() >=450 &&e.getX() <=660&& e.getY()>=378&& e.getY() <=582&&e.getButton()==1){
                numberOfCharacterChosen++;
                if(numberOfCharacterChosen==1) {
                    choose1=fighter;
                    p1Character="fighter";
                }
                if(numberOfCharacterChosen==2) {
                    choose2=fighter;
                    p2Character="fighter";
                }
            }
            repaint();

            if(numberOfCharacterChosen ==2){
                finishChoosing = true;
                repaint();
            }
            if(numberOfCharacterChosen>2){
                numberOfCharacterChosen=2;
            }
            if(numberOfCharacterChosen==2&&e.getX() >=727 &&e.getX() <=867&& e.getY()>=465&& e.getY() <=534&&e.getButton()==1)
            {
                gamestate = 2;
                numberOfCharacterChosen = 0;
                finishChoosing = false;
                repaint();
            }

        }
        else if(gamestate==2){
            if(e.getX() >=180 &&e.getX() <=421&& e.getY()>=223&& e.getY() <=520&&e.getButton()==1){
                gamestate = 3;
                repaint();
            }
            if(e.getX() >=490&&e.getX() <=732&& e.getY()>=223&& e.getY() <=520&&e.getButton()==1){
                gamestate = 4;
                repaint();
            }
        }
    }
    //useless method
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    /**/
    public static class Bullet{
        int x, y;
        boolean rightDirection;
        int meetCounter;
        public Bullet(int x, int y, boolean rightDirection) {
            this.x = x;
            this.y = y;
            this.rightDirection = rightDirection;
        }
        public void move() {
            if(bulletMeetObstacles()){
                rightDirection=!rightDirection;
            }
            if (rightDirection)
            {
                x += bulletSpeed;
            }
            else {
                x -= bulletSpeed;
            }
        }
        public boolean bulletMeetObstacles(){
            return x<0||x>900;
        }
    }
}
