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
   rect(pos.x + floorWidth * .5, pos.y + floorHeight * .5, floorWidth, floorHeight);
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
   rect(pos.x + floorWidth * .5, pos.y + floorHeight * .5, floorWidth, floorHeight);
   update();
   if (!deletes) 
   {
     noStroke();
     fill(0, 128);
   rectMode(CENTER);
   rect(pos.x + floorWidth * .5, pos.y + floorHeight * .5, floorWidth, floorHeight);
   }
   }
   if (thisFloor) 
   {
     noStroke();
     fill(255, 0, 0, 128);
   rectMode(CENTER);
   rect(pos.x + floorWidth * .5, pos.y + floorHeight * .5, floorWidth, floorHeight);
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
   if (hitTest(mouseX, mouseY)) rect(pos.x + floorWidth * .5, pos.y + floorHeight * .5, floorWidth, floorHeight);
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
