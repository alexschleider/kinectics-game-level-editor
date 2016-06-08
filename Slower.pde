public class Slower
{
 Vector3D pos;
 Vector3D vel;
 Vector3D gravity;
 int slowerWidth = 20;
 int slowerHeight = 30;
 boolean walkingRight = true;
 boolean hasHit;
 boolean onFloor;
 boolean onPlatform;
 boolean isDragging;
 boolean isMouseOver = false;
 boolean justHit;
 boolean isSlowing;
float offsetX;
float offsetY;
float radius = 40;
float baseHeight = 5;
float baseWidth = 30;
float speed = 1;
float friction = 0;
float expandAmount = -1;
boolean expanded = false;
boolean animating = false;
  
 public Slower(float _x, float _y)
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
   pushMatrix();
    translate(pos.x, pos.y);
    strokeWeight(2);
    rectMode(CENTER);
    if(hitDetect(mouseX,mouseY))
    {
    stroke(100, 100, 0, 200);
    fill(100, 100, 200);
    } else {
    stroke(0, 200);
    fill(0, 200);
    }
    rect(0, 7, baseWidth, baseHeight);
    strokeWeight(2);
    line(-baseWidth/2, 5, -baseWidth/2, expandAmount);
    line(baseWidth/2, 5, baseWidth/2, expandAmount);
    if (hitDetect (mouseX, mouseY))
    {
    stroke(100, 100, 0, 150);
    fill(150, 150, 0, 100);
    } else {
    stroke(0, 50, 100, 150);
    fill(0, 50, 150, 100);
    }
    rect(0, expandAmount/2, baseWidth, expandAmount);
    strokeWeight(1);
    popMatrix();
    if(!expanded && !isSlowing) animating = true;
    if(expanded && isSlowing) animating = true;
    if (animating) animate();
   update();
 }
 
 public void animate()
 {
    if (expanded)
    {
      expandAmount -=2;
      if (expandAmount <= -30)
      {
        expanded = false;
        animating = false;
      }
    }
    
    if (!expanded)
    {
      expandAmount +=2;
      if (expandAmount >= 0)
      {
        expanded = true;
        animating = false;
      }
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
        if (b.chooserType == 3) 
        {
          b.amount++;
          mySlowersList.remove(this);
        }
     }
   }
     
   //HIT TEST
      //Floor Hit Test
      onFloor = false;
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionSlowerFloor(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y - baseHeight * 1.5 + 2;
          vel = new Vector3D (0,0);
        }
      }
      
      //Platform Hit Test
      onPlatform = false;
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionSlowerPlatform(this, b);
        if(result != null) {
          onPlatform = true;
          pos.y = b.pos.y - baseHeight * 1.5 + 2;
          if(b.horizontal) vel = new Vector3D (b.vel.x, 0);
          else vel = new Vector3D (0, b.vel.y);
        }
      }
      
      //Walker Hit Test
      if (onFloor == true || onPlatform == true)
      {
   for(int i=0; i<myWalkersList.size(); i++) {
        Walker b = (Walker)myWalkersList.get(i);
        Vector3D result = lineIntersectionSlowerWalker(this, b);
        if(result == null) isSlowing = false;
        if(result != null) {
            b.isSlowing = true;
            isSlowing = true;
            b.walkingRightTemp = b.walkingRight;
            if (b.walkingRight) b.vel = new Vector3D(0.1, b.vel.y * .9); 
            else b.vel = new Vector3D(-0.1, b.vel.y * .9);
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
      isSlowing = false;
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
    if(theX >= pos.x - baseWidth * .5 && theX <= pos.x + baseWidth * .5 && theY >= pos.y - baseHeight * .5 && theY <= pos.y + baseHeight * .5) return true;
    else return false;
  }
  
  
}
