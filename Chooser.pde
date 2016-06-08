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
      text("J", pos.x - .1 * chooserWidth, pos.y);
      fill(0);
      noStroke();
      textSize(12);
      textAlign(LEFT);
      text("x" + amount, pos.x + chooserWidth * .5 + 5, pos.y + chooserHeight * .5);
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
      text("B", pos.x - .1 * chooserWidth, pos.y);
      fill(0);
      noStroke();
      textSize(12);
      textAlign(LEFT);
      text("x" + amount, pos.x + chooserWidth * .5 + 5, pos.y + chooserHeight * .5);
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
      text("S", pos.x - .1 * chooserWidth, pos.y);
      fill(0);
      noStroke();
      textSize(12);
      textAlign(LEFT);
      text("x" + amount, pos.x + chooserWidth * .5 + 5, pos.y + chooserHeight * .5);
      break;
    
    }
    
    
  }
  
  public boolean hitDetect()
  {
    if( mouseX <= pos.x + chooserWidth * .5 && mouseX >= pos.x - chooserWidth * .5 )
      {
        if( mouseY <= pos.y + chooserHeight * .5 && mouseY >= pos.y - chooserHeight * .5)
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
