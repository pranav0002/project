//Libraries and Definitions:      
#include <Servo.h>  // Includes the Servo library to control the servo motor

#define trigPin 8  // Defines the trig pin for the ultrasonic sensor
#define echoPin 9  // Defines the echo pin for the ultrasonic sensor

//Global Variables:
long duration;  // Stores the duration of the pulse from the ultrasonic sensor
int distance;   // Stores the calculated distance
Servo myservo;  // Creates a Servo object to control the servo motor


//Function to Calculate Distance:
int calculateDistance()
{
  digitalWrite(trigPin, LOW);  // Sets the trig pin low
  delayMicroseconds(2);        // Waits for 2 microseconds
  digitalWrite(trigPin, HIGH); // Sets the trig pin high
  delayMicroseconds(10);       // Waits for 10 microseconds
  digitalWrite(trigPin, LOW);  // Sets the trig pin low again
  duration = pulseIn(echoPin, HIGH);  // Measures the pulse duration
  distance = duration * 0.034 / 2;    // Calculates the distance in cm
  return distance;
}

//Setup Function:
void setup()
{
  pinMode(trigPin, OUTPUT);  // Sets the trig pin as an output
  pinMode(echoPin, INPUT);   // Sets the echo pin as an input
  myservo.attach(11);        // Attaches the servo motor to pin 11
  Serial.begin(9600);        // Starts serial communication at 9600 baud rate
}

//Main Loop
void loop()
{
  int i;
  for (i = 15; i <= 165; i++)  // Rotates the servo from 15 to 165 degrees
  {
    myservo.write(i);          // Writes the angle to the servo
    delay(15);                 // Waits for the servo to move
    calculateDistance();       // Calls the function to measure distance
    Serial.print(i);           // Prints the angle
    Serial.print(",");
    Serial.print(distance);    // Prints the distance
    Serial.print(".");
  }
  for (i = 165; i >= 15; i--)  // Rotates the servo back from 165 to 15 degrees
  {
    myservo.write(i);          // Writes the angle to the servo
    delay(15);                 // Waits for the servo to move
    calculateDistance();       // Calls the function to measure distance
    Serial.print(i);           // Prints the angle
    Serial.print(",");
    Serial.print(distance);    // Prints the distance
    Serial.print(".");
  }
}
