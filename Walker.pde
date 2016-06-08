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
float dog = 0.01;
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
 gravity = new Vector3D(0, .1);
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
    gravity = new Vector3D(0, .1);
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
    line(pos.x, pos.y - walkerHeight * .5, pos.x, pos.y - walkerHeight * .5 - 15);
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
    ellipse(pos.x + .5, pos.y - walkerHeight * .5 - 15, rradius * 2, rradius * 2);
    noStroke();
    ellipse(pos.x + .5, pos.y - walkerHeight * .5 - 15, rradius * 1.6, rradius * 1.6);
    ellipse(pos.x + .5, pos.y - walkerHeight * .5 - 15, rradius * 1, rradius * 1);
    ellipse(pos.x + .5, pos.y - walkerHeight * .5 - 15, rradius * .6, rradius * .6);
    fill(0, 150);
    stroke(128, 175);
    pushMatrix();
    translate(pos.x/2 + .5, pos.y/2 + 8);
    scale(.5);
    pushMatrix();
    translate(pos.x - 12, pos.y + walkerHeight * .5);
    rotate(dog);
    ellipse(0, 0, rradius * multiplier * 3, rradius * multiplier * 3);
    ellipse(0, 0, rradius * multiplier * 2, rradius * multiplier * 2);
    ellipse(0, 0, rradius/(multiplier*2), rradius/(multiplier*2));
    ellipse(0, 0, rradius/(multiplier*3), rradius/(multiplier*3));
    ellipse(0, 0, rradius * 2.5, rradius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x, pos.y + walkerHeight * .5);
    rotate(dog);
    ellipse(0, 0, rradius * multiplier * 3, rradius * multiplier * 3);
    ellipse(0, 0, rradius * multiplier * 2, rradius * multiplier * 2);
    ellipse(0, 0, rradius/(multiplier*2), rradius/(multiplier*2));
    ellipse(0, 0, rradius/(multiplier*3), rradius/(multiplier*3));
    ellipse(0, 0, rradius * 2.5, rradius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x + 12, pos.y + walkerHeight * .5);
    rotate(dog);
    ellipse(0, 0, rradius * multiplier * 3, rradius * multiplier * 3);
    ellipse(0, 0, rradius * multiplier * 2, rradius * multiplier * 2);
    ellipse(0, 0, rradius/(multiplier*2), rradius/(multiplier*2));
    ellipse(0, 0, rradius/(multiplier*3), rradius/(multiplier*3));
    ellipse(0, 0, rradius * 2.5, rradius / 2);
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
    friction += .05;
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
      gravity = new Vector3D (gravity.x, 0.1);
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
          pos.y = b.pos.y - walkerHeight * .5;
          vel = new Vector3D (vel.x,0);
        }
      }
      
      //Floor Bottom Hit Test
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionWalkerFloorBottom(this, b);
        if(result != null) {
          vel = new Vector3D (vel.x, 2);
          gravity = new Vector3D (gravity.x, .1);
        }
      }
      
      //Platform Hit Test
      onFloor = false;
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionWalkerPlatform(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y - walkerHeight * .5;
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
          gravity = new Vector3D (gravity.x, .1);
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
    if(theX >= pos.x - walkerWidth * .5 && theX <= pos.x + walkerWidth * .5 && theY >= pos.y - walkerHeight * .5 && theY <= pos.y + walkerHeight * .5) return true;
    else return false;
  }
  
  
}
