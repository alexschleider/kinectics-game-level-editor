import processing.core.*; import noc.*; import proxml.*; import java.applet.*; import java.awt.*; import java.awt.image.*; import java.awt.event.*; import java.io.*; import java.net.*; import java.text.*; import java.util.*; import java.util.zip.*; import javax.sound.midi.*; import javax.sound.midi.spi.*; import javax.sound.sampled.*; import javax.sound.sampled.spi.*; import java.util.regex.*; import javax.xml.parsers.*; import javax.xml.transform.*; import javax.xml.transform.dom.*; import javax.xml.transform.sax.*; import javax.xml.transform.stream.*; import org.xml.sax.*; import org.xml.sax.ext.*; import org.xml.sax.helpers.*; public class PuzzleGameLevelEditorV2 extends PApplet {


PFont font48;
Walker myWalkers;
ArrayList myWalkersList;
Wall myWalls;
ArrayList myWallsList;
Floor myFloors;
ArrayList myFloorsList;
Chooser myChoosers;
ArrayList myChoosersList;
OtherObjects myObjects;
ArrayList myObjectsList;
Jumper myJumpers;
ArrayList myJumpersList;
Booster myBoosters;
ArrayList myBoostersList;
Slower mySlowers;
ArrayList mySlowersList;
MovingPlatform myPlatforms;
ArrayList myPlatformsList;
ArrayList myButtonsList;
Goal myGoal;
int gameLevel = 1;
boolean mouseIsDragging = false;
boolean levelFinished = false;
float score;
float bonusScore;
boolean scoreChanged = false;
boolean buttonJustHit;
boolean testMode = false;
boolean modeMore = false;
float fadeTimer;
boolean fadeText = false;
boolean fadingIn;
boolean fadingOut;
float onscreenTime;
String fadingText;
float fadeX;
float fadeY;
int fadeFont;
boolean drawingEditBox;
boolean updatingObject;
int whatIsSelected;
int editType;
boolean enteringText;
String enterText = "Enter Number";

PImage Background;
PImage backgroundFront;
int editMode;
final static int NULL_MODE = 0;
final static int CHOOSER_MODE = 1;
final static int WALL_MODE = 2;
final static int FLOOR_MODE = 3;
final static int WALKER_MODE = 4;
final static int PLATFORM_MODE = 5;
final static int SWITCH_MODE = 6;
final static int GOAL_MODE = 7;
final static int DELETE_MODE = 8;
final static int SAVE_MODE = 9;
final static int LOAD_MODE = 10;
final static int New = 8;
final static int Move = 9;
final static int EditSize = 10;
final static int WallFloorType = 0;
final static int Amount = 9;
final static int Type = 10;
final static int SpeedTime = 9;
final static int DistDir = 8;
final static int DeleteKey = 0;
final static int Dir = 10;




public void setup()
{
  smooth();
  size(640, 480);
  font48 = loadFont ("HelveticaNeue-Bold-48.vlw");
  textFont(font48);
  myWallsList = new ArrayList();
  myFloorsList = new ArrayList();
  myWalkersList = new ArrayList();
  myChoosersList = new ArrayList();
  myObjectsList = new ArrayList();
  myJumpersList = new ArrayList();
  myBoostersList = new ArrayList();
  myPlatformsList = new ArrayList();
  mySlowersList = new ArrayList();
  myButtonsList = new ArrayList();
  drawLevel();
  Background = loadImage("background.png");
  backgroundFront = loadImage("backgroundfront.png");
  myButtonsList.add (new ModeButton(545, 449, 0));
  myButtonsList.add (new ModeButton(130, 38, 2));
  myButtonsList.add (new ModeButton(200, 38, 3));
  myButtonsList.add (new ModeButton(270, 38, 4));
  myButtonsList.add (new ModeButton(340, 38, 1));
  myButtonsList.add (new ModeButton(410, 38, 5));
  myButtonsList.add (new ModeButton(480, 38, 6));
  myButtonsList.add (new ModeButton(550, 38, 7));
  myButtonsList.add (new ModeButton(245, 449, 8));
  myButtonsList.add (new ModeButton(345, 449, 9));
  myButtonsList.add (new ModeButton(445, 449, 10));
  myGoal = new Goal (-100, -100);
  levelTemp();
}

public void levelTemp()
{
  
}

public void draw()
{
  for(int i=0; i<myButtonsList.size(); i++)
    {
      ModeButton dc = (ModeButton)myButtonsList.get(i);
  if(!drawingEditBox)
       {
        if (dc.buttonType == 12 || dc.buttonType == 13 || dc.buttonType == 14 || dc.buttonType == 15) 
        {
          myButtonsList.remove(i);
         i--;
       }
    }
    }
  if(!levelFinished)
  {
    //background(128);
    image(Background, 0 ,0);
    myGoal.draw();
    if (!scoreChanged) bonusScore--;
    scoreChanged = !scoreChanged;
    if (bonusScore <= 0) bonusScore = 0;

    for(int i=0; i<myFloorsList.size(); i++) 
    {
      Floor b = (Floor)myFloorsList.get(i);
      b.draw();
    }
    for(int i=0; i<myWallsList.size(); i++) 
    {
      Wall b = (Wall)myWallsList.get(i);
      b.draw();
    }
    for(int i=0; i<myPlatformsList.size(); i++) 
    {
      MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
      b.draw();
    }
    for(int i=0; i<myObjectsList.size(); i++)
    {
      OtherObjects b = (OtherObjects)myObjectsList.get(i);
      b.draw();
    }
    
    for(int i=0; i<myWalkersList.size(); i++)
    {
      Walker b = (Walker)myWalkersList.get(i);
      b.draw();
    }
    if (testMode) checkProgress();
  }
  if(testMode)
  {
    image(backgroundFront, 0 ,0);
    levelText();
    fill(0);
    noStroke();
    textAlign(LEFT);
    textSize(12);
    text("Level", 175, 30);
    text("Bonus", 325, 30);
    text("Score", 475, 30);
    textAlign(CENTER);
    textSize(20);
    text(gameLevel, 192, 53);
    text(PApplet.parseInt(bonusScore), 342, 53);
    text(PApplet.parseInt(score), 492, 53);
  } else {
    textFont(font48);
    fill(0);
    noStroke();
    textAlign(LEFT);
    textSize(16);
   switch(editMode)
  {
   case NULL_MODE:
   if(!testMode) text("Select Mode", 100, 453);
   break;
 
   case CHOOSER_MODE:
   text("Chooser Mode", 100, 453);
   break;
 
   case WALL_MODE:
   text("Wall Mode", 100, 453);
   break;
 
   case FLOOR_MODE:
   text("Floor Mode", 100, 453);
   break;
 
   case WALKER_MODE:
   text("Walker Mode", 100, 453);
   break;
 
   case PLATFORM_MODE:
   text("Platform Mode", 100, 453);
   break;
 
   case SWITCH_MODE:
   text("Switch Mode", 100, 453);
   break;
 
   case GOAL_MODE:
   text("Goal Mode", 100, 453);
   break;
 
   case DELETE_MODE:
   text("Delete Mode", 100, 453);
   break;
 
   case SAVE_MODE:
   text("Save Mode", 100, 453);
   break;
 
   case LOAD_MODE:
   text("Load Mode", 100, 453);
   break;
  } 
  }
    for(int i=0; i<myChoosersList.size(); i++) 
    {
      Chooser b = (Chooser)myChoosersList.get(i);
      b.draw();
    }for(int i=0; i<myBoostersList.size(); i++)
    {
      Booster b = (Booster)myBoostersList.get(i);
      b.draw();
    }
    for(int i=0; i<mySlowersList.size(); i++)
    {
      Slower b = (Slower)mySlowersList.get(i);
      b.draw();
    }
    for(int i=0; i<myJumpersList.size(); i++)
    {
      Jumper b = (Jumper)myJumpersList.get(i);
      b.draw();
    }
  if (drawingEditBox) updateEditBox();
    for(int i=0; i<myButtonsList.size(); i++)
    {
      ModeButton b = (ModeButton)myButtonsList.get(i);
      if (!testMode) b.draw();
      if (testMode && b.buttonType == 0) b.draw();
    }
    
 
  if (editMode != PLATFORM_MODE) modeMore = false;
  if (fadeText) updateFadeText();
}

public void drawLevel()
{
}

public void checkProgress()
{
  for(int i=0; i<myObjectsList.size(); i++)
    {
      OtherObjects b = (OtherObjects)myObjectsList.get(i);
      for(int x=1; x<30; x++)
      {
        if (b.hitTest(x, false))
        {
          for(int j=0; j<myFloorsList.size(); j++)
            {
              Floor bd = (Floor)myFloorsList.get(j);
                if (bd.floorType == x) 
                 {
                  bd.isDead = !bd.isDead;
                 }
             }
             for(int j=0; j<myWallsList.size(); j++)
            {
              Wall bd = (Wall)myWallsList.get(j);
                if (bd.wallType == x) 
                 {
                  bd.isDead = !bd.isDead;
                 }
             }
             for(int j=0; j<myPlatformsList.size(); j++)
            {
              MovingPlatform bd = (MovingPlatform)myPlatformsList.get(j);
                if (bd.floorType == x) 
                 {
                  bd.isDead = !bd.isDead;
                 }
             }
        }
        
        
        
      }
    }
  //Check Goal Collision
  if (myGoal.hitTest()) finishedLevel();

}

public void finishedLevel()
{
  textSize(50);
  textAlign(CENTER);
  text("Level Finished", 355, 160);
  score = score + 1025 * gameLevel;
  score = score + bonusScore;
  levelFinished = true;
}

public void mousePressed() {
  
  
  
if (testMode)
{
  
  for(int i=0; i<myJumpersList.size(); i++) {
    Jumper dc = (Jumper)myJumpersList.get(i);
    dc.mousePressed();
  }

  for(int i=0; i<myBoostersList.size(); i++) {
    Booster dc = (Booster)myBoostersList.get(i);
    dc.mousePressed();
  }

  for(int i=0; i<mySlowersList.size(); i++) {
    Slower dc = (Slower)mySlowersList.get(i);
    dc.mousePressed();
  }

  for(int i=0; i<myChoosersList.size(); i++) {
    Chooser dc = (Chooser)myChoosersList.get(i);
    if(dc.hitDetect() == true ) 
    {
      if(dc.amount > 0)
      {
        if(dc.chooserType == 1) myJumpersList.add( new Jumper( dc.pos.x, dc.pos.y) );
        if(dc.chooserType == 2) myBoostersList.add( new Booster( dc.pos.x, dc.pos.y) );
        if(dc.chooserType == 3) mySlowersList.add( new Slower( dc.pos.x, dc.pos.y) );
        dc.amount --;
      }

    }
  }
  for(int i=0; i<myButtonsList.size(); i++) {
    ModeButton dc = (ModeButton)myButtonsList.get(i);
    if (dc.hitTest(mouseX, mouseY))
   { 
    if (dc.buttonType == 0) 
    {
       for(int x=0; x<myJumpersList.size(); x++)
     {
       Jumper d = (Jumper)myJumpersList.get(x);
       d.pos.y = 1000;
       }
       for(int x=0; x<myBoostersList.size(); x++)
     {
       Booster d = (Booster)myBoostersList.get(x);
       d.pos.y = 1000;
       }
       for(int x=0; x<mySlowersList.size(); x++)
     {
       Slower d = (Slower)mySlowersList.get(x);
       d.pos.y = 1000;
       }
       for(int x=0; x<myPlatformsList.size(); x++)
     {
       MovingPlatform d = (MovingPlatform)myPlatformsList.get(x);
       d.pos = d.startPos;
       }
       for(int x=0; x<myObjectsList.size(); x++)
     {
       OtherObjects d = (OtherObjects)myObjectsList.get(x);
       d.switchHit = false;
       }
       for(int j=0; j<myFloorsList.size(); j++)
            {
              Floor bd = (Floor)myFloorsList.get(j);
                  bd.isDead = false;
             }
             for(int j=0; j<myWallsList.size(); j++)
            {
              Wall bd = (Wall)myWallsList.get(j);
                  bd.isDead = false;
             }
      testMode = !testMode;
    editMode = NULL_MODE;
  for(int x=0; x<myWalkersList.size(); x++) 
  {
    Walker d = (Walker)myWalkersList.get(x);
    d.pos = d.originalPos.copy();
    d.vel = new Vector3D(0, 0);
    d.gravity = new Vector3D(0, 0);
    d.onFloor = false;
    d.walkingRight = d.direction;
  }
   }
  }
  }
} else {
  myGoal.mousePressed();
  for(int i=0; i<myPlatformsList.size(); i++) {
    MovingPlatform dc = (MovingPlatform)myPlatformsList.get(i);
    dc.mousePressed();
  }
  for(int i=0; i<myWallsList.size(); i++) {
    Wall dc = (Wall)myWallsList.get(i);
    dc.mousePressed();
  }
  for(int i=0; i<myObjectsList.size(); i++)
  {
    OtherObjects dc = (OtherObjects)myObjectsList.get(i);
    dc.mousePressed();
  }
  for(int i=0; i<myFloorsList.size(); i++) {
    Floor dc = (Floor)myFloorsList.get(i);
    dc.mousePressed();
  }
  for(int i=0; i<myWalkersList.size(); i++) {
    Walker dc = (Walker)myWalkersList.get(i);
    dc.mousePressed();
  }
  myGoal.mousePressed();
  for(int i=0; i<myButtonsList.size(); i++) {
    ModeButton dc = (ModeButton)myButtonsList.get(i);
    if (dc.hitTest(mouseX, mouseY))
   { 
     if (!drawingEditBox)
     {
    if (dc.buttonType == 2) editMode = 2;
    if (dc.buttonType == 3) editMode = 3;
    if (dc.buttonType == 5) editMode = 5;
    if (dc.buttonType != 0 && dc.buttonType <= 7) editMode = dc.buttonType;
    if (dc.buttonType == 0 && editMode != 2 && editMode != 3 && editMode != 5) 
    {
      testMode = !testMode;
      for(int x=0; x<myWalkersList.size(); x++)
     {
       Walker d = (Walker)myWalkersList.get(x);
       d.originalPos = d.pos.copy();
       d.vel = new Vector3D (0, 0);
       d.gravity = new Vector3D (0, 0.1f);
       d.onFloor = false;
       }
       for(int x=0; x<myPlatformsList.size(); x++)
     {
       MovingPlatform d = (MovingPlatform)myPlatformsList.get(x);
       d.startPos = d.pos.copy();
       if(d.horizontal) d.endPos = new Vector3D (d.pos.x + d.distance, d.pos.y);
        else d.endPos = new Vector3D (d.pos.x, d.pos.y + d.distance);
       d.isDead = !d.deletes;
       }
       for(int j=0; j<myFloorsList.size(); j++)
            {
              Floor bd = (Floor)myFloorsList.get(j);
                 if (!bd.deletes) bd.isDead = true;
                 else bd.isDead = false;
             }
             for(int j=0; j<myWallsList.size(); j++)
            {
              Wall bd = (Wall)myWallsList.get(j);
                  if (!bd.deletes) bd.isDead = true;
                 else bd.isDead = false;
             }
    }
    if (editMode != 5) modeMore = false;
    if (dc.buttonType == 0 && editMode == 5) modeMore = !modeMore;
    if (dc.buttonType == DELETE_MODE && editMode == NULL_MODE) 
    {
      editMode = DELETE_MODE;
    }
    if (dc.buttonType == 9 && editMode == DELETE_MODE) 
    {
      editMode = NULL_MODE;
    }
    if (editMode == NULL_MODE && editType == 9) 
    {
    editMode = SAVE_MODE;
    editType = 0;
    }
    if (editMode == NULL_MODE && editType == 10) 
    {
      editMode = LOAD_MODE;
      editType = 0;
    }
    
    editType = dc.buttonType;
     } else {
       if (dc.buttonType == 9) 
    {
    editMode = SAVE_MODE;
    editType = 0;
    }
     }
     
   }
  }
  
   switch(editMode)
{
 case NULL_MODE:
 
 break;
 
 case CHOOSER_MODE:
 for(int i=0; i<myButtonsList.size(); i++) {
    ModeButton dc = (ModeButton)myButtonsList.get(i);
    if (dc.hitTest(mouseX, mouseY))
   {
     switch(dc.buttonType)
     {
       case New:
       if (myChoosersList.size() >=3) drawFadingText(new String ("Choosers Maxed Out"), 30, 340, 250, 100);
       if (myChoosersList.size() == 2) myChoosersList.add( new Chooser( 35, 200, 1, 0));
       if (myChoosersList.size() == 1) myChoosersList.add( new Chooser( 35, 150, 1, 0));
       if (myChoosersList.size() == 0) myChoosersList.add( new Chooser( 35, 100, 1, 0));
       break;
       
       case Amount:
       updatingObject = true;
       break;
       
       case Type:
       updatingObject = true;
       break;
     }
   }
  }
  for(int x=0; x<myChoosersList.size(); x++) {
  Chooser d = (Chooser)myChoosersList.get(x);
           if(!drawingEditBox) d.thisChooser = false; 
          if (d.hitDetect()) 
          {
            if (updatingObject)
            {
            drawEditBox();
            updatingObject = false;
            } 
            d.thisChooser = true;
          }
        }
 break;
 
 case WALL_MODE:
 for(int i=0; i<myButtonsList.size(); i++) {
    ModeButton dc = (ModeButton)myButtonsList.get(i);
    if (dc.hitTest(mouseX, mouseY))
   {
     switch(dc.buttonType)
     {
       case New:
       myWallsList.add( new Wall( mouseX, mouseY, 15, 100, 0, true));
       editType = Move;
       break;
       
       case DeleteKey:
       updatingObject = true;
       break;
     }
   }
  }
  for(int x=0; x<myWallsList.size(); x++) {
  Wall d = (Wall)myWallsList.get(x);
           if(!drawingEditBox) d.thisWall = false; 
          if (d.hitTest(mouseX, mouseY)) 
          {
            if (updatingObject)
            {
              drawEditBox();
              updatingObject = false;
            }
          d.thisWall = true;
          }
        }
 break;
 
 case FLOOR_MODE:
 for(int i=0; i<myButtonsList.size(); i++) {
    ModeButton dc = (ModeButton)myButtonsList.get(i);
    if (dc.hitTest(mouseX, mouseY))
   {
     switch(dc.buttonType)
     {
       case New:
       myFloorsList.add( new Floor( mouseX, mouseY, 100, 15, 0, true));
       editType = Move;
       break;
       
       case DeleteKey:
       updatingObject = true;
       break;
     }
   }
  }
  
  for(int x=0; x<myFloorsList.size(); x++) {
  Floor d = (Floor)myFloorsList.get(x);
           if(!drawingEditBox) d.thisFloor = false; 
          if (d.hitTest(mouseX, mouseY)) 
          {
            if (updatingObject)
            {
              drawEditBox();
              updatingObject = false;
            }
            d.thisFloor = true;
          }
        }
 break;
 
 case WALKER_MODE:
 switch (editType)
 {
   case New:
   myWalkersList.add( new Walker( mouseX, mouseY, true));
   editType = Move;
   break;
   
   case Dir:
   updatingObject = true;
   break;
 }
 
 for(int x=0; x<myWalkersList.size(); x++) {
  Walker d = (Walker)myWalkersList.get(x);
           if(!drawingEditBox) d.thisWalker = false; 
          if (d.hitDetect(mouseX, mouseY)) 
          {
            if (updatingObject)
            {
              drawEditBox();
              updatingObject = false;
            }
            d.thisWalker = true;
          }
        }
 break;
 
 case PLATFORM_MODE:
 updatingObject = false;
 if (!modeMore && editType == New)
 {
   myPlatformsList.add (new MovingPlatform(mouseX, mouseY, 100, 15, 1, 100, 100, true, true, 0, true));
   editType = Move;
 } else {
 switch(editType)
 {
 case SpeedTime:
   if (modeMore) updatingObject = true;
 break;
 
 case DistDir:
  if (modeMore) updatingObject = true;
 break;
 
 case Type:
  if (modeMore) updatingObject = true;
 break;
   }
 }
 
  for(int x=0; x<myPlatformsList.size(); x++) {
  MovingPlatform d = (MovingPlatform)myPlatformsList.get(x);
           if(!drawingEditBox) d.thisPlatform = false; 
          if (d.hitTest(mouseX, mouseY)) 
          {
            if (updatingObject)
            {
              drawEditBox();
              updatingObject = false;
            }
            d.thisPlatform = true;
          }
        }
 
 break;
 
 case SWITCH_MODE:
 switch(editType)
 {
   case New:
   myObjectsList.add( new OtherObjects( mouseX, mouseY, 0, 1));
   editType = Move;
   break;
   
   case Type:
   updatingObject = true;
   break;
 }
 
 for(int x=0; x<myObjectsList.size(); x++) {
  OtherObjects d = (OtherObjects)myObjectsList.get(x);
           if(!drawingEditBox) d.thisObject = false; 
          if (d.hitDetect(mouseX, mouseY)) 
          {
            if (updatingObject)
            {
              drawEditBox();
              updatingObject = false;
            }
            d.thisObject = true;
          }
        }
 break;
 
 case GOAL_MODE:
 if (editType == New)
 {
 myGoal = null;
 myGoal = new Goal(mouseX, mouseY);
 myGoal.isDragging = true;
 myGoal.isMouseOver = true;
 myGoal.mouseIsDragging = true;
 editType = Move;
 }
 break;
 
 case DELETE_MODE:
   for(int i=0; i<myPlatformsList.size(); i++) {
    MovingPlatform dc = (MovingPlatform)myPlatformsList.get(i);
    if (dc.thisPlatform) myPlatformsList.remove(i);
    else dc.thisPlatform = true;
  }
  for(int i=0; i<myWallsList.size(); i++) {
    Wall dc = (Wall)myWallsList.get(i);
    if (dc.thisWall) myWallsList.remove(i);
    else dc.thisWall = true;
  }
  for(int i=0; i<myFloorsList.size(); i++) {
    Floor dc = (Floor)myFloorsList.get(i);
    if (dc.thisFloor) myFloorsList.remove(i);
    else dc.thisFloor = true;
  }
  for(int i=0; i<myWalkersList.size(); i++)
  {
    Walker dc = (Walker)myWalkersList.get(i);
    if (dc.thisWalker) myWalkersList.remove(i);
    else dc.thisWalker = true;
  }
  for(int i=0; i<myObjectsList.size(); i++)
  {
    OtherObjects dc = (OtherObjects)myObjectsList.get(i);
    if (dc.thisObject) myObjectsList.remove(i);
    else dc.thisObject = true;
  }
 break;
 
 case SAVE_MODE:
 drawEditBox();
 break;
 
 case LOAD_MODE:
 drawEditBox();
 break;
}
for(int i=0; i<myPlatformsList.size(); i++) {
    MovingPlatform dc = (MovingPlatform)myPlatformsList.get(i);
    if (!drawingEditBox) dc.thisPlatform = false;
    if (dc.hitTest(mouseX, mouseY)) dc.thisPlatform = true;
  }
  for(int i=0; i<myWallsList.size(); i++) {
    Wall dc = (Wall)myWallsList.get(i);
    if (!drawingEditBox) dc.thisWall = false;
    if (dc.hitTest(mouseX, mouseY) && !drawingEditBox) dc.thisWall = true;
  }
  for(int i=0; i<myFloorsList.size(); i++) {
    Floor dc = (Floor)myFloorsList.get(i);
    if (!drawingEditBox) dc.thisFloor = false;
    if (dc.hitTest(mouseX, mouseY) && !drawingEditBox) dc.thisFloor = true;
  }
  for(int i=0; i<myWalkersList.size(); i++)
  {
    Walker dc = (Walker)myWalkersList.get(i);
    if (!drawingEditBox) dc.thisWalker = false;
    if (dc.hitDetect(mouseX, mouseY) && !drawingEditBox) dc.thisWalker = true;
  }
  for(int i=0; i<myObjectsList.size(); i++)
  {
    OtherObjects dc = (OtherObjects)myObjectsList.get(i);
    if (!drawingEditBox) dc.thisObject = false;
    if (dc.hitDetect(mouseX, mouseY) && !drawingEditBox) dc.thisObject = true;
  }
  myGoal.thisGoal = false;
  if (myGoal.hitDetect(mouseX, mouseY)) myGoal.thisGoal = true;

  
}
  if (levelFinished == true)
  {
    
    levelFinished = false;
  }
}

public void mouseReleased() {
  if (testMode)
  {

  for(int i=0; i<myJumpersList.size(); i++) {
    Jumper dc = (Jumper)myJumpersList.get(i);
    dc.mouseReleased();
  }

  for(int i=0; i<myBoostersList.size(); i++) {
    Booster dc = (Booster)myBoostersList.get(i);
    dc.mouseReleased();
  }

  for(int i=0; i<mySlowersList.size(); i++) {
    Slower dc = (Slower)mySlowersList.get(i);
    dc.mouseReleased();
  }

  for(int i=0; i<myChoosersList.size(); i++) {
    Chooser dc = (Chooser)myChoosersList.get(i);
    dc.mouseReleased();
  }
} else {
  myGoal.mouseReleased();
  for(int i=0; i<myPlatformsList.size(); i++) {
    MovingPlatform dc = (MovingPlatform)myPlatformsList.get(i);
    dc.mouseReleased();
  }
  for(int i=0; i<myWallsList.size(); i++) {
    Wall dc = (Wall)myWallsList.get(i);
    dc.mouseReleased();
  }
  for(int i=0; i<myFloorsList.size(); i++) {
    Floor dc = (Floor)myFloorsList.get(i);
    dc.mouseReleased();
  }
  for(int i=0; i<myWalkersList.size(); i++)
  {
    Walker dc = (Walker)myWalkersList.get(i);
    dc.mouseReleased();
  }
  for(int i=0; i<myObjectsList.size(); i++)
  {
    OtherObjects dc = (OtherObjects)myObjectsList.get(i);
    dc.mouseReleased();
  }
  myGoal.mouseReleased();
}
}

public void keyReleased()
{
  for(int i=0; i<myWallsList.size(); i++) {
    Wall dc = (Wall)myWallsList.get(i);
    dc.keyReleased();
  }
  for(int i=0; i<myFloorsList.size(); i++) {
    Floor dc = (Floor)myFloorsList.get(i);
    dc.keyReleased();
  }
  for(int i=0; i<myPlatformsList.size(); i++) {
    MovingPlatform dc = (MovingPlatform)myPlatformsList.get(i);
    dc.keyReleased();
  }
}

public void drawFadingText(String _fadeText, int _fontSize, float _x, float _y, float fadeTime)
{
  onscreenTime = fadeTime - 50;
  fadeTimer = 0;
  fadeText = true;
  fadingIn = true;
  fadingText = _fadeText;
  fadeX = _x;
  fadeY = _y;
  fadeFont = _fontSize;
}

public void updateFadeText()
{
  if (fadingIn || fadingOut) fill(0, fadeTimer * 10.2f);
  else fill(0);
  textSize(fadeFont);
  text(fadingText, fadeX, fadeY);
  if (fadingIn) fadeTimer++;
  else fadeTimer--;
  if (fadingIn && fadeTimer >= 25)
  {
    fadingIn = false;
    fadeTimer = onscreenTime;
  }
  if (!fadingIn && !fadingOut && fadeTimer <= 0)
  {
    fadingIn = false;
    fadeTimer = 25;
    fadingOut = true;
  }
  if (fadingOut && fadeTimer <= 0)
  {
    fadingIn = false;
    fadeTimer = 0;
    fadingOut = false;
    fadeText = false;
  }
  
}

public void keyPressed()
{
  if (enteringText)
  {
  switch(editMode)
  {
   case CHOOSER_MODE:
   if (editType == Amount)
   {
     if(key!=CODED)
    {
      if(enterText == "Enter Number") enterText = "";
      if(key==BACKSPACE && enterText.length() > 0) enterText = enterText.substring(0, enterText.length()-1);
      else if(key==ENTER)
      {
        if(enterText.length()>0)
        {
          for(int i=0; i<myChoosersList.size(); i++)
          {
            Chooser dc = (Chooser)myChoosersList.get(i);
            if (dc.thisChooser) 
            {
              dc.amount = parseInt(enterText);
              enterText = "Enter Number";
              dc.thisChooser = false;
            }
          }
          enteringText = false;
          drawingEditBox = false;
        }
      }
    else 
      {
        if (key == '0' || key == '1' || key == '2' || key == '3' || key == '4' || key == '5' || key == '6' || key == '7' || key == '8' || key == '9'|| key == '.') enterText+=key;
      }
      return;
    }
   }
   break;
 
   case WALL_MODE:
   if (editType == DeleteKey)
   {
     if(key!=CODED)
    {
      if(enterText == "Enter Number") enterText = "";
      if(key==BACKSPACE && enterText.length() > 0) enterText = enterText.substring(0, enterText.length()-1);
      else if(key==ENTER)
      {
        if(enterText.length()>0)
        {
          for(int i=0; i<myWallsList.size(); i++)
          {
            Wall dc = (Wall)myWallsList.get(i);
            if (dc.thisWall) 
            {
              dc.wallType = parseInt(enterText);
              enterText = "Enter Number";
              dc.thisWall = false;
            }
          }
          enteringText = false;
          drawingEditBox = false;
        }
      }
    else 
      {
        if (key == '0' || key == '1' || key == '2' || key == '3' || key == '4' || key == '5' || key == '6' || key == '7' || key == '8' || key == '9'|| key == '.') enterText+=key;
      }
      return;
    }
   }
   break;
 
   case FLOOR_MODE:
   if (editType == DeleteKey)
   {
     if(key!=CODED)
    {
      if(enterText == "Enter Number") enterText = "";
      if(key==BACKSPACE && enterText.length() > 0) enterText = enterText.substring(0, enterText.length()-1);
      else if(key==ENTER)
      {
        if(enterText.length()>0)
        {
          for(int i=0; i<myFloorsList.size(); i++)
          {
            Floor dc = (Floor)myFloorsList.get(i);
            if (dc.thisFloor) 
            {
              dc.floorType = parseInt(enterText);
              enterText = "Enter Number";
              dc.thisFloor = false;
            }
          }
          enteringText = false;
          drawingEditBox = false;
        }
      }
    else 
      {
        if (key == '0' || key == '1' || key == '2' || key == '3' || key == '4' || key == '5' || key == '6' || key == '7' || key == '8' || key == '9'|| key == '.') enterText+=key;
      }
      return;
    }
   }
   break;
   
   case PLATFORM_MODE:
   if(key!=CODED)
    {
      if(enterText == "Enter Number") enterText = "";
      if(key==BACKSPACE && enterText.length() > 0) enterText = enterText.substring(0, enterText.length()-1);
      else if(key==ENTER)
      {
        if(enterText.length()>0)
        {
          for(int i=0; i<myPlatformsList.size(); i++)
          {
            MovingPlatform dc = (MovingPlatform)myPlatformsList.get(i);
            if (dc.thisPlatform) 
            {
              if (editType == Type && modeMore) dc.floorType = parseInt(enterText);
              if (editType == DistDir && modeMore) dc.speed = parseInt(enterText);
              if (editType == SpeedTime && modeMore) dc.holdTime = parseInt(enterText);
              enterText = "Enter Number";
              dc.thisPlatform = false;
            }
          }
          enteringText = false;
          drawingEditBox = false;
        }
      }
    else 
      {
        if (key == '0' || key == '1' || key == '2' || key == '3' || key == '4' || key == '5' || key == '6' || key == '7' || key == '8' || key == '9'|| key == '.') enterText+=key;
      }
      return;
    }
   break;
 
   case SWITCH_MODE:
   if (editType == Type)
   {
     if(key!=CODED)
    {
      if(enterText == "Enter Number") enterText = "";
      if(key==BACKSPACE && enterText.length() > 0) enterText = enterText.substring(0, enterText.length()-1);
      else if(key==ENTER)
      {
        if(enterText.length()>0)
        {
          for(int i=0; i<myObjectsList.size(); i++)
          {
            OtherObjects dc = (OtherObjects)myObjectsList.get(i);
            if (dc.thisObject) 
            {
              dc.whichSwitch = parseInt(enterText);
              enterText = "Enter Number";
              dc.thisObject = false;
            }
          }
          enteringText = false;
          drawingEditBox = false;
        }
      }
    else 
      {
        if (key == '0' || key == '1' || key == '2' || key == '3' || key == '4' || key == '5' || key == '6' || key == '7' || key == '8' || key == '9'|| key == '.') enterText+=key;
      }
      return;
    }
   }
   break;
 
   case SAVE_MODE:
     if(key!=CODED)
    {
      if(enterText == "Enter Filename") enterText = "";
      if(key==BACKSPACE && enterText.length() > 0) enterText = enterText.substring(0, enterText.length()-1);
      else if(key==ENTER)
      {
        if(enterText.length()>0)
        {
          saveLevel();
          enteringText = false;
          drawingEditBox = false;
        }
      }
    else 
      {
        enterText+=key;
      }
      return;
    }
   break;
 
   case LOAD_MODE:
     if(key!=CODED)
    {
      if(enterText == "Enter Filename") enterText = "";
      if(key==BACKSPACE && enterText.length() > 0) enterText = enterText.substring(0, enterText.length()-1);
      else if(key==ENTER)
      {
        if(enterText.length()>0)
        {
          loadLevel();
          enteringText = false;
          drawingEditBox = false;
        }
      }
    else 
      {
        enterText+=key;
      }
      return;
    }
   
   break;
  }
  }
}

public void drawEditBox()
{
  drawingEditBox = true;
  switch(editMode)
{
 case NULL_MODE:
 
 break;
 
 case CHOOSER_MODE:
     switch(editType)
     {
       case Amount:
       enteringText = true;
       break;
       
       case Type:
        myButtonsList.add (new ModeButton(320, 200, 12));
        myButtonsList.add (new ModeButton(320, 250, 13));
        myButtonsList.add (new ModeButton(320, 300, 14));
       
       break;
   }
  
 break;
 
 case WALL_MODE:
 if (editType == DeleteKey)
 {
   enteringText = true;
   myButtonsList.add (new ModeButton(285, 300, 12));
   myButtonsList.add (new ModeButton(355, 300, 13));
 }
 break;
 
 case FLOOR_MODE:
 if (editType == DeleteKey)
 {
   enteringText = true;
   myButtonsList.add (new ModeButton(285, 300, 12));
   myButtonsList.add (new ModeButton(355, 300, 13));
 }
 break;
 
 case WALKER_MODE:
 if (editType == Dir)
 {
   myButtonsList.add (new ModeButton(285, 250, 12));
   myButtonsList.add (new ModeButton(355, 250, 13));
 }
 break;
 
 case PLATFORM_MODE:
 if (modeMore)
 {
  switch(editType)
 {
  case SpeedTime:
       enteringText = true;
   myButtonsList.add (new ModeButton(315, 290, 12));
  break;
  
  case DistDir:
       enteringText = true;
   myButtonsList.add (new ModeButton(285, 270, 12));
   myButtonsList.add (new ModeButton(355, 270, 13));
   myButtonsList.add (new ModeButton(285, 310, 14));
   myButtonsList.add (new ModeButton(355, 310, 15));
  break;
  
  case Type:
       enteringText = true;
   myButtonsList.add (new ModeButton(285, 300, 12));
   myButtonsList.add (new ModeButton(355, 300, 13));
  break;
 } 
 }
 break;
 
 case SWITCH_MODE:
 if (editType == Dir) enteringText = true;
 break;
 
 case GOAL_MODE:
 
 break;
 
 case DELETE_MODE:
 
 break;
 
 case SAVE_MODE:
     enterText = "Enter Filename";
 enteringText = true;
 break;
 
 case LOAD_MODE:
     enterText = "Enter Filename";
 enteringText = true;
 break;
}
}

public void updateEditBox()
{
  fill(255);
  stroke(0);
  rectMode(CENTER);
  rect(width/2, height/2, 150, 200);
     switch(editMode)
{
 case NULL_MODE:
 
 break;
 
 case CHOOSER_MODE:
     switch(editType)
     {
       case Amount:
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Amount", width/2, height/2 - 70);
       textSize(12);
       text("Type in Amount", width/2, height/2 - 50);
       textSize(20);
       text(enterText, width/2, height/2);
       break;
       
       case Type:
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Type", width/2, height/2 - 70);
       if (mousePressed == true)
       {
         for(int i=0; i<myButtonsList.size(); i++)
         {
           ModeButton dc = (ModeButton)myButtonsList.get(i);
           if (dc.hitTest(mouseX, mouseY))
           {
             for(int x=0; x<myChoosersList.size(); x++)
         {
           Chooser d = (Chooser)myChoosersList.get(x);
           if (dc.buttonType == 12)
           {
             if(d.thisChooser) d.chooserType = 1;
       drawingEditBox = false;
       d.thisChooser = false;
           }
           
          if (dc.buttonType == 13)
           {
             if(d.thisChooser) d.chooserType = 2;
       drawingEditBox = false;
       d.thisChooser = false;
             
           }
           
           if (dc.buttonType == 14)
           {
             if(d.thisChooser) d.chooserType = 3;
       drawingEditBox = false;
       d.thisChooser = false;
           }
           }
         }
       if(!drawingEditBox)
       {
        if (dc.buttonType == 12) myButtonsList.remove(i);
        if (dc.buttonType == 13) myButtonsList.remove(i);
        if (dc.buttonType == 14) myButtonsList.remove(i);
        i--;
       }
       }
       }
       break;
  }
 break;
 
 case WALL_MODE:
 if (editType == DeleteKey)
 {
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Amount", width/2, height/2 - 70);
       textSize(12);
       text("Type in Amount", width/2, height/2 - 50);
       textSize(20);
       text(enterText, width/2, height/2); 
       if (mousePressed == true)
       {
         for(int i=0; i<myButtonsList.size(); i++)
         {
           ModeButton dc = (ModeButton)myButtonsList.get(i);
           if (dc.hitTest(mouseX, mouseY))
           {
             for(int x=0; x<myWallsList.size(); x++)
         {
           Wall d = (Wall)myWallsList.get(x);
           if (dc.buttonType == 12)
           {
             if(d.thisWall) d.deletes = true;
       drawingEditBox = false;
       enteringText = false;
       d.thisWall = false;
           }
           
          if (dc.buttonType == 13)
           {
             if(d.thisWall) d.deletes = false;
       drawingEditBox = false;
       d.thisWall = false;
       enteringText = false;
             
           }
           }
         }
       if(!drawingEditBox)
       {
        if (dc.buttonType == 12) myButtonsList.remove(i);
        if (dc.buttonType == 13) myButtonsList.remove(i);
        i--;
       }
       }
       }
 }
 break;
 
 case FLOOR_MODE:
 if (editType == DeleteKey)
 {
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Amount", width/2, height/2 - 70);
       textSize(12);
       text("Type in Amount", width/2, height/2 - 50);
       textSize(20);
       text(enterText, width/2, height/2); 
       if (mousePressed == true)
       {
         for(int i=0; i<myButtonsList.size(); i++)
         {
           ModeButton dc = (ModeButton)myButtonsList.get(i);
           if (dc.hitTest(mouseX, mouseY))
           {
             for(int x=0; x<myFloorsList.size(); x++)
         {
           Floor d = (Floor)myFloorsList.get(x);
           if (dc.buttonType == 12)
           {
             if(d.thisFloor) d.deletes = true;
       drawingEditBox = false;
       enteringText = false;
       d.thisFloor = false;
           }
           
          if (dc.buttonType == 13)
           {
             if(d.thisFloor) d.deletes = false;
       drawingEditBox = false;
       d.thisFloor = false;
       enteringText = false;
             
           }
           }
         }
       if(!drawingEditBox)
       {
        if (dc.buttonType == 12) myButtonsList.remove(i);
        if (dc.buttonType == 13) myButtonsList.remove(i);
        i--;
       }
       }
       }
 }
 break;
 
 case WALKER_MODE:
 if (editType == Dir)
 {
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Direction", width/2, height/2 - 70);
       if (mousePressed == true)
       {
         for(int i=0; i<myButtonsList.size(); i++)
         {
           ModeButton dc = (ModeButton)myButtonsList.get(i);
           if (dc.hitTest(mouseX, mouseY))
           {
             for(int x=0; x<myWalkersList.size(); x++)
         {
           Walker d = (Walker)myWalkersList.get(x);
           if (dc.buttonType == 12)
           {
             if(d.thisWalker) 
             {
             d.direction = false;
             d.walkingRight = false;
             }
       drawingEditBox = false;
       d.thisWalker = false;
           }
           
          if (dc.buttonType == 13)
           {
             if(d.thisWalker) 
             {
               d.direction = true;
              d.walkingRight = true; 
             }
       drawingEditBox = false;
       d.thisWalker = false;
             
           }
           }
         }
       if(!drawingEditBox)
       {
        if (dc.buttonType == 12) myButtonsList.remove(i);
        if (dc.buttonType == 13) myButtonsList.remove(i);
        i--;
       }
       }
       }
 }
 break;
 
 case PLATFORM_MODE:
 if (modeMore)
 {
  switch(editType)
     {
       case DistDir:
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Speed", width/2, height/2 - 70);
       textSize(12);
       text("Type in Amount", width/2, height/2 - 50);
       textSize(20);
       text(enterText, width/2, height/2); 
       if (mousePressed == true)
       {
         for(int i=0; i<myButtonsList.size(); i++)
         {
           ModeButton dc = (ModeButton)myButtonsList.get(i);
           if (dc.hitTest(mouseX, mouseY))
           {
             for(int x=0; x<myPlatformsList.size(); x++)
         {
           MovingPlatform d = (MovingPlatform)myPlatformsList.get(x);
           if (dc.buttonType == 12)
           {
             if(d.thisPlatform) d.horizontal = false;
       drawingEditBox = false;
       enteringText = false;
       d.thisPlatform = false;
           }
           
          if (dc.buttonType == 13)
           {
             if(d.thisPlatform) d.horizontal = true;
       drawingEditBox = false;
       d.thisPlatform = false;
       enteringText = false;
             
           }
           if (dc.buttonType == 14)
           {
             if(d.thisPlatform) d.dir = true;
       drawingEditBox = false;
       d.thisPlatform = false;
       enteringText = false;
             
           }
           if (dc.buttonType == 15)
           {
             if(d.thisPlatform) 
             {
               d.dir = false;
             }
       drawingEditBox = false;
       d.thisPlatform = false;
       enteringText = false;
             
           }
           }
         }
       if(!drawingEditBox)
       {
        if (dc.buttonType == 12) myButtonsList.remove(i);
        if (dc.buttonType == 13) myButtonsList.remove(i);
        if (dc.buttonType == 14) myButtonsList.remove(i);
        if (dc.buttonType == 15) myButtonsList.remove(i);
        i--;
       }
       }
       }
       break;
       
       case SpeedTime:
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Time", width/2, height/2 - 70);
       textSize(12);
       text("Type in Amount", width/2, height/2 - 50);
       textSize(20);
       text(enterText, width/2, height/2); 
       if (mousePressed == true)
       {
         for(int i=0; i<myButtonsList.size(); i++)
         {
           ModeButton dc = (ModeButton)myButtonsList.get(i);
           if (dc.hitTest(mouseX, mouseY))
           {
             for(int x=0; x<myPlatformsList.size(); x++)
         {
           MovingPlatform d = (MovingPlatform)myPlatformsList.get(x);
           if (dc.buttonType == 12)
           {
             if(d.thisPlatform) d.editDistance = true;
       drawingEditBox = false;
       enteringText = false;
       d.thisPlatform = false;
           }
           }
         }
       if(!drawingEditBox)
       {
        if (dc.buttonType == 12) myButtonsList.remove(i);
        i--;
       }
       }
       }
       break;
       
       case Type:
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Choose Amount", width/2, height/2 - 70);
       textSize(12);
       text("Type in Amount", width/2, height/2 - 50);
       textSize(20);
       text(enterText, width/2, height/2); 
       if (mousePressed == true)
       {
         for(int i=0; i<myButtonsList.size(); i++)
         {
           ModeButton dc = (ModeButton)myButtonsList.get(i);
           if (dc.hitTest(mouseX, mouseY))
           {
             for(int x=0; x<myPlatformsList.size(); x++)
         {
           MovingPlatform d = (MovingPlatform)myPlatformsList.get(x);
           if (dc.buttonType == 12)
           {
             if(d.thisPlatform) d.deletes = true;
       drawingEditBox = false;
       enteringText = false;
       d.thisPlatform = false;
           }
           
          if (dc.buttonType == 13)
           {
             if(d.thisPlatform) d.deletes = false;
       drawingEditBox = false;
       d.thisPlatform = false;
       enteringText = false;
             
           }
           }
         }
       if(!drawingEditBox)
       {
        if (dc.buttonType == 12) myButtonsList.remove(i);
        if (dc.buttonType == 13) myButtonsList.remove(i);
        i--;
       }
       }
       }
       break;
  }
 }
 break;
 
 case SWITCH_MODE:
 if (editType == Dir)
 {
  
       textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Give Number", width/2, height/2 - 70);
       textSize(12);
       text("Type in Amount", width/2, height/2 - 50);
       textSize(20);
       text(enterText, width/2, height/2); 
 }
 break;
 
 case GOAL_MODE:
 
 break;
 
 case DELETE_MODE:
 
 break;
 
 case SAVE_MODE:
 textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Save File", width/2, height/2 - 70);
       textSize(12);
       text("Type in File Name", width/2, height/2 - 50);
       textSize(16);
       text(enterText, width/2, height/2); 
 break;
 
 case LOAD_MODE:
 textAlign(CENTER);
       fill(0);
       textSize(16);
       text("Load File", width/2, height/2 - 70);
       textSize(12);
       text("Type in File Name", width/2, height/2 - 50);
       textSize(16);
       text(enterText, width/2, height/2); 
 break;
}
}

public int convertBoolean(boolean _test)
{
  if (_test) return 1;
  else return 0;
}

public boolean convertInt(int _test)
{
  if (_test == 1) return true;
  else return false;
}

public void saveLevel()
{
  XMLElement root = new XMLElement("level");
  for(int i=0; i<myChoosersList.size(); i++)
  {
    Chooser d = (Chooser)myChoosersList.get(i);
    root.addChild(d.toXML());
  }
  for(int i=0; i<myFloorsList.size(); i++)
  {
    Floor d = (Floor)myFloorsList.get(i);
    root.addChild(d.toXML());
  }
  for(int i=0; i<myPlatformsList.size(); i++)
  {
    MovingPlatform d = (MovingPlatform)myPlatformsList.get(i);
    root.addChild(d.toXML());
  }
  for(int i=0; i<myObjectsList.size(); i++)
  {
    OtherObjects d = (OtherObjects)myObjectsList.get(i);
    root.addChild(d.toXML());
  }
  for(int i=0; i<myWalkersList.size(); i++)
  {
    Walker d = (Walker)myWalkersList.get(i);
    root.addChild(d.toXML());
  }
  for(int i=0; i<myWallsList.size(); i++)
  {
    Wall d = (Wall)myWallsList.get(i);
    root.addChild(d.toXML());
  }
  root.addChild(myGoal.toXML());

  XMLInOut xmlIO = new XMLInOut(this);
  xmlIO.saveElement(root, enterText+".xml");

  editMode = NULL_MODE;
}

public void loadLevel()
{
  XMLInOut xmlIO = new XMLInOut(this);
  XMLElement root = xmlIO.loadElementFrom(enterText+".xml");

  myChoosersList.clear();
  myFloorsList.clear();
  myPlatformsList.clear();
  myObjectsList.clear();
  myWalkersList.clear();
  myWallsList.clear();
  myGoal = null;

  for(int i=0; i<root.countChildren(); i++)
  {
    XMLElement node = root.getChild(i);
    println(node.getName());
    
    if(node.getName().equals("chooser"))
      myChoosersList.add(new Chooser(node));
    else if(node.getName().equals("floor"))
      myFloorsList.add(new Floor(node));
    else if(node.getName().equals("platform"))
      myPlatformsList.add(new MovingPlatform(node));
    else if(node.getName().equals("switch"))
      myObjectsList.add(new OtherObjects(node));
    else if(node.getName().equals("walker"))
      myWalkersList.add(new Walker(node));
    else if(node.getName().equals("wall"))
      myWallsList.add(new Wall(node));
    else if(node.getName().equals("goal"))
      myGoal = new Goal(node);
  }

  editMode = NULL_MODE;
}

//Collision Detection
//Walker-Wall Detection
public Vector3D lineIntersectionWalkerWall(Walker walker, Wall l1) {
  if(!walker.walkingRight) return lineIntersection(walker.pos.x - walker.walkerWidth * .5f, walker.pos.y - walker.walkerHeight * .5f, walker.pos.x + walker.walkerWidth * .5f, walker.pos.y + walker.walkerHeight * .5f, l1.inX + l1.wallWidth, l1.inY, l1.outX + l1.wallWidth, l1.outY);
  else return lineIntersection(walker.pos.x - walker.walkerWidth * .5f, walker.pos.y - walker.walkerHeight * .5f, walker.pos.x + walker.walkerWidth * .5f, walker.pos.y + walker.walkerHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Walker-Floor Detection
public Vector3D lineIntersectionWalkerFloor(Walker walker, Floor l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5f, walker.pos.y - walker.walkerHeight * .5f, walker.pos.x + walker.walkerWidth * .5f, walker.pos.y + walker.walkerHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Walker-FloorBottom Detection
public Vector3D lineIntersectionWalkerFloorBottom(Walker walker, Floor l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5f, walker.pos.y - walker.walkerHeight * .5f, walker.pos.x + walker.walkerWidth * .5f, walker.pos.y + walker.walkerHeight * .5f, l1.inX, l1.inY + l1.floorHeight, l1.outX, l1.outY + l1.floorHeight);
}
//Walker-Platform Detection
public Vector3D lineIntersectionWalkerPlatform(Walker walker, MovingPlatform l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5f, walker.pos.y - walker.walkerHeight * .5f, walker.pos.x + walker.walkerWidth * .5f, walker.pos.y + walker.walkerHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Walker-PlatformBottom Detection
public Vector3D lineIntersectionWalkerPlatformBottom(Walker walker, MovingPlatform l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5f, walker.pos.y - walker.walkerHeight * .5f, walker.pos.x + walker.walkerWidth * .5f, walker.pos.y + walker.walkerHeight * .5f, l1.inX, l1.inY + l1.floorHeight, l1.outX, l1.outY + l1.floorHeight);
}
//Walker-Walker Detection
public Vector3D lineIntersectionWalkerWalker(Walker walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.walkerWidth * .5f, walker1.pos.y - walker1.walkerHeight * .5f, walker1.pos.x + walker1.walkerWidth * .5f, walker1.pos.y + walker1.walkerHeight * .5f, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}
//Jumper-Floor Detection
public Vector3D lineIntersectionJumperFloor(Jumper jumper, Floor l1) {
  return lineIntersection(jumper.pos.x - jumper.jumperWidth *.5f, jumper.pos.y - jumper.jumperHeight * .5f, jumper.pos.x + jumper.jumperWidth * .5f, jumper.pos.y + jumper.jumperHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Jumper-Platform Detection
public Vector3D lineIntersectionJumperPlatform(Jumper jumper, MovingPlatform l1) {
  return lineIntersection(jumper.pos.x - jumper.jumperWidth *.5f, jumper.pos.y - jumper.jumperHeight * .5f, jumper.pos.x + jumper.jumperWidth * .5f, jumper.pos.y + jumper.jumperHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Jumper-Walker Detection
public Vector3D lineIntersectionJumperWalker(Jumper walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.jumperWidth * .5f, walker1.pos.y - walker1.jumperHeight * .5f, walker1.pos.x + walker1.jumperWidth * .5f, walker1.pos.y + walker1.jumperHeight * .5f, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}
//Booster-Floor Detection
public Vector3D lineIntersectionBoosterFloor(Booster booster, Floor l1) {
  return lineIntersection(booster.pos.x - booster.boosterWidth *.5f, booster.pos.y - booster.boosterHeight * .5f, booster.pos.x + booster.boosterWidth * .5f, booster.pos.y + booster.boosterHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Booster-Platform Detection
public Vector3D lineIntersectionBoosterPlatform(Booster booster, MovingPlatform l1) {
  return lineIntersection(booster.pos.x - booster.boosterWidth *.5f, booster.pos.y - booster.boosterHeight * .5f, booster.pos.x + booster.boosterWidth * .5f, booster.pos.y + booster.boosterHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Booster-Walker Detection
public Vector3D lineIntersectionBoosterWalker(Booster walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.boosterWidth * .5f, walker1.pos.y - walker1.boosterHeight * .5f, walker1.pos.x + walker1.boosterWidth * .5f, walker1.pos.y + walker1.boosterHeight * .5f, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}
//Slower-Floor Detection
public Vector3D lineIntersectionSlowerFloor(Slower slower, Floor l1) {
  return lineIntersection(slower.pos.x - slower.slowerWidth *.5f, slower.pos.y - slower.slowerHeight * .5f, slower.pos.x + slower.slowerWidth * .5f, slower.pos.y + slower.slowerHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Slower-Platform Detection
public Vector3D lineIntersectionSlowerPlatform(Slower slower, MovingPlatform l1) {
  return lineIntersection(slower.pos.x - slower.slowerWidth *.5f, slower.pos.y - slower.slowerHeight * .5f, slower.pos.x + slower.slowerWidth * .5f, slower.pos.y + slower.slowerHeight * .5f, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Slower-Walker Detection
public Vector3D lineIntersectionSlowerWalker(Slower walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.slowerWidth * .5f, walker1.pos.y - walker1.slowerHeight * .5f, walker1.pos.x + walker1.slowerWidth * .5f, walker1.pos.y + walker1.slowerHeight * .5f, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}


public Vector3D lineIntersection( float l1_inX, float l1_inY, float l1_outX, float l1_outY,
float l2_inX, float l2_inY, float l2_outX, float l2_outY)
{
  float denom = ((l1_outY - l1_inY)*(l2_outX - l2_inX)) -
    ((l1_outX - l1_inX)*(l2_outY - l2_inY));

  float nume_a = ((l1_outX - l1_inX)*(l2_inY - l1_inY)) - 
    ((l1_outY - l1_inY)*(l2_inX - l1_inX));

  float nume_b = ((l2_outX - l2_inX)*(l2_inY - l1_inY)) -
    ((l2_outY - l2_inY)*(l2_inX - l1_inX));

  if(denom == 0.0f)
  {
    if(nume_a == 0.0f && nume_b == 0.0f)
    {
      //println("COINCIDENT");
    }
    else
    {
      //println("PARALLEL");
    }
  }

  float ua = nume_a / denom;
  float ub = nume_b / denom;

  if(ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f)
  {
    //println("intersection ua: " + ua + " ub: " + ub);
    float intersectionX = l2_inX + ua*(l2_outX - l2_inX);
    float intersectionY = l2_inY + ua*(l2_outY - l2_inY);

    return new Vector3D(intersectionX, intersectionY);
  }
  else
  {
    return null;
  }
}

public class Booster
{
 Vector3D pos;
 Vector3D vel;
 Vector3D gravity;
 int boosterWidth = 40;
 int boosterHeight = 60;
 int rboosterWidth = 60;
 int rboosterHeight = 10;
 boolean walkingRight = true;
 boolean hasHit;
 boolean onFloor;
 boolean onPlatform;
 boolean isDragging;
 boolean isMouseOver = true;
 boolean justHit;
float offsetX;
float offsetY;
float radius = 4;
float dog = 0.01f;
float speed = 15;
float friction = 0;
boolean animating = false;
float multiplier = 1;
  
 public Booster(float _x, float _y)
{
 pos = new Vector3D(_x, _y); 
 vel = new Vector3D(0, 0);
 gravity = new Vector3D(0, .1f);
 hasHit = false;
 onFloor = false;
 isDragging = true;
}
 
 public void draw()
 {
   ellipseMode(CENTER);
    strokeWeight(2);
    if(hitDetect(mouseX, mouseY))
    {
    fill(160, 160, 0, 200);
    stroke(200, 200, 0, 225);
    } else{
    fill(160, 200);
    stroke(200, 225);
 }
    rectMode(CENTER);
    rect(pos.x, pos.y, rboosterWidth, rboosterHeight);
    if (hitDetect(mouseX, mouseY))
    {
    fill(100, 100, 0, 150);
    stroke(180, 180, 0, 175);
    } else {
    fill(0, 150);
    stroke(128, 175);
    }
    pushMatrix();
    translate(pos.x - 24, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5f, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x - 12, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5f, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5f, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x + 12, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5f, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x + 24, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5f, radius / 2);
    popMatrix();
    strokeWeight(1);
    if (animating) drawAnimation();
    update();
 }
 
 public void drawAnimation()
  {
    dog+=speed;
    speed -= friction;
    friction += .05f;
    if (speed <= 0)
    {
      animating = false;
      speed = 15;
      friction = 0;
      dog = 0;
    }
  }
 
 public void update()
 {
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
   } else {
     
   if (onFloor == true) 
   {
   gravity = new Vector3D (0, 0);
   } else {
      gravity = new Vector3D (0, 0.1f);
   }
   vel.add(gravity);
   pos.add(vel);
   if (pos.y > height) 
   {
     for(int i=0; i<myChoosersList.size(); i++) {
        Chooser b = (Chooser)myChoosersList.get(i);
        if (b.chooserType == 2) 
        {
          b.amount++;
          myBoostersList.remove(this);
        }
     }
   }
     
   //HIT TEST
      //Floor Hit Test
      onFloor = false;
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionBoosterFloor(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y - rboosterHeight * .5f + 5;
          vel = new Vector3D (0,0);
        }
      }
      
      //Platform Hit Test
      onPlatform = false;
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionBoosterPlatform(this, b);
        if(result != null) {
          onPlatform = true;
          pos.y = b.pos.y - rboosterHeight * .5f + 5;
          if(b.horizontal) vel = new Vector3D (b.vel.x, 0);
          else vel = new Vector3D (0, b.vel.y);
        }
      }
      
      //Walker Hit Test
      if (onFloor == true || onPlatform == true)
      {
   for(int i=0; i<myWalkersList.size(); i++) {
        Walker b = (Walker)myWalkersList.get(i);
        Vector3D result = lineIntersectionBoosterWalker(this, b);
        if(result == null) justHit = false;
        if(result != null) {
          if(justHit == false) 
          {
            b.isBoosting = true;
            b.walkingRightTemp = b.walkingRight;
            if(b.walkingRight == true)
   {
     b.vel = new Vector3D(b.vel.x + 10, b.vel.y * 1.5f);
      b.gravity = new Vector3D (-0.2f, b.gravity.y);
   } else {
     b.vel = new Vector3D(b.vel.x - 10, b.vel.y * 1.5f);
      b.gravity = new Vector3D (0.2f, b.gravity.y);
   }
          }
          justHit = true;
          animating = true;
        }
      }
   }
   }
   
   if(hitDetect(mouseX, mouseY)) isMouseOver = true;
    else isMouseOver = false;
 }
 
  public void mousePressed() {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
      onFloor = false;
      vel = new Vector3D (0, 0);
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
  }
  
  public void mouseReleased() {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
  }
  
  public boolean hitDetect(float theX, float theY) {
    if(theX >= pos.x - rboosterWidth * .5f && theX <= pos.x + rboosterWidth * .5f && theY >= pos.y - rboosterHeight * .5f && theY <= pos.y + rboosterHeight * .5f) return true;
    else return false;
  }
  
  
}

public class Chooser
{
 Vector3D pos;
 int chooserType;
 int amount;
 boolean isClicked = true;
 float chooserWidth;
 float chooserHeight;
 boolean thisChooser = false;
 
 final static int JUMPER = 1;
 final static int BOOSTER = 2;
 final static int SLOWER = 3;
 
 
 
 
 public Chooser(float _x, float _y, int _type, int _amount)
{
 pos = new Vector3D(_x, _y);
 chooserType = _type;
 amount = _amount;
} 

public Chooser(XMLElement chooserNode)
  {
    float tempX = chooserNode.getFloatAttribute("posX");
    float tempY = chooserNode.getFloatAttribute("posY");
    this.pos = new Vector3D(tempX, tempY);
    this.chooserType = chooserNode.getIntAttribute("chooserType");
    this.amount = chooserNode.getIntAttribute("amount");
  }

public XMLElement toXML()
  {
    XMLElement chooserNode = new XMLElement("chooser");
    chooserNode.addAttribute("posX", pos.x);
    chooserNode.addAttribute("posY", pos.y);
    chooserNode.addAttribute("amount", amount);
    chooserNode.addAttribute("chooserType", chooserType);
    return chooserNode;
  }
  
  public void draw()
  {
    textAlign(LEFT);
    switch(chooserType)
    {
      case JUMPER:
      if (amount == 0)
      {
        fill(200);
        if (hitDetect() && !testMode) stroke(255, 255, 0);
        else stroke(100);
      } else {
      fill(255);
      if (hitDetect() && !testMode) stroke(255, 255, 0);
        else stroke(0);
      }
      rectMode(CENTER);
      chooserWidth = 20;
      chooserHeight = 30;
      rect(pos.x, pos.y, chooserWidth, chooserHeight);
      textFont(font48);
      textSize(12);
      fill(0);
      text("J", pos.x - .1f * chooserWidth, pos.y);
      fill(0);
      noStroke();
      textSize(12);
      textAlign(LEFT);
      text("x" + amount, pos.x + chooserWidth * .5f + 5, pos.y + chooserHeight * .5f);
      break;
      
      case BOOSTER:
      if (amount == 0)
      {
        fill(200);
        if (hitDetect() && !testMode) stroke(255, 255, 0);
        else stroke(100);
      } else {
      fill(255);
      if (hitDetect() && !testMode) stroke(255, 255, 0);
        else stroke(0);
      }
      rectMode(CENTER);
      chooserWidth = 20;
      chooserHeight = 30;
      rect(pos.x, pos.y, chooserWidth, chooserHeight);
      textFont(font48);
      textSize(12);
      fill(0);
      text("B", pos.x - .1f * chooserWidth, pos.y);
      fill(0);
      noStroke();
      textSize(12);
      textAlign(LEFT);
      text("x" + amount, pos.x + chooserWidth * .5f + 5, pos.y + chooserHeight * .5f);
      break;
      
    
    
    case SLOWER:
      if (amount == 0)
      {
        fill(200);
        if (hitDetect() && !testMode) stroke(255, 255, 0);
        else stroke(100);
      } else {
      fill(255);
      if (hitDetect() && !testMode) stroke(255, 255, 0);
        else stroke(0);
      }
      rectMode(CENTER);
      chooserWidth = 20;
      chooserHeight = 30;
      rect(pos.x, pos.y, chooserWidth, chooserHeight);
      textFont(font48);
      textSize(12);
      fill(0);
      text("S", pos.x - .1f * chooserWidth, pos.y);
      fill(0);
      noStroke();
      textSize(12);
      textAlign(LEFT);
      text("x" + amount, pos.x + chooserWidth * .5f + 5, pos.y + chooserHeight * .5f);
      break;
    
    }
    
    
  }
  
  public boolean hitDetect()
  {
    if( mouseX <= pos.x + chooserWidth * .5f && mouseX >= pos.x - chooserWidth * .5f )
      {
        if( mouseY <= pos.y + chooserHeight * .5f && mouseY >= pos.y - chooserHeight * .5f)
        {
          return true;
        } else { return false;}
      } else {return false;}
  }
  
  public void mouseReleased()
  {
   isClicked = false; 
  }
  
}

public class Floor
{
  Vector3D pos;
 float floorWidth;
 float floorHeight;
  float inX, inY, outX, outY;
  int floorType;
  boolean invisible;
  int copyNumber;
  PImage floorPattern;
 boolean isDragging = true;
 boolean mouseIsDragging = true;
 boolean isMouseOver = true;
 float offsetX;
 float offsetY;
 boolean thisFloor = false;
 float mouseChange;
 float mouseOld;
 boolean changingSize = false;
 boolean deletes;
 boolean keyJustPressed = false;
 boolean isDead = false;
 
  public Floor (float _x, float _y, float _width, float _height, int _floorType, boolean _deletes)
 {
 pos = new Vector3D(_x, _y);
 floorWidth = _width;
 floorHeight = _height;
  inX = _x;
 inY = _y;
 outX = inX + _width;
 outY = inY;
 floorType = _floorType;
 invisible = false;
 floorPattern = loadImage("texture.png");
 deletes = _deletes;
 isDead = !deletes;
 }
 
 public Floor (float _x, float _y, float _width, float _height, int _floorType, boolean _deletes, boolean _invisible)
 {
 pos = new Vector3D(_x, _y);
 floorWidth = _width;
 floorHeight = _height;
  inX = _x;
 inY = _y;
 outX = inX + _width;
 outY = inY;
 floorType = _floorType;
 invisible = _invisible;
 deletes = _deletes;
 isDead = !deletes;
 }
 
 public Floor(XMLElement floorNode)
  {
    float tempX= floorNode.getFloatAttribute("posX");
    float tempY = floorNode.getFloatAttribute("posY");
    this.pos = new Vector3D(tempX, tempY);
    this.floorWidth = floorNode.getFloatAttribute("floorWidth");
    this.floorHeight = floorNode.getFloatAttribute("floorHeight");
    this.floorType = floorNode.getIntAttribute("floorType");
    this.deletes = convertInt(floorNode.getIntAttribute("deletes"));
    this.invisible = convertInt(floorNode.getIntAttribute("invisible"));
 floorPattern = loadImage("texture.png");
  isDragging = false;
  mouseIsDragging = false;
  isMouseOver = false;
  }
 
 public XMLElement toXML()
  {
    XMLElement floorNode = new XMLElement("floor");
    floorNode.addAttribute("posX", pos.x);
    floorNode.addAttribute("posY", pos.y);
    floorNode.addAttribute("floorWidth", floorWidth);
    floorNode.addAttribute("floorHeight", floorHeight);
    floorNode.addAttribute("floorType", floorType);
    floorNode.addAttribute("deletes", convertBoolean(deletes));
    floorNode.addAttribute("invisible", convertBoolean(invisible));
    inX = pos.x;
 inY = pos.y;
 outX = inX + floorWidth;
 outY = inY;
 isDead = !deletes;
    return floorNode;
  }
 
 public void draw()
 {
   if(!isDead)
   {
   if (!invisible)
   {
   rectMode(CENTER);
   noStroke();
   fill(50, 250);
   rect(pos.x + floorWidth * .5f, pos.y + floorHeight * .5f, floorWidth, floorHeight);
   }
   update();
   inX = pos.x;
 inY = pos.y;
 outX = inX + floorWidth;
 outY = inY;
   } else {
     
   inX = 0;
 inY = 0;
 outX = 0;
 outY = 0;
   }
   if(!testMode)
   {
     
   rectMode(CENTER);
   noStroke();
   fill(50, 250);
   rect(pos.x + floorWidth * .5f, pos.y + floorHeight * .5f, floorWidth, floorHeight);
   update();
   if (!deletes) 
   {
     noStroke();
     fill(0, 128);
   rectMode(CENTER);
   rect(pos.x + floorWidth * .5f, pos.y + floorHeight * .5f, floorWidth, floorHeight);
   }
   }
   if (thisFloor) 
   {
     noStroke();
     fill(255, 0, 0, 128);
   rectMode(CENTER);
   rect(pos.x + floorWidth * .5f, pos.y + floorHeight * .5f, floorWidth, floorHeight);
   }
 }
 
  public void update()
 {
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
   }
   if (changingSize)
   {
     mouseChange = mouseX - mouseOld;
     floorWidth = floorWidth + mouseChange;
     mouseOld = mouseX;
   }
   if (hitTest(mouseX, mouseY)) isMouseOver = true;
   else isMouseOver = false; 
   noFill();
   stroke(255, 255, 0);
   rectMode(CENTER);
   if (hitTest(mouseX, mouseY)) rect(pos.x + floorWidth * .5f, pos.y + floorHeight * .5f, floorWidth, floorHeight);
   if (keyPressed && thisFloor && !keyJustPressed)
   {
     if (keyCode == LEFT)
     {
   if (editType == Move)
   {
     pos.x--;
   }
   if (editType == EditSize)
   {
     floorWidth--;
   }
   }
   if (keyCode == RIGHT)
     {
   if (editType == Move)
   {
     pos.x++;
   }
   if (editType == EditSize)
   {
     floorWidth++;
   }
   }
   if (keyCode == UP)
     {
   if (editType == Move)
   {
     pos.y--;
   }
   }
   if (keyCode == DOWN)
     {
   if (editType == Move)
   {
     pos.y++;
   }
   }
   keyJustPressed = true;
 }
 }
  
  public void keyReleased()
  {
    keyJustPressed = false;
  }
  
  public void mousePressed() 
  {
    if (editType == Move)
    {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
    }
    if (editType == EditSize)
    {
      if(isMouseOver && !mouseIsDragging)
      {
      mouseOld = mouseX;
      changingSize = true;
      mouseIsDragging = true;
      }
    }
  }
  
  public void mouseReleased() 
  {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
    if(changingSize) {
      mouseIsDragging = false;
      changingSize = false;
    }
  }


public boolean hitTest(float theX, float theY)
{
  if (theX >= pos.x && theX <= pos.x + floorWidth)
  {
    if ( theY > pos.y&& theY <= pos.y + floorHeight)
    { return true;
    }
  }
  return false;
}
}

public class Goal
{
  Vector3D pos;
 float radius = 20;
 boolean isDragging = false;
 boolean mouseIsDragging = false;
 boolean isMouseOver = false;
 float offsetX;
 float offsetY;
 boolean thisGoal;
 
  public Goal (float _x, float _y)
 {
 pos = new Vector3D(_x, _y);
 }
 
 public Goal(XMLElement goalNode)
  {
    float tempX = goalNode.getFloatAttribute("posX");
    float tempY = goalNode.getFloatAttribute("posY");
    this.pos = new Vector3D(tempX, tempY);
 boolean isDragging = false;
 boolean mouseIsDragging = false;
 boolean isMouseOver = false;
  }
 
 public XMLElement toXML()
  {
    XMLElement goalNode = new XMLElement("goal");
    goalNode.addAttribute("posX", pos.x);
    goalNode.addAttribute("posY", pos.y);
  isDragging = false;
  mouseIsDragging = false;
  isMouseOver = false;
    return goalNode;
  }
 
 public void draw()
 {
   float cradius = 20;
   if (isMouseOver) fill(255, 0, 0, 100);
   else fill(150, 0, 200, 100);
   stroke(200, 255, 200, 100);
   ellipseMode(CENTER);
   ellipse(pos.x, pos.y, cradius, cradius);
   noStroke();
   ellipse(pos.x, pos.y, cradius * .8f, cradius * .8f);
   ellipse(pos.x, pos.y, cradius * .5f, cradius * .5f);
   ellipse(pos.x, pos.y, cradius * .3f, cradius * .3f);
   update();
 }
 
 public void update()
 {
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
   }
   if (hitDetect(mouseX, mouseY)) isMouseOver = true;
   else isMouseOver = false;
 }
  
  public boolean hitTest()
  {
   
   for(int i=0; i<myWalkersList.size(); i++)
  {
    Walker b = (Walker)myWalkersList.get(i);
    if( pos.x <= b.pos.x + 1 && pos.x >= b.pos.x - 1)
    {
      if(  pos.y <= b.pos.y + 1 && pos.y >= b.pos.y - 1)
      {
        return true;
      }
    }
  }
    return false;
  }
  
  public void mousePressed() {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
  }
  
  public void mouseReleased() {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
  }
  
  public boolean hitDetect(float theX, float theY) {
    float d = dist(pos.x, pos.y, theX, theY);
    if(d < radius) return true;
    else return false;
  }
  
}

public class Jumper
{
 Vector3D pos;
 Vector3D vel;
 Vector3D gravity;
 int jumperWidth = 20;
 int jumperHeight = 30;
 boolean walkingRight = true;
 boolean hasHit;
 boolean onFloor;
 boolean onPlatform;
 boolean isDragging;
 boolean isMouseOver = false;
 boolean justHit;
 boolean animating;
float offsetX;
float offsetY;
float offset = .5f;
float radius = 4;
  float speed = 1.5f;
  boolean expanding = true;
  
 public Jumper(float _x, float _y)
{
 pos = new Vector3D(_x, _y); 
 vel = new Vector3D(0, 0);
 gravity = new Vector3D(0, .1f);
 hasHit = false;
 onFloor = false;
 isDragging = true;
}
 
 public void draw()
 {
   if (hitDetect(mouseX, mouseY))
    {
    stroke(255, 255, 0, 128);
    fill(100, 100, 0, 80);
    } else {
    stroke(0, 128);
    fill(128, 80);
    }
    strokeWeight(2);
    ellipse(pos.x, pos.y, radius * 4, radius);
    ellipse(pos.x, pos.y - offset * 1, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 2, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 3, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 4, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 5, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 6, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 7, radius * 8, radius);
    strokeWeight(1);
    update();
 }
 
 public void animate()
  {
    if (expanding) this.offset+= speed;
    if (!expanding) this.offset-= speed - .5f;
    if (offset >= 7) this.expanding = false;
    if (offset <= .5f) {
      this.offset = .5f;
      this.expanding = true;
    this.animating = false;
    }
  }
 
 public void update()
 {
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
      this.animating = false;
      this.offsetY = .5f;
   } else {
     
   if (onFloor == true) 
   {
   gravity = new Vector3D (0, 0);
   } else {
      gravity = new Vector3D (0, 0.1f);
   }
   vel.add(gravity);
   pos.add(vel);
   if (pos.y > height) 
   {
     for(int i=0; i<myChoosersList.size(); i++) {
        Chooser b = (Chooser)myChoosersList.get(i);
        if (b.chooserType == 1) 
        {
          b.amount++;
          myJumpersList.remove(this);
        }
     }
   }
     
   //HIT TEST
      //Floor Hit Test
      onFloor = false;
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionJumperFloor(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y;
          vel = new Vector3D (0,0);
        }
      }
      
      //Platform Hit Test
      onPlatform = false;
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionJumperPlatform(this, b);
        if(result != null) {
          onPlatform = true;
          pos.y = b.pos.y - jumperHeight * .5f;
          if(b.horizontal) vel = new Vector3D (b.vel.x, 0);
          else vel = new Vector3D (0, b.vel.y);
        }
      }
      
      //Walker Hit Test
      if (onFloor == true || onPlatform == true)
      {
   for(int i=0; i<myWalkersList.size(); i++) {
        Walker b = (Walker)myWalkersList.get(i);
        Vector3D result = lineIntersectionJumperWalker(this, b);
        if(result == null) justHit = false;
        if(result != null) {
          if(justHit == false) 
          {
            b.isJumping = true;
            b.vel = new Vector3D(b.vel.x, -5.5f);
          }
          justHit = true;
          this.animating = true;
        }
      }
   }
   }
   
   if(hitDetect(mouseX, mouseY)) isMouseOver = true;
    else isMouseOver = false;
    
    
    if (animating) animate();
 }
 
  public void mousePressed() {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
      onFloor = false;
      vel = new Vector3D (0, 0);
      offset = .5f;
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
  }
  
  public void mouseReleased() {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
  }
  
 public boolean hitDetect(float theX, float theY) {
    float d = dist(pos.x, pos.y, theX, theY);
    if(d < radius * 2) return true;
    else return false;
    
  }
  
  
}

public void levelText()
{
  textSize(20);
  fill(0);
  noStroke();
  textAlign(LEFT);
 switch(gameLevel)
{
 case 1:
 text("Level 1: Jump The Wall", 110, 455);
 break;
 
 case 2:
 text("Level 2: Leap Of Faith", 110, 455);
 break;
 
 case 3:
 text("Level 3: ", 110, 455);
 break;
 
 case 4:
 text("Level 4: ", 110, 455);
 break;
 
 case 5:
 text("Level 5: ", 110, 455);
 break;
} 
}

/*
final static int NULL_MODE = 0;
final static int CHOOSER_MODE = 1;
final static int WALL_MODE = 2;
final static int FLOOR_MODE = 3;
final static int WALKER_MODE = 4;
final static int PLATFORM_MODE = 5;
final static int SWITCH_MODE = 6;
final static int GOAL_MODE = 7;
final static int DELETE_MODE = 8;
final static int SAVE_MODE = 9;
final static int LOAD_MODE = 10;
*/

public class ModeButton
{
  Vector3D pos;
 float buttonWidth = 60;
 float buttonHeight = 40;
  float inX, inY, outX, outY;
  int buttonType;
 
  public ModeButton (float _x, float _y, int _buttonType)
 {
 pos = new Vector3D(_x, _y);
 buttonType = _buttonType;
 if (buttonType == 0 || buttonType >= 8) buttonHeight = 25;
 if (buttonType <= 7) buttonWidth = 55;
 }

 
 public void draw()
 {
   rectMode(CENTER);
   fill(255);
   if (hitTest(mouseX, mouseY)) stroke( 255, 255, 0, 150);
   else stroke(0);
   rect(pos.x, pos.y, buttonWidth, buttonHeight);
   textFont(font48);
   textSize(11);
   fill(0);
   textAlign(CENTER);
   if (buttonType == 0 && !testMode && editMode != 2 && editMode != 3 && editMode != 5) text("Test Level", pos.x, pos.y + 3);
   if (buttonType == 0 && testMode) text("Edit Level", pos.x, pos.y + 3);
   if (buttonType == 1) text("Choosers", pos.x, pos.y + 3);
   if (buttonType == 2) text("Walls", pos.x, pos.y + 3);
   if (buttonType == 3) text("Floors", pos.x, pos.y + 3);
   if (buttonType == 4) text("Walkers", pos.x, pos.y + 3);
   if (buttonType == 5) text("Platforms", pos.x, pos.y + 3);
   if (buttonType == 6) text("Switches", pos.x, pos.y + 3);
   if (buttonType == 7) text("Goals", pos.x, pos.y + 3);
   if (editMode == 0 || editMode == 8 || editMode == 9 || editMode == 10 )
   {
   if (buttonType == 8) text("Delete", pos.x, pos.y + 3);
   if (buttonType == 9 && editMode != 8) text("Save", pos.x, pos.y + 3);
   if (buttonType == 9 && editMode == 8) text("Back", pos.x, pos.y + 3);
   if (buttonType == 10 && editMode != 8) text("Load", pos.x, pos.y + 3);
   } else {
   if (buttonType == 8 && !modeMore) text("New", pos.x, pos.y + 3);
   if (buttonType == 9 && editMode != 1 && !modeMore) text("Move", pos.x, pos.y + 3);
   if (buttonType == 9 && editMode == 1) text("Amount", pos.x, pos.y + 3);
   if (buttonType == 10 && editMode == 1) text("Type", pos.x, pos.y + 3);
   if (buttonType == 0 && editMode == 5 && !modeMore) text("More", pos.x, pos.y + 3);
   if (buttonType == 0 && editMode == 5 && modeMore) text("Back", pos.x, pos.y + 3);
   if (buttonType == 10 && editMode == 6) text("Delete Key", pos.x, pos.y + 3);
   if (buttonType == 0 && editMode == 2 || buttonType == 0 && editMode == 3) text("Delete Key", pos.x, pos.y + 3);
   if (editMode == 2 && buttonType == 10 || editMode == 3 && buttonType == 10 || editMode == 5 && buttonType == 10 & !modeMore) text("Edit Size", pos.x, pos.y + 3);
   if (editMode == 5 && buttonType == 10 && modeMore) text("Type", pos.x, pos.y + 3);
   if (buttonType == 9 && modeMore) text("Dist/Time", pos.x, pos.y + 3);
   if (editMode == WALKER_MODE && buttonType == Dir) text("Direction", pos.x, pos.y + 3);
   textSize(11);
   if (buttonType == 8 && modeMore) text("Speed/Dir", pos.x, pos.y + 3);
   }
   //Chooser Amount
   if (editMode == 1 && editType == 10)
   {
   textSize(12);
   if (buttonType == 12) text("Jumper", pos.x, pos.y + 3);
   if (buttonType == 13) text("Booster", pos.x, pos.y + 3);
   if (buttonType == 14) text("Slower", pos.x, pos.y + 3);
   }
   //Wall Deletes
   if (editMode == WALL_MODE && editType == DeleteKey || editMode == FLOOR_MODE && editType == DeleteKey || editMode == PLATFORM_MODE && editType == Type)
   {
   textSize(12);
   if (buttonType == 12) text("Deletes", pos.x, pos.y + 3);
   if (buttonType == 13) text("Adds", pos.x, pos.y + 3);
   }
   //Walker Direction
   if (editMode == WALKER_MODE && editType == Dir)
   {
   textSize(12);
   if (buttonType == 12) text("Left", pos.x, pos.y + 3);
   if (buttonType == 13) text("Right", pos.x, pos.y + 3);
   }
   //Platform Direction
   if (editMode == PLATFORM_MODE && editType == DistDir && modeMore)
   {
   textSize(12);
   if (buttonType == 12) text("Up/Down", pos.x, pos.y + 3);
   if (buttonType == 13) text("Left/Right", pos.x, pos.y + 3);
   if (buttonType == 14) text("Start Left", pos.x, pos.y + 3);
   if (buttonType == 15) text("Start Right", pos.x, pos.y + 3);
   }
    if (editMode == PLATFORM_MODE && editType == SpeedTime && modeMore)
   {
   textSize(12);
   if (buttonType == 12) text("Edit Dist", pos.x, pos.y + 3);
   }
}

public boolean hitTest(float theX, float theY)
{
  if (theX >= pos.x - buttonWidth * .5f && theX <= pos.x + buttonWidth * .5f)
  {
    if ( theY > pos.y - buttonHeight * .5f && theY <= pos.y + buttonHeight * .5f)
    { return true;
    }
  }
  return false;
}
}

public class MovingPlatform
{
  Vector3D pos;
  Vector3D vel;
  Vector3D startPos;
  Vector3D endPos;
  boolean horizontal;
  boolean movingRight;
  float speed;
 float floorWidth;
 float floorHeight;
 float timer = 0;
  float inX, inY, outX, outY;
  int floorType;
  int holdTime;
  boolean deletes;
  boolean isDead;
 boolean isDragging = true;
 boolean mouseIsDragging = true;
 boolean isMouseOver = true;
 float offsetX;
 float offsetY;
 boolean thisPlatform = false;
 float mouseChange;
 float mouseOld;
 boolean changingSize = false;
 boolean keyJustPressed = false;
 boolean dir;
 boolean editDistance;
 float distance;
 
  public MovingPlatform (float _x, float _y, float _width, float _height, float _speed, int _time, float _distance, boolean _horizontal, boolean _dir, int _floorType, boolean _deletes)
 {
 pos = new Vector3D(_x, _y);
 horizontal = _horizontal;
 distance = _distance;
 if(horizontal) endPos = new Vector3D (pos.x + distance, pos.y);
 else endPos = new Vector3D (pos.x, pos.y + distance);
 startPos = pos.copy();
 dir = _dir;
 movingRight = dir;
 speed = _speed;
 holdTime = _time;
 floorWidth = _width;
 floorHeight = _height;
  inX = _x;
 inY = _y;
 outX = inX + _width;
 outY = inY;
 floorType = _floorType;
 if (!dir) pos = endPos.copy();
 deletes = _deletes;
 isDead = !deletes;
 }
 
  public MovingPlatform(XMLElement platformNode)
  {
    float tempX = platformNode.getFloatAttribute("posX");
    float tempY = platformNode.getFloatAttribute("posY");
    this.pos = new Vector3D(tempX, tempY);
    this.horizontal = convertInt(platformNode.getIntAttribute("horizontal"));
    this.distance = platformNode.getFloatAttribute("distance");
    if(horizontal) endPos = new Vector3D (pos.x + distance, pos.y);
    else endPos = new Vector3D (pos.x, pos.y + distance);
    startPos = pos.copy();
    this.movingRight = convertInt(platformNode.getIntAttribute("dir"));
    this.speed = platformNode.getFloatAttribute("speed");
    this.holdTime = platformNode.getIntAttribute("holdTime");
    this.floorWidth = platformNode.getFloatAttribute("floorWidth");
    this.floorHeight = platformNode.getFloatAttribute("floorHeight");
    inX = pos.x;
    inY = pos.y;
    outX = inX + floorWidth;
    outY = inY;
    this.floorType = platformNode.getIntAttribute("floorType");
    this.dir = convertInt(platformNode.getIntAttribute("dir"));
    if (dir) pos = endPos.copy();
    this.deletes = convertInt(platformNode.getIntAttribute("deletes"));
    isDead = !deletes;
  isDragging = false;
  mouseIsDragging = false;
  isMouseOver = false;
  }
 
 public XMLElement toXML()
  {
    XMLElement platformNode = new XMLElement("platform");
    platformNode.addAttribute("posX", pos.x);
    platformNode.addAttribute("posY", pos.y);
    platformNode.addAttribute("floorWidth", floorWidth);
    platformNode.addAttribute("floorHeight", floorHeight);
    platformNode.addAttribute("floorType", floorType);
    platformNode.addAttribute("deletes", convertBoolean(deletes));
    platformNode.addAttribute("speed", speed);
    platformNode.addAttribute("holdTime", holdTime);
    platformNode.addAttribute("distance", distance);
    platformNode.addAttribute("dir", convertBoolean(dir));
    platformNode.addAttribute("horizontal", convertBoolean(horizontal));
    return platformNode;
  }
 
 public void draw()
 {
   if (!isDead)
   {
   fill(60, 200);
   stroke(200, 150);
   rectMode(CORNER);
   rect(pos.x, pos.y, floorWidth, floorHeight);
   update();
   } else {
     
   inX = 0;
 inY = 0;
 outX = 0;
 outY = 0;
     
   }
   
   if(!testMode)
   {
   fill(60, 200);
   stroke(200, 150);
   rectMode(CORNER);
   rect(pos.x, pos.y, floorWidth, floorHeight);
   update();
   if (!deletes) 
   {
     noStroke();
     fill(0, 128);
   rectMode(CORNER);
   rect(pos.x, pos.y, floorWidth, floorHeight);
   }
   }
   if (thisPlatform) 
   {
     noStroke();
     fill(255, 0, 0, 128);
   rectMode(CENTER);
   rect(pos.x + floorWidth * .5f, pos.y + floorHeight * .5f, floorWidth, floorHeight);
   }
 }
  
  public void update()
  {
    if (testMode)
    {
    if (horizontal)
    {
    if (movingRight) 
    {
      if(pos.x >= endPos.x) 
      {
        pos.x = endPos.x;
      timer++;
      vel = new Vector3D (0, 0);
    } else{
      vel = new Vector3D (speed, 0);
    }
     } else {
       if(pos.x <= startPos.x) 
      {
        pos.x = startPos.x;
      timer++;
      vel = new Vector3D (0, 0);
    } else{
      vel = new Vector3D (speed * -1, 0);
    }
     }
    } else {
      if (movingRight) 
      {
        if(pos.y >= endPos.y) 
      {
        pos.y = endPos.y;
      timer++;
      vel = new Vector3D (0, 0);
    } else{
      vel = new Vector3D (0, speed);
    }
      } else {
        if(pos.y <= startPos.y) 
      {
        pos.y = startPos.y;
      timer++;
      vel = new Vector3D (0, 0);
    } else{
      vel = new Vector3D (0, speed * -1);
    }
      }
    }
    if (timer == holdTime)
    {
     movingRight = !movingRight;
    timer = 0; 
    }
    pos.add(vel);
    inX = pos.x;
 inY = pos.y;
 outX = inX + floorWidth;
 outY = inY;
  } else {
     if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
   }
   if (editDistance)
   {
     stroke(0);
     fill(0, 128);
     rectMode(CORNER);
     if (horizontal) rect(mouseX, pos.y, floorWidth, floorHeight);
     if (!horizontal) rect(pos.x, mouseY, floorWidth, floorHeight);
     if (keyPressed)
     {
     if (key == ENTER)
     {
       
     if (horizontal) 
     {
       this.distance = mouseX - pos.x;
     this.endPos = new Vector3D (pos.x + distance, pos.y);
     }
     if (!horizontal) 
     {
       this.distance = mouseY - pos.y;
       this.endPos = new Vector3D (pos.x, pos.y + distance);
     }
     editDistance = false;
     }
   }
   }
   if (changingSize)
   {
     mouseChange = mouseX - mouseOld;
     floorWidth = floorWidth + mouseChange;
     mouseOld = mouseX;
   }
   if (hitTest(mouseX, mouseY)) isMouseOver = true;
   else isMouseOver = false; 
   noFill();
   stroke(255, 255, 0);
   rectMode(CENTER);
   if (hitTest(mouseX, mouseY)) rect(pos.x + floorWidth * .5f, pos.y + floorHeight * .5f, floorWidth, floorHeight);
   if (keyPressed && thisPlatform && !keyJustPressed)
   {
     if (keyCode == LEFT)
     {
   if (editType == Move)
   {
     pos.x--;
   }
   if (editType == EditSize)
   {
     floorWidth--;
   }
   }
   if (keyCode == RIGHT)
     {
   if (editType == Move)
   {
     pos.x++;
   }
   if (editType == EditSize)
   {
     floorWidth++;
   }
   }
   if (keyCode == UP)
     {
   if (editType == Move)
   {
     pos.y--;
   }
   }
   if (keyCode == DOWN)
     {
   if (editType == Move)
   {
     pos.y++;
   }
   }
   keyJustPressed = true;
 }
  }
  }
  
  public void keyReleased()
  {
    keyJustPressed = false;
  }
  
  public void mousePressed() 
  {
    if (!editDistance)
    {
    if (editType == Move && !modeMore)
    {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
    }
    }
    if (editType == EditSize && !modeMore)
    {
      if(isMouseOver && !mouseIsDragging)
      {
      mouseOld = mouseX;
      changingSize = true;
      mouseIsDragging = true;
      }
    }
    }
  }
  
  public void mouseReleased() 
  {
    if (!editDistance)
    {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
    if(changingSize) {
      mouseIsDragging = false;
      changingSize = false;
    }
    }
  }
  
  public boolean hitTest(float theX, float theY)
{
  if (theX >= pos.x && theX <= pos.x + floorWidth)
  {
    if ( theY > pos.y&& theY <= pos.y + floorHeight)
    { return true;
    }
  }
  return false;
}
  
}

public class OtherObjects
{
  Vector3D pos;
 float radius = 20;
 int type;
 int whichSwitch;
 boolean switchHit = false;
 final static int SWITCH = 0;
 final static int BUTTON = 1;
 boolean isDragging = true;
 boolean mouseIsDragging = true;
 boolean isMouseOver = true;
 float offsetX;
 float offsetY;
 boolean thisObject = false;
 
  public OtherObjects (float _x, float _y, int _type, int _whichSwitch)
 {
 pos = new Vector3D(_x, _y);
 type = _type;
 whichSwitch = _whichSwitch;
 }
 
  public OtherObjects(XMLElement switchNode)
  {
    float tempX = switchNode.getFloatAttribute("posX");
    float tempY = switchNode.getFloatAttribute("posY");
    this.pos = new Vector3D(tempX, tempY);
    this.whichSwitch = switchNode.getIntAttribute("whichSwitch");
    this.type = switchNode.getIntAttribute("type");
isDragging = false;
  mouseIsDragging = false;
  isMouseOver = false;
  }
 
 public XMLElement toXML()
  {
    XMLElement switchNode = new XMLElement("switch");
    switchNode.addAttribute("posX", pos.x);
    switchNode.addAttribute("posY", pos.y);
    switchNode.addAttribute("whichSwitch", whichSwitch);
    switchNode.addAttribute("type", type);
    return switchNode;
  }
 
 public void draw()
 {
   switch(type)
   {
   case SWITCH:
   if (switchHit == false)
   {
   fill(255, 0, 0, 100);
   if (hitDetect(mouseX, mouseY)) stroke(255, 255, 0, 100);
   else stroke(255, 200, 200, 100);
   } else {
    fill(0, 255, 0, 100);
   if (hitDetect(mouseX, mouseY)) stroke(200, 255, 200, 100); 
   else stroke(0);
   }
   ellipseMode(CENTER);
   ellipse(pos.x, pos.y, radius, radius);
   noStroke();
   ellipse(pos.x, pos.y, radius * .8f, radius * .8f);
   ellipse(pos.x, pos.y, radius * .5f, radius * .5f);
   ellipse(pos.x, pos.y, radius * .3f, radius * .3f);
   break;
   
   case BUTTON:
   if (switchHit == false)
   {
   fill(255);
   stroke(0);
   } else {
    fill(0, 0, 255);
   stroke(0); 
   }
   ellipseMode(CENTER);
   ellipse(pos.x, pos.y, radius, radius);
   break;
   
   }
   update();
 }
 
 public void update()
 {
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
   }
   if (hitDetect(mouseX, mouseY)) isMouseOver = true;
   else isMouseOver = false;
 }
 
 public void mousePressed() 
  {
    if (editType == Move)
    {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
    }
  }
  
  public void mouseReleased() 
  {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
  }
 
 public boolean hitTest(int _whichSwitch, boolean _changeDir)
 {
           for(int i=0; i<myWalkersList.size(); i++)
    {
      Walker b = (Walker)myWalkersList.get(i);
        if (whichSwitch == _whichSwitch && switchHit == false)
        {
          if( pos.x <= b.pos.x + b.walkerWidth  && pos.x >= b.pos.x - b.walkerWidth)
          {
            if( pos.y <= b.pos.y + b.walkerHeight && pos.y >= b.pos.y - b.walkerHeight)
            {
              switchHit = true;
              if (_changeDir) b.walkingRight = !b.walkingRight;
              return true;
                }
              }
            }
          }
          return false;
        }
 
 public boolean hitDetect(float theX, float theY) {
    float d = dist(pos.x, pos.y, theX, theY);
    if(d < radius) return true;
    else return false;
  }
     
}

public class Slower
{
 Vector3D pos;
 Vector3D vel;
 Vector3D gravity;
 int slowerWidth = 20;
 int slowerHeight = 30;
 boolean walkingRight = true;
 boolean hasHit;
 boolean onFloor;
 boolean onPlatform;
 boolean isDragging;
 boolean isMouseOver = false;
 boolean justHit;
 boolean isSlowing;
float offsetX;
float offsetY;
float radius = 40;
float baseHeight = 5;
float baseWidth = 30;
float speed = 1;
float friction = 0;
float expandAmount = -1;
boolean expanded = false;
boolean animating = false;
  
 public Slower(float _x, float _y)
{
 pos = new Vector3D(_x, _y); 
 vel = new Vector3D(0, 0);
 gravity = new Vector3D(0, .1f);
 hasHit = false;
 onFloor = false;
 isDragging = true;
}
 
 public void draw()
 {
   pushMatrix();
    translate(pos.x, pos.y);
    strokeWeight(2);
    rectMode(CENTER);
    if(hitDetect(mouseX,mouseY))
    {
    stroke(100, 100, 0, 200);
    fill(100, 100, 200);
    } else {
    stroke(0, 200);
    fill(0, 200);
    }
    rect(0, 7, baseWidth, baseHeight);
    strokeWeight(2);
    line(-baseWidth/2, 5, -baseWidth/2, expandAmount);
    line(baseWidth/2, 5, baseWidth/2, expandAmount);
    if (hitDetect (mouseX, mouseY))
    {
    stroke(100, 100, 0, 150);
    fill(150, 150, 0, 100);
    } else {
    stroke(0, 50, 100, 150);
    fill(0, 50, 150, 100);
    }
    rect(0, expandAmount/2, baseWidth, expandAmount);
    strokeWeight(1);
    popMatrix();
    if(!expanded && !isSlowing) animating = true;
    if(expanded && isSlowing) animating = true;
    if (animating) animate();
   update();
 }
 
 public void animate()
 {
    if (expanded)
    {
      expandAmount -=2;
      if (expandAmount <= -30)
      {
        expanded = false;
        animating = false;
      }
    }
    
    if (!expanded)
    {
      expandAmount +=2;
      if (expandAmount >= 0)
      {
        expanded = true;
        animating = false;
      }
    }
 }
 
 public void update()
 {
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
   } else {
     
   if (onFloor == true) 
   {
   gravity = new Vector3D (0, 0);
   } else {
      gravity = new Vector3D (0, 0.1f);
   }
   vel.add(gravity);
   pos.add(vel);
   if (pos.y > height) 
   {
     for(int i=0; i<myChoosersList.size(); i++) {
        Chooser b = (Chooser)myChoosersList.get(i);
        if (b.chooserType == 3) 
        {
          b.amount++;
          mySlowersList.remove(this);
        }
     }
   }
     
   //HIT TEST
      //Floor Hit Test
      onFloor = false;
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionSlowerFloor(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y - baseHeight * 1.5f + 2;
          vel = new Vector3D (0,0);
        }
      }
      
      //Platform Hit Test
      onPlatform = false;
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionSlowerPlatform(this, b);
        if(result != null) {
          onPlatform = true;
          pos.y = b.pos.y - baseHeight * 1.5f + 2;
          if(b.horizontal) vel = new Vector3D (b.vel.x, 0);
          else vel = new Vector3D (0, b.vel.y);
        }
      }
      
      //Walker Hit Test
      if (onFloor == true || onPlatform == true)
      {
   for(int i=0; i<myWalkersList.size(); i++) {
        Walker b = (Walker)myWalkersList.get(i);
        Vector3D result = lineIntersectionSlowerWalker(this, b);
        if(result == null) isSlowing = false;
        if(result != null) {
            b.isSlowing = true;
            isSlowing = true;
            b.walkingRightTemp = b.walkingRight;
            if (b.walkingRight) b.vel = new Vector3D(0.1f, b.vel.y * .9f); 
            else b.vel = new Vector3D(-0.1f, b.vel.y * .9f);
        }
      }
   }
   }
   
   if(hitDetect(mouseX, mouseY)) isMouseOver = true;
    else isMouseOver = false;
 }
 
  public void mousePressed() {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      isSlowing = false;
      mouseIsDragging = true;
      onFloor = false;
      vel = new Vector3D (0, 0);
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
  }
  
  public void mouseReleased() {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
  }
  
  public boolean hitDetect(float theX, float theY) {
    if(theX >= pos.x - baseWidth * .5f && theX <= pos.x + baseWidth * .5f && theY >= pos.y - baseHeight * .5f && theY <= pos.y + baseHeight * .5f) return true;
    else return false;
  }
  
  
}

public class Walker
{
 Vector3D pos;
 Vector3D originalPos;
 Vector3D vel;
 Vector3D gravity;
 int walkerWidth = 20;
 int walkerHeight = 30;
 boolean walkingRight;
 boolean walkingRightTemp;
 boolean hasHit;
 boolean onFloor;
 boolean isDragging = false;
 boolean isMouseOver = false;
 boolean justHit;
 boolean isJumping = false;
 boolean isBoosting = false;
 boolean isSlowing = false;
float offsetX;
float offsetY;
float radius = 40;
boolean thisWalker = false;
boolean direction;
float Degrees = 0;
float dog = 0.01f;
float speed = 15;
float friction = 0;
boolean blink = true;
float waitTime = 150;
float rradius = 4;
float multiplier = 1;
  
 public Walker(float _x, float _y, boolean _direction)
{
 originalPos = new Vector3D(_x, _y);
 pos = new Vector3D(_x, _y); 
 vel = new Vector3D(0, 0);
 gravity = new Vector3D(0, .1f);
 hasHit = false;
 onFloor = false;
 isDragging = true;
 direction = _direction;
 walkingRight = direction;
}

 public Walker(XMLElement walkerNode)
  {
    float tempX = walkerNode.getFloatAttribute("posX");
    float tempY = walkerNode.getFloatAttribute("posY");
    this.originalPos = new Vector3D(tempX, tempY);
    this.pos = new Vector3D(tempX, tempY);
    vel = new Vector3D (0, 0);
    gravity = new Vector3D(0, .1f);
    hasHit = false;
    onFloor = false;
    isDragging = false;
    this.direction = convertInt(walkerNode.getIntAttribute("direction"));
    this.walkingRight = direction;
isDragging = false;
 mouseIsDragging = false;
 isMouseOver = false;
  }

public XMLElement toXML()
  {
    XMLElement walkerNode = new XMLElement("walker");
    walkerNode.addAttribute("posX", pos.x);
    walkerNode.addAttribute("posY", pos.y);
    walkerNode.addAttribute("direction", convertBoolean(direction));
    return walkerNode;
  }
 
 public void draw()
 {
   ellipseMode(CENTER);
    strokeWeight(2);
    fill(100, 80, 80);
    stroke(80, 70, 70);
    line(pos.x, pos.y - walkerHeight * .5f, pos.x, pos.y - walkerHeight * .5f - 15);
    rectMode(CENTER);
    rect(pos.x, pos.y, walkerWidth, walkerHeight);
    ellipseMode(CENTER);
    if (blink)
    {
    fill(80, 0, 0, 100);
    stroke(255, 200, 200, 100);
    } else {
    fill(255, 0, 0, 100);
    stroke(255, 200, 200, 100);
    }
    ellipse(pos.x + .5f, pos.y - walkerHeight * .5f - 15, rradius * 2, rradius * 2);
    noStroke();
    ellipse(pos.x + .5f, pos.y - walkerHeight * .5f - 15, rradius * 1.6f, rradius * 1.6f);
    ellipse(pos.x + .5f, pos.y - walkerHeight * .5f - 15, rradius * 1, rradius * 1);
    ellipse(pos.x + .5f, pos.y - walkerHeight * .5f - 15, rradius * .6f, rradius * .6f);
    fill(0, 150);
    stroke(128, 175);
    pushMatrix();
    translate(pos.x/2 + .5f, pos.y/2 + 8);
    scale(.5f);
    pushMatrix();
    translate(pos.x - 12, pos.y + walkerHeight * .5f);
    rotate(dog);
    ellipse(0, 0, rradius * multiplier * 3, rradius * multiplier * 3);
    ellipse(0, 0, rradius * multiplier * 2, rradius * multiplier * 2);
    ellipse(0, 0, rradius/(multiplier*2), rradius/(multiplier*2));
    ellipse(0, 0, rradius/(multiplier*3), rradius/(multiplier*3));
    ellipse(0, 0, rradius * 2.5f, rradius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x, pos.y + walkerHeight * .5f);
    rotate(dog);
    ellipse(0, 0, rradius * multiplier * 3, rradius * multiplier * 3);
    ellipse(0, 0, rradius * multiplier * 2, rradius * multiplier * 2);
    ellipse(0, 0, rradius/(multiplier*2), rradius/(multiplier*2));
    ellipse(0, 0, rradius/(multiplier*3), rradius/(multiplier*3));
    ellipse(0, 0, rradius * 2.5f, rradius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x + 12, pos.y + walkerHeight * .5f);
    rotate(dog);
    ellipse(0, 0, rradius * multiplier * 3, rradius * multiplier * 3);
    ellipse(0, 0, rradius * multiplier * 2, rradius * multiplier * 2);
    ellipse(0, 0, rradius/(multiplier*2), rradius/(multiplier*2));
    ellipse(0, 0, rradius/(multiplier*3), rradius/(multiplier*3));
    ellipse(0, 0, rradius * 2.5f, rradius / 2);
    popMatrix();
    popMatrix();
    strokeWeight(1);
    if(testMode) animate();
    if(hitDetect(mouseX, mouseY))
   {
     noFill();
     stroke(255, 0, 0, 128);
   rectMode(CENTER);
   rect(pos.x, pos.y, walkerWidth, walkerHeight);
   }
   update();
 }
 
 public void animate()
  {
    dog+=speed;
    speed -= friction;
    friction += .05f;
    if (speed <= 0)
    {
      speed = 15;
      friction = 0;
      dog = 0;
    }
    waitTime -=1;
   if (waitTime <= 0) 
   {
     blink = !blink;
     if (blink) waitTime = 150;
     else waitTime = 10;
   }
  }
 
 public void update()
 { 
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
   }  else {
     if (testMode)
     {
    if (isJumping == false) 
     {
   if (onFloor == true) 
   {
   gravity = new Vector3D (gravity.x, 0);
   } else {
      gravity = new Vector3D (gravity.x, 0.1f);
   }
     }
     if (onFloor == true) isJumping = false;
    isSlowing = false;
     for(int i=0; i<mySlowersList.size(); i++)
  {
    Slower b = (Slower)mySlowersList.get(i);
    if (b.isSlowing == true) isSlowing = true;
  }
     if (isBoosting == false && isSlowing == false)
     {
     if(walkingRight == true)
   {
     vel = new Vector3D(1, vel.y);
   } else {
     vel = new Vector3D(-1, vel.y);
   }
     } else {
       if(walkingRight != walkingRightTemp) 
       {
         vel.x = -vel.x;
         gravity.x = -gravity.x;
       }
       walkingRightTemp = walkingRight;
     }
     if (vel.x >= 1 || vel.x <= -1) {}else{isBoosting = false; gravity.x = 0;}
   vel.add(gravity);
   pos.add(vel);
   if (pos.y > height + 500 || pos.y < -100) 
   {
    pos = originalPos.copy();
    vel = new Vector3D (0, 0);
    onFloor = false;
    isJumping = false;
    isBoosting = false;
   }
   if (vel.x >= 20) vel.x = 20;
   if (vel.x <= - 20) vel.x = -20;
   //HIT TEST
   //Wall Hit Test
      for(int i=0; i<myWallsList.size(); i++) {
        Wall b = (Wall)myWallsList.get(i);
        Vector3D result = lineIntersectionWalkerWall(this, b);
        if(result != null) {
          walkingRight = !walkingRight;
        }
      }
      //Floor Hit Test
      onFloor = false;
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionWalkerFloor(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y - walkerHeight * .5f;
          vel = new Vector3D (vel.x,0);
        }
      }
      
      //Floor Bottom Hit Test
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionWalkerFloorBottom(this, b);
        if(result != null) {
          vel = new Vector3D (vel.x, 2);
          gravity = new Vector3D (gravity.x, .1f);
        }
      }
      
      //Platform Hit Test
      onFloor = false;
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionWalkerPlatform(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y - walkerHeight * .5f;
          vel = new Vector3D (vel.x, 0);
          if (!b.horizontal)  vel = new Vector3D (vel.x, b.vel.y);
        }
      }
      
      //Platform Bottom Hit Test
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionWalkerPlatformBottom(this, b);
        if(result != null) {
          vel = new Vector3D (vel.x, 2);
          gravity = new Vector3D (gravity.x, .1f);
        }
      }
      
      //Walker Hit Test
   for(int i=0; i<myWalkersList.size(); i++) {
        Walker b = (Walker)myWalkersList.get(i);
        if (b != this)
        {
        Vector3D result = lineIntersectionWalkerWalker(this, b);
        if(result == null) justHit = false;
        if(result != null) {
          if(justHit == false) walkingRight = !walkingRight;
          justHit = true;
        }
        }
      }
   }  
   }
   
   if(hitDetect(mouseX, mouseY)) isMouseOver = true;
    else isMouseOver = false;
 }
 
  public void mousePressed() {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
      onFloor = false;
      vel = new Vector3D (0, 0);
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
  }
  
  public void mouseReleased() {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
  }
  
  public boolean hitDetect(float theX, float theY) {
    if(theX >= pos.x - walkerWidth * .5f && theX <= pos.x + walkerWidth * .5f && theY >= pos.y - walkerHeight * .5f && theY <= pos.y + walkerHeight * .5f) return true;
    else return false;
  }
  
  
}

