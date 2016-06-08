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
   ellipse(pos.x, pos.y, radius * .8, radius * .8);
   ellipse(pos.x, pos.y, radius * .5, radius * .5);
   ellipse(pos.x, pos.y, radius * .3, radius * .3);
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
