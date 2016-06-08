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
   rect(pos.x + wallWidth * .5, pos.y + wallHeight * .5, wallWidth, wallHeight);
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
   rect(pos.x + wallWidth * .5, pos.y + wallHeight * .5, wallWidth, wallHeight);
   update();
   if (!deletes) 
   {
     noStroke();
     fill(0, 128);
   rectMode(CENTER);
   rect(pos.x + wallWidth * .5, pos.y + wallHeight * .5, wallWidth, wallHeight);
   }
   if (thisWall) 
   {
     noStroke();
     fill(255, 0, 0, 128);
   rectMode(CENTER);
   rect(pos.x + wallWidth * .5, pos.y + wallHeight * .5, wallWidth, wallHeight);
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
   if (hitTest(mouseX, mouseY)) rect(pos.x + wallWidth * .5, pos.y + wallHeight * .5, wallWidth, wallHeight);
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