public class Wall
{
 Vector3D pos;
 float wallWidth;
 float wallHeight;
 float inX, inY, outX, outY;
 float wallType;
 boolean invisible;
  int copyNumber;
  PImage wallPattern;
 boolean isDragging = true;
 boolean mouseIsDragging = true;
 boolean isMouseOver = true;
 float offsetX;
 float offsetY;
 boolean thisWall = false;
 float mouseChange;
 float mouseOld;
 float mouseOldX;
 float mouseOldY;
 boolean changingSize = false;
 boolean deletes;
 boolean keyJustPressed = false;
 boolean isDead = false;
 boolean snapX;
 boolean snapY;
 boolean snapTop;
 boolean snapLeft;
 float mouseOffsetY;
 
 public Wall (float _x, float _y, float _width, float _height, int _wallType, boolean _deletes) 
 {
 pos = new Vector3D(_x, _y);
 wallWidth = _width;
 wallHeight = _height;
 inX = _x;
 inY = _y;
 outX = inX;
 outY = inY + _height;
 wallType = _wallType;
 invisible = false;
 wallPattern = loadImage("texture.png");
 deletes = _deletes;
 isDead = !deletes;
 }
 
  public Wall (float _x, float _y, float _width, float _height, int _wallType, boolean _deletes, boolean _invisible) 
 {
 pos = new Vector3D(_x, _y);
 wallWidth = _width;
 wallHeight = _height;
 inX = _x;
 inY = _y;
 outX = inX;
 outY = inY + _height;
 wallType = _wallType;
 invisible = _invisible;
 deletes = _deletes;
 isDead = !deletes;
 wallPattern = loadImage("texture.png");
 }
  public Wall(XMLElement wallNode)
  {
    float tempX = wallNode.getFloatAttribute("posX");
    float tempY = wallNode.getFloatAttribute("posY");
    this.pos = new Vector3D(tempX, tempY);
    this.wallWidth = wallNode.getFloatAttribute("wallWidth");
    this.wallHeight = wallNode.getFloatAttribute("wallHeight");
    this.wallType = wallNode.getFloatAttribute("wallType");
    this.deletes = convertInt(wallNode.getIntAttribute("deletes"));
    this.invisible = convertInt(wallNode.getIntAttribute("invisible"));
 wallPattern = loadImage("texture.png");
isDragging = false;
 mouseIsDragging = false;
 isMouseOver = false;
  }
 
