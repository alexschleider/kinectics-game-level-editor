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
   ellipse(pos.x, pos.y, cradius * .8, cradius * .8);
   ellipse(pos.x, pos.y, cradius * .5, cradius * .5);
   ellipse(pos.x, pos.y, cradius * .3, cradius * .3);
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
