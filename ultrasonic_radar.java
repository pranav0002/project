import processing.serial.*; // Imports library for serial communication
import java.awt.event.KeyEvent; // Imports library for reading the data from the serial port
import java.io.IOException;
import java.io.Serial;

Serial myPort; // Defines Object Serial

// Defines variables
String angle = "";
String distance = "";
String data = "";
String noObject;
float pixsDistance;
int iAngle, iDistance;
int index1 = 0;
int index2 = 0;
PFont orcFont;

//Setup: Initializes the window size, enables smooth drawing, sets up serial communication, and configures the serial buffer to read data until the '.' character.
void setup() {
    size(1200, 700); // Sets the window size
    smooth();
    myPort = new Serial(this, "COM7", 9600); // Initializes serial communication on COM7 at 9600 baud rate
    myPort.bufferUntil('.'); // Reads data until the '.' character
  }
  
  //Draw: Continuously executes to update the display. It fills the background with green, simulates motion blur, and calls functions to draw the radar, line, object, and text.
  void draw() {
    fill(98, 245, 31);
    // Simulates motion blur and slow fade of the moving line
    noStroke();
    fill(0, 4); 
    rect(0, 0, width, height - height * 0.065); 
  
    fill(98, 245, 31); // Green color
    // Calls the functions for drawing the radar
    drawRadar(); 
    drawLine();
    drawObject();
    drawText();
  }

//Serial Event: Triggered when data is available on the serial port. It reads the data, extracts the angle and distance, and converts them from strings to integers.
  void serialEvent(Serial myPort) {
    // Reads the data from the Serial Port up to the character '.' and puts it into the String variable "data".
    data = myPort.readStringUntil('.');
    data = data.substring(0, data.length() - 1);
  
    index1 = data.indexOf(","); // Finds the character ',' and puts it into the variable "index1"
    angle = data.substring(0, index1); // Extracts the angle
    distance = data.substring(index1 + 1, data.length()); // Extracts the distance
  
    // Converts the String variables into Integer
    iAngle = int(angle);
    iDistance = int(distance);
  }
  
  //Draw Radar: Draws the radar grid with arcs and angle lines.
  void drawRadar() {
    pushMatrix();
    translate(width / 2, height - height * 0.074); // Moves the starting coordinates to a new location
    noFill();
    strokeWeight(2);
    stroke(98, 245, 31);
    // Draws the arc lines
    arc(0, 0, (width - width * 0.0625), (width - width * 0.0625), PI, TWO_PI);
    arc(0, 0, (width - width * 0.27), (width - width * 0.27), PI, TWO_PI);
    arc(0, 0, (width - width * 0.479), (width - width * 0.479), PI, TWO_PI);
    arc(0, 0, (width - width * 0.687), (width - width * 0.687), PI, TWO_PI);
    // Draws the angle lines
    line(-width / 2, 0, width / 2, 0);
    line(0, 0, (-width / 2) * cos(radians(30)), (-width / 2) * sin(radians(30)));
    line(0, 0, (-width / 2) * cos(radians(60)), (-width / 2) * sin(radians(60)));
    line(0, 0, (-width / 2) * cos(radians(90)), (-width / 2) * sin(radians(90)));
    line(0, 0, (-width / 2) * cos(radians(120)), (-width / 2) * sin(radians(120)));
    line(0, 0, (-width / 2) * cos(radians(150)), (-width / 2) * sin(radians(150)));
    line((-width / 2) * cos(radians(30)), 0, width / 2, 0);
    popMatrix();
  }
  
  //Draw Line: Draws the scanning line in green based on the current angle.
  void drawLine() {
    pushMatrix();
    strokeWeight(9);
    stroke(30, 250, 60);
    translate(width / 2, height - height * 0.074); // Moves the starting coordinates to a new location
    line(0, 0, (height - height * 0.12) * cos(radians(iAngle)), -(height - height * 0.12) * sin(radians(iAngle))); // Draws the line according to the angle
    popMatrix();
  }
  
  //darw text function : Displays text information such as angle, distance, and range status on the screen.
  void drawText() {
    pushMatrix();
    if (iDistance > 40) {
      noObject = "Out of Range";
    } else {
      noObject = "In Range";
    }
    fill(0, 0, 0);
    noStroke();
    rect(0, height - height * 0.0648, width, height);
    fill(98, 245, 31);
    textSize(25);
  
    text("10cm", width - width * 0.3854, height - height * 0.0833);
    text("20cm", width - width * 0.281, height - height * 0.0833);
    text("30cm", width - width * 0.177, height - height * 0.0833);
    text("40cm", width - width * 0.0729, height - height * 0.0833);
    textSize(40);
    text("N_Tech ", width - width * 0.875, height - height * 0.0277);
    text("Angle: " + iAngle + " ", width - width * 0.48, height - height * 0.0277);
    text("Distance: ", width - width * 0.26, height - height * 0.0277);
    if (iDistance < 40) {
      text("        " + iDistance + " cm", width - width * 0.225, height - height * 0.0277);
    }
    textSize(25);
    fill(98, 245, 60);
    translate((width - width * 0.4994) + width / 2 * cos(radians(30)), (height - height * 0.0907) - width / 2 * sin(radians(30)));
    rotate(-radians(-60));
    text("30", 0, 0);
    resetMatrix();
    translate((width - width * 0.503) + width / 2 * cos(radians(60)), (height - height * 0.0888) - width / 2 * sin(radians(60)));
    rotate(-radians(-30));
    text("60", 0, 0);
    resetMatrix();
    translate((width - width * 0.507) + width / 2 * cos(radians(90)), (height - height * 0.0833) - width / 2 * sin(radians(90)));
    rotate(radians(0));
    text("90", 0, 0);
    resetMatrix();
    translate(width - width * 0.513 + width / 2 * cos(radians(120)), (height - height * 0.07129) - width / 2 * sin(radians(120)));
    rotate(radians(-30));
    text("120", 0, 0);
    resetMatrix();
    translate((width - width * 0.5104) + width / 2 * cos(radians(150)), (height - height * 0.0574) - width / 2 * sin(radians(150)));
    rotate(radians(-60));
    text("150", 0, 0);
    popMatrix();
  }