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
  if (theX >= pos.x - buttonWidth * .5 && theX <= pos.x + buttonWidth * .5)
  {
    if ( theY > pos.y - buttonHeight * .5 && theY <= pos.y + buttonHeight * .5)
    { return true;
    }
  }
  return false;
}
}