 public XMLElement toXML()
  {
    XMLElement wallNode = new XMLElement("wall");
    wallNode.addAttribute("posX", pos.x);
    wallNode.addAttribute("posY", pos.y);
    wallNode.addAttribute("wallWidth", wallWidth);
    wallNode.addAttribute("wallHeight", wallHeight);
    wallNode.addAttribute("wallType", wallType);
    wallNode.addAttribute("deletes", convertBoolean(deletes));
    wallNode.addAttribute("invisible", convertBoolean(invisible));
    return wallNode;
  }
 
 public void draw()
 {
   if (!isDead)
   {
   if (!invisible)
   {
     rectMode(CENTER);
   noStroke();
   fill(50, 250);
   rect(pos.x + wallWidth * .5f, pos.y + wallHeight * .5f, wallWidth, wallHeight);
   }
   update();
 inX = pos.x;
 inY = pos.y;
 outX = inX;
 outY = inY + wallHeight;
   } else {
     
   inX = 0;
 inY = 0;
 outX = 0;
 outY = 0;
     
   }
   if(!testMode && !invisible)
   {
     rectMode(CENTER);
   noStroke();
   fill(50, 250);
   rect(pos.x + wallWidth * .5f, pos.y + wallHeight * .5f, wallWidth, wallHeight);
   update();
   if (!deletes) 
   {
     noStroke();
     fill(0, 128);
   rectMode(CENTER);
   rect(pos.x + wallWidth * .5f, pos.y + wallHeight * .5f, wallWidth, wallHeight);
   }
   if (thisWall) 
   {
     noStroke();
     fill(255, 0, 0, 128);
   rectMode(CENTER);
   rect(pos.x + wallWidth * .5f, pos.y + wallHeight * .5f, wallWidth, wallHeight);
   }
   }
 }
 
