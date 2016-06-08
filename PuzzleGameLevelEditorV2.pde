import noc.*;
import proxml.*;

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




void setup()
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

void draw()
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
    text(int(bonusScore), 342, 53);
    text(int(score), 492, 53);
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
       d.gravity = new Vector3D (0, 0.1);
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
  if (fadingIn || fadingOut) fill(0, fadeTimer * 10.2);
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
Vector3D lineIntersectionWalkerWall(Walker walker, Wall l1) {
  if(!walker.walkingRight) return lineIntersection(walker.pos.x - walker.walkerWidth * .5, walker.pos.y - walker.walkerHeight * .5, walker.pos.x + walker.walkerWidth * .5, walker.pos.y + walker.walkerHeight * .5, l1.inX + l1.wallWidth, l1.inY, l1.outX + l1.wallWidth, l1.outY);
  else return lineIntersection(walker.pos.x - walker.walkerWidth * .5, walker.pos.y - walker.walkerHeight * .5, walker.pos.x + walker.walkerWidth * .5, walker.pos.y + walker.walkerHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Walker-Floor Detection
Vector3D lineIntersectionWalkerFloor(Walker walker, Floor l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5, walker.pos.y - walker.walkerHeight * .5, walker.pos.x + walker.walkerWidth * .5, walker.pos.y + walker.walkerHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Walker-FloorBottom Detection
Vector3D lineIntersectionWalkerFloorBottom(Walker walker, Floor l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5, walker.pos.y - walker.walkerHeight * .5, walker.pos.x + walker.walkerWidth * .5, walker.pos.y + walker.walkerHeight * .5, l1.inX, l1.inY + l1.floorHeight, l1.outX, l1.outY + l1.floorHeight);
}
//Walker-Platform Detection
Vector3D lineIntersectionWalkerPlatform(Walker walker, MovingPlatform l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5, walker.pos.y - walker.walkerHeight * .5, walker.pos.x + walker.walkerWidth * .5, walker.pos.y + walker.walkerHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Walker-PlatformBottom Detection
Vector3D lineIntersectionWalkerPlatformBottom(Walker walker, MovingPlatform l1) {
  return lineIntersection(walker.pos.x - walker.walkerWidth *.5, walker.pos.y - walker.walkerHeight * .5, walker.pos.x + walker.walkerWidth * .5, walker.pos.y + walker.walkerHeight * .5, l1.inX, l1.inY + l1.floorHeight, l1.outX, l1.outY + l1.floorHeight);
}
//Walker-Walker Detection
Vector3D lineIntersectionWalkerWalker(Walker walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.walkerWidth * .5, walker1.pos.y - walker1.walkerHeight * .5, walker1.pos.x + walker1.walkerWidth * .5, walker1.pos.y + walker1.walkerHeight * .5, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}
//Jumper-Floor Detection
Vector3D lineIntersectionJumperFloor(Jumper jumper, Floor l1) {
  return lineIntersection(jumper.pos.x - jumper.jumperWidth *.5, jumper.pos.y - jumper.jumperHeight * .5, jumper.pos.x + jumper.jumperWidth * .5, jumper.pos.y + jumper.jumperHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Jumper-Platform Detection
Vector3D lineIntersectionJumperPlatform(Jumper jumper, MovingPlatform l1) {
  return lineIntersection(jumper.pos.x - jumper.jumperWidth *.5, jumper.pos.y - jumper.jumperHeight * .5, jumper.pos.x + jumper.jumperWidth * .5, jumper.pos.y + jumper.jumperHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Jumper-Walker Detection
Vector3D lineIntersectionJumperWalker(Jumper walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.jumperWidth * .5, walker1.pos.y - walker1.jumperHeight * .5, walker1.pos.x + walker1.jumperWidth * .5, walker1.pos.y + walker1.jumperHeight * .5, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}
//Booster-Floor Detection
Vector3D lineIntersectionBoosterFloor(Booster booster, Floor l1) {
  return lineIntersection(booster.pos.x - booster.boosterWidth *.5, booster.pos.y - booster.boosterHeight * .5, booster.pos.x + booster.boosterWidth * .5, booster.pos.y + booster.boosterHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Booster-Platform Detection
Vector3D lineIntersectionBoosterPlatform(Booster booster, MovingPlatform l1) {
  return lineIntersection(booster.pos.x - booster.boosterWidth *.5, booster.pos.y - booster.boosterHeight * .5, booster.pos.x + booster.boosterWidth * .5, booster.pos.y + booster.boosterHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Booster-Walker Detection
Vector3D lineIntersectionBoosterWalker(Booster walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.boosterWidth * .5, walker1.pos.y - walker1.boosterHeight * .5, walker1.pos.x + walker1.boosterWidth * .5, walker1.pos.y + walker1.boosterHeight * .5, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}
//Slower-Floor Detection
Vector3D lineIntersectionSlowerFloor(Slower slower, Floor l1) {
  return lineIntersection(slower.pos.x - slower.slowerWidth *.5, slower.pos.y - slower.slowerHeight * .5, slower.pos.x + slower.slowerWidth * .5, slower.pos.y + slower.slowerHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Slower-Platform Detection
Vector3D lineIntersectionSlowerPlatform(Slower slower, MovingPlatform l1) {
  return lineIntersection(slower.pos.x - slower.slowerWidth *.5, slower.pos.y - slower.slowerHeight * .5, slower.pos.x + slower.slowerWidth * .5, slower.pos.y + slower.slowerHeight * .5, l1.inX, l1.inY, l1.outX, l1.outY);
}
//Slower-Walker Detection
Vector3D lineIntersectionSlowerWalker(Slower walker1, Walker walker2) {
  return lineIntersection(walker1.pos.x - walker1.slowerWidth * .5, walker1.pos.y - walker1.slowerHeight * .5, walker1.pos.x + walker1.slowerWidth * .5, walker1.pos.y + walker1.slowerHeight * .5, walker2.pos.x - walker2.walkerWidth, walker2.pos.y, walker2.pos.x + walker2.walkerWidth, walker2.pos.y);
}


Vector3D lineIntersection( float l1_inX, float l1_inY, float l1_outX, float l1_outY,
float l2_inX, float l2_inY, float l2_outX, float l2_outY)
{
  float denom = ((l1_outY - l1_inY)*(l2_outX - l2_inX)) -
    ((l1_outX - l1_inX)*(l2_outY - l2_inY));

  float nume_a = ((l1_outX - l1_inX)*(l2_inY - l1_inY)) - 
    ((l1_outY - l1_inY)*(l2_inX - l1_inX));

  float nume_b = ((l2_outX - l2_inX)*(l2_inY - l1_inY)) -
    ((l2_outY - l2_inY)*(l2_inX - l1_inX));

  if(denom == 0.0)
  {
    if(nume_a == 0.0 && nume_b == 0.0)
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
