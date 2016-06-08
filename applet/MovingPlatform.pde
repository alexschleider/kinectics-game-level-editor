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
   rect(pos.x + floorWidth * .5, pos.y + floorHeight * .5, floorWidth, floorHeight);
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
   if (hitTest(mouseX, mouseY)) rect(pos.x + floorWidth * .5, pos.y + floorHeight * .5, floorWidth, floorHeight);
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
