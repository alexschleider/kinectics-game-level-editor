public class Jumper
{
 Vector3D pos;
 Vector3D vel;
 Vector3D gravity;
 int jumperWidth = 20;
 int jumperHeight = 30;
 boolean walkingRight = true;
 boolean hasHit;
 boolean onFloor;
 boolean onPlatform;
 boolean isDragging;
 boolean isMouseOver = false;
 boolean justHit;
 boolean animating;
float offsetX;
float offsetY;
float offset = .5;
float radius = 4;
  float speed = 1.5;
  boolean expanding = true;
  
 public Jumper(float _x, float _y)
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
   if (hitDetect(mouseX, mouseY))
    {
    stroke(255, 255, 0, 128);
    fill(100, 100, 0, 80);
    } else {
    stroke(0, 128);
    fill(128, 80);
    }
    strokeWeight(2);
    ellipse(pos.x, pos.y, radius * 4, radius);
    ellipse(pos.x, pos.y - offset * 1, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 2, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 3, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 4, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 5, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 6, radius * 8, radius);
    ellipse(pos.x, pos.y - offset * 7, radius * 8, radius);
    strokeWeight(1);
    update();
 }
 
 public void animate()
  {
    if (expanding) this.offset+= speed;
    if (!expanding) this.offset-= speed - .5;
    if (offset >= 7) this.expanding = false;
    if (offset <= .5) {
      this.offset = .5;
      this.expanding = true;
    this.animating = false;
    }
  }
 
 public void update()
 {
   if (isDragging == true)
   {
     pos.x = mouseX - offsetX;
      pos.y = mouseY - offsetY;
      this.animating = false;
      this.offsetY = .5;
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
        if (b.chooserType == 1) 
        {
          b.amount++;
          myJumpersList.remove(this);
        }
     }
   }
     
   //HIT TEST
      //Floor Hit Test
      onFloor = false;
   for(int i=0; i<myFloorsList.size(); i++) {
        Floor b = (Floor)myFloorsList.get(i);
        Vector3D result = lineIntersectionJumperFloor(this, b);
        if(result != null) {
          onFloor = true;
          pos.y = b.pos.y;
          vel = new Vector3D (0,0);
        }
      }
      
      //Platform Hit Test
      onPlatform = false;
   for(int i=0; i<myPlatformsList.size(); i++) {
        MovingPlatform b = (MovingPlatform)myPlatformsList.get(i);
        Vector3D result = lineIntersectionJumperPlatform(this, b);
        if(result != null) {
          onPlatform = true;
          pos.y = b.pos.y - jumperHeight * .5;
          if(b.horizontal) vel = new Vector3D (b.vel.x, 0);
          else vel = new Vector3D (0, b.vel.y);
        }
      }
      
      //Walker Hit Test
      if (onFloor == true || onPlatform == true)
      {
   for(int i=0; i<myWalkersList.size(); i++) {
        Walker b = (Walker)myWalkersList.get(i);
        Vector3D result = lineIntersectionJumperWalker(this, b);
        if(result == null) justHit = false;
        if(result != null) {
          if(justHit == false) 
          {
            b.isJumping = true;
            b.vel = new Vector3D(b.vel.x, -5.5);
          }
          justHit = true;
          this.animating = true;
        }
      }
   }
   }
   
   if(hitDetect(mouseX, mouseY)) isMouseOver = true;
    else isMouseOver = false;
    
    
    if (animating) animate();
 }
 
  public void mousePressed() {
    if(isMouseOver && !mouseIsDragging) {
      isDragging = true;
      mouseIsDragging = true;
      onFloor = false;
      vel = new Vector3D (0, 0);
      offset = .5;
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
    if(d < radius * 2) return true;
    else return false;
    
  }
  
  
}