  public void update()
 {
   if (isDragging == true)
   {
     if (!snapX) pos.x = mouseX - offsetX;
     if (!snapY) pos.y = mouseY - offsetY;
   for(int x=0; x<myFloorsList.size(); x++)
         {
           Floor d = (Floor)myFloorsList.get(x);
           if (pos.x - d.pos.x >= -5 && pos.x - d.pos.x <= 5 || pos.x - d.floorWidth  + 15 - d.pos.x <= 5 && pos.x - d.floorWidth + 15 -d.pos.x >= -5) 
           {
             if (!snapX) mouseOldX = mouseX;
             snapX = true;
             if (pos.x - d.pos.x >= -5 && pos.x - d.pos.x <= 5) snapLeft = true;
             else snapLeft = false;
             mouseOffsetY = d.floorWidth;
             if(snapLeft) pos.x = d.pos.x;
             else pos.x = d.pos.x + d.floorWidth - 15;
             println("here");
           }
           if (pos.y - d.pos.y >= -5 && pos.y - d.pos.y <= 5 || pos.y + wallHeight - 15 - d.pos.y >= -5 && pos.y + wallHeight - 15 - d.pos.y <= 5) 
           {
             if(!snapY) mouseOldY = mouseY;
             snapY = true;
             if (pos.y - d.pos.y >= -5 && pos.y - d.pos.y <= 5) snapTop = true;
             else snapTop = false;
             if (snapTop) pos.y = d.pos.y;
             else pos.y = d.pos.y - wallHeight + 15;
           }
         }
         //if (!snapTop) mouseOldX = mouseOldX + mouseOffsetY;
         //if (!snapLeft) mouseOldY = mouseOldY + wallWidth;
           if (mouseX - mouseOldX >= 5 || mouseX - mouseOldX <= -5) 
           {
           snapX = false;pos.x = mouseX;
           }
           if (mouseY - mouseOldY >= 5 || mouseY - mouseOldY <= -5)
          {
           snapY = false;
           pos.y = mouseY;
          }
         
   }
   if (changingSize)
   {
     mouseChange = mouseY - mouseOld;
     wallHeight = wallHeight + mouseChange;
     mouseOld = mouseY;
   }
   if (hitTest(mouseX, mouseY)) isMouseOver = true;
   else isMouseOver = false;
   noFill();
   stroke(255, 255, 0);
   rectMode(CENTER);
   if (hitTest(mouseX, mouseY)) rect(pos.x + wallWidth * .5f, pos.y + wallHeight * .5f, wallWidth, wallHeight);
   if (keyPressed && thisWall && !keyJustPressed)
   {
     if (keyCode == LEFT)
     {
   if (editType == Move)
   {
     pos.x--;
   }
   }
   if (keyCode == RIGHT)
     {
   if (editType == Move)
   {
     pos.x++;
   }
   }
   if (keyCode == UP)
     {
   if (editType == Move)
   {
     pos.y--;
   }
   
   if (editType == EditSize)
   {
     wallHeight--;
   }
   }
   if (keyCode == DOWN)
     {
   if (editType == Move)
   {
     pos.y++;
   }
   if (editType == EditSize)
   {
     wallHeight++;
   }
   }
   keyJustPressed = true;
 }
 }
  
  public void keyReleased()
  {
    keyJustPressed = false;
  }
  
  public void mousePressed() 
  {
    if (editType == Move)
    {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
    }
    offsetX = mouseX - pos.x;
    offsetY = mouseY - pos.y;
    }
    if (editType == EditSize && editMode != NULL_MODE)
    {
      if(isMouseOver && !mouseIsDragging)
      {
      mouseOld = mouseY;
      changingSize = true;
      mouseIsDragging = true;
      }
    }
  }
  
  public void mouseReleased() 
  {
    if(isDragging) {
      mouseIsDragging = false;
      isDragging = false;
    }
    if(changingSize) {
      mouseIsDragging = false;
      changingSize = false;
    }
  }

public boolean hitTest(float theX, float theY)
{
  if (theX >= pos.x && theX <= pos.x + wallWidth)
  {
    if ( theY > pos.y && theY <= pos.y + wallHeight)
    { return true;
    }
  }
  return false;
}
}

  static public void main(String args[]) {     PApplet.main(new String[] { "PuzzleGameLevelEditorV2" });  }}