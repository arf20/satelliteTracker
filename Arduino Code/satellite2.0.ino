#include <Servo.h>
#include "stringSplitter.h"

Servo azimuthServo;
Servo elevationServo;

char numSeparator = 'S';
char decPointSeparator = '.';

int strErr = 0;

String str = "";
float x = 0;
float y = 0;

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(10);

  pinMode(2, OUTPUT);
  pinMode(5, INPUT);
  pinMode(6, INPUT);
  pinMode(7, OUTPUT);

  azimuthServo.attach(3);
  elevationServo.attach(4);
  
  azimuthServo.write(90);
  elevationServo.write(90);

  digitalWrite(2, LOW);
}

void loop() {
  if (Serial.available() > 0) {   //Get string (char[])
    digitalWrite(7, HIGH);
    str = Serial.readString();

    strErr = splitByCharDouble(str, numSeparator, &x, &y);              //Split str in to xstr and ystr by numSeparator
    //strErr = checkStringSeparator(str, numSeparator);

    if (strErr == -1) {
      unrecognicedStringErr();
    }
    else if (strErr == 0) {
      updateServos(x, y);
      //Serial.print(x);
      //Serial.print("    ");
      //Serial.println(y);
      delay(5);
      digitalWrite(7, LOW);
    }
    else if (strErr == -2) {
      unrecognicedStringErr();
    }
    else {
      unrecognicedStringErr();
    }
  }
  if (digitalRead(5) == HIGH) {
    calibrate();
  }

  if (digitalRead(6) == HIGH) {
    updateServos(90, 90);
  }
}

void unrecognicedStringErr() {
  digitalWrite(2, HIGH);
  delay(250);
  digitalWrite(2, LOW);
}

void updateServos(float x, float y) {
  //map(x, 0, 360, -10, 360);
  azimuthServo.write(x);
  elevationServo.write(y);
}

void calibrate() {
  azimuthServo.write(90);
  elevationServo.write(90);
  delay(5000);
  azimuthServo.write(0);
  elevationServo.write(90);
  delay(5000);
  azimuthServo.write(90);
  elevationServo.write(90);
  delay(5000);
  azimuthServo.write(180);
  elevationServo.write(90);
  delay(5000);
  azimuthServo.write(270);
  elevationServo.write(90);
  delay(5000);
  azimuthServo.write(0);
  elevationServo.write(90);
  delay(5000);
  azimuthServo.write(0);
  elevationServo.write(0);
  delay(5000);
  azimuthServo.write(0);
  elevationServo.write(90);
  delay(5000);
  azimuthServo.write(0);
  elevationServo.write(180);
  delay(5000);
  azimuthServo.write(0);
  elevationServo.write(270);
  delay(5000);
  azimuthServo.write(0);
  elevationServo.write(0);
  delay(5000);
  azimuthServo.write(90);
  elevationServo.write(90);
}

