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
float dog = 0.01;
float speed = 15;
float friction = 0;
boolean animating = false;
float multiplier = 1;
  
 public Booster(float _x, float _y)
{
 pos = new Vector3D(_x, _y); 
 vel = new Vector3D(0, 0);
 gravity = new Vector3D(0, .1);
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
    ellipse(0, 0, radius * 2.5, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x - 12, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x + 12, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5, radius / 2);
    popMatrix();
    
    pushMatrix();
    translate(pos.x + 24, pos.y - 5);
    rotate(dog);
    ellipse(0, 0, radius * multiplier * 3, radius * multiplier * 3);
    ellipse(0, 0, radius * multiplier * 2, radius * multiplier * 2);
    ellipse(0, 0, radius/(multiplier*2), radius/(multiplier*2));
    ellipse(0, 0, radius/(multiplier*3), radius/(multiplier*3));
    ellipse(0, 0, radius * 2.5, radius / 2);
    popMatrix();
    strokeWeight(1);
    if (animating) drawAnimation();
    update();
 }
 
 public void drawAnimation()
  {
    dog+=speed;
    speed -= friction;
    friction += .05;
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
      gravity = new Vector3D (0, 0.1);
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
          pos.y = b.pos.y - rboosterHeight * .5 + 5;
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
          pos.y = b.pos.y - rboosterHeight * .5 + 5;
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
     b.vel = new Vector3D(b.vel.x + 10, b.vel.y * 1.5);
      b.gravity = new Vector3D (-0.2, b.gravity.y);
   } else {
     b.vel = new Vector3D(b.vel.x - 10, b.vel.y * 1.5);
      b.gravity = new Vector3D (0.2, b.gravity.y);
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
    if(theX >= pos.x - rboosterWidth * .5 && theX <= pos.x + rboosterWidth * .5 && theY >= pos.y - rboosterHeight * .5 && theY <= pos.y + rboosterHeight * .5) return true;
    else return false;
  }
  
  
}
