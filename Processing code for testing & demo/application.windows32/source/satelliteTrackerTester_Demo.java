import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class satelliteTrackerTester_Demo extends PApplet {



Serial port;

float x = 0, y = 0;
float r = 0, a = 0, e = 0;
float d = 100;
float t = 0;
int p = 50;

String portList = "";
String portStr = "";
String str = "";

public void setup() {
  
  background(0);
  textSize(15);
  portList = arrToString(Serial.list());
  
}

public void draw() {  
  x = zeroToCenter(mouseX, width);
  y = zeroToCenter(mouseY, height);
  
  r = sqrt(pow(x, 2) + pow(y, 2));
  
  a = zeroDegRightToUp(degrees(a = atan2(zeroToCenter(mouseY, width), zeroToCenter(mouseX, height))));
  if (a < 0) { a += 360; }
  
  e = (((float)-90 / (float)400) * r) + 90;
  if (e < 0) { e = 0; }
  
  str = nf(a, 0, 1) + "S" + nf(e, 0, 1);
  str = str.replaceAll(",", ".");
  
  portList = arrToString(Serial.list());
  
  if (millis() - t >= p) {
    if (port != null) {
      port.write(str);
    }
    println(str);
    t = millis();
  }
  
  background(0);
  noFill();
  stroke(255);
  text(portList, 650, 20);
  text(str, 20, 20);
  text(portStr, 20, 40);
  text(p + "ms", 20, 60);
  //ellipse(width / 2, height / 2, width, height);
  ellipse(centerToZero(0, width), centerToZero(0, height), width, height);
  line(centerToZero(0, width), centerToZero(0, height), centerToZero(x, width), centerToZero(y, height));
  text(nf(e, 0, 1).replaceAll(",", ".") + "º", centerToZero(x / 2, width), centerToZero(y / 2, height));
  arc(centerToZero(0, width), centerToZero(0, height), d, d, radians(zeroDegUpToRight(0)), radians(zeroDegUpToRight(a)));
  text(nf(a, 0, 1).replaceAll(",", ".") + "º", centerToZero(cos(radians(zeroDegUpToRight(a / 2))) * (d / 2), width), centerToZero(sin(radians(zeroDegUpToRight(a / 2))) * (d / 2), height));
  
  
  
}

public float zeroToCenter(float num, float axisLen) {
  return num - (axisLen / 2);
}

public float centerToZero(float num, float axisLen) {
  return num + (axisLen / 2);
}

public float zeroDegRightToUp(float a) {
  return a + 90;
}

public float zeroDegUpToRight(float a) {
  return a - 90;
}

public void keyPressed() {
  if (key == '1') {
    if (port != null) { port.stop(); }
    port = new Serial(this, Serial.list()[0], 9600);
    portStr = Serial.list()[0];
  } else if (key == '2') {
    if (port != null) { port.stop(); }
    port = new Serial(this, Serial.list()[1], 9600);
    portStr = Serial.list()[1];
  } else if (key == '3') {
    if (port != null) { port.stop(); }
    port = new Serial(this, Serial.list()[2], 9600);
    portStr = Serial.list()[2];
  } else if (key == '4') {
    if (port != null) { port.stop(); }
    port = new Serial(this, Serial.list()[3], 9600);
    portStr = Serial.list()[3];
  } else if (key == 'd') {
    p += 50;
  } else if (key == 'a') {
    p -= 50;
  }
}

public String arrToString(String strIn[]) {
  String strOut = "";
  for (int i = 0; i < Serial.list().length; i++) {
    strOut += Serial.list()[i] + "\r\n";
  }
  return strOut;
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "satelliteTrackerTester_Demo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
