/*
 * Arduino LEDs
 *
 */

int val = 0; //variable to store the data from the serial port
int leds[] = { 
  2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 }; //number of LEDs
int const numLEDs = sizeof(leds) / (sizeof(int));

void setup(){
  for(int i = 0; i < numLEDs; i++){
    pinMode(leds[i], OUTPUT);
  }
  Serial.begin(9600); // connect to the serial port
}

//recursive greatest common divisor, this is temp, will be hard coded later.
int gcd(int a, int b){
  if(a == 0)
    return b;
  return gcd(b % a, a);
}

void clearArray(unsigned long arr[]){
  for(int i = 0; i < numLEDs; i++)
    arr[i] = 0;
}

void clearLEDs(void){
  for(int i = 0; i < numLEDs; i++){
    digitalWrite(leds[i], LOW);
  }
}

void loop(void){
  val = Serial.read(); // read the serial port
top:
  
  //Criss-Cross
  if(val == '0'){
    unsigned int lastSec = 0;
    boolean a = true;
    while(true){
      unsigned long sec = millis() / 200;
      if(lastSec != sec)
        lastSec = sec;
      else
        continue;

      clearLEDs();
      for(int i = 0; i < numLEDs; i++){
        if(a && i % 2 == 1)
          digitalWrite(leds[i], HIGH);
        if(!a && i % 2 == 0)
          digitalWrite(leds[i], HIGH);
      }
      a = !a;

      val = Serial.read();
      if(val >= 0){
        clearLEDs();
        goto top;
      }
    }
  }
  
  //Random
  if(val == '1'){
    while(true){
      byte i = random(numLEDs);
      if(random(2) == 1)
        digitalWrite(leds[i], HIGH);
      else
        digitalWrite(leds[i], LOW);

      //delay from 10 to 150 inclusive, low numbers more likely
      int dlay =  random(0, 151) + random(0, 151) - 130;
      if(dlay < 10 || dlay > 150)
        dlay = random(10, 151);

      delay(dlay);

      val = Serial.read();
      if(val >= 0){
        clearLEDs();
        goto top;
      }
    }
  }

  //Binary Counter
  if(val == '2'){
    unsigned int count = 0;
    while(true){
      clearLEDs();
      unsigned int mask = 1;
      for(int i = 0; i < numLEDs; i++){
        if((count & mask) != 0)
          digitalWrite(leds[i], HIGH);
        mask = mask << 1;
      }
      count++;

      delay(200);

      val = Serial.read();
      if(val >= 0){
        clearLEDs();
        goto top;
      }
    }
  }

  //Bouncing
  if(val == '3'){
    unsigned int left = 0, right = numLEDs - 1;
    unsigned int middle = numLEDs / 2;
    boolean forwards = true;
    unsigned int lastSec = 0;
    while(true){
      unsigned long sec = millis() / 60;
      if(lastSec != sec)
        lastSec = sec;
      else
        continue;

      clearLEDs();

      digitalWrite(leds[left], HIGH);
      digitalWrite(leds[right], HIGH);

      if(forwards){
        left++;
        right--;
      }
      else{
        left--;
        right++;
      }

      if(right == middle)
        forwards = false;
      if(left == 0)
        forwards = true;

      val = Serial.read();
      if(val >= 0){
        clearLEDs();
        goto top;
      }
    }
  }
  
  //Scanner
  if(val == '4'){
    int t = 20;
    while(true){
      int i;
      for(i = 0; i < numLEDs; i++){
        digitalWrite(leds[i], HIGH);
        delay(t);
      }
      for(i = 0; i < numLEDs; i++){
        digitalWrite(leds[i], LOW);
        delay(t);
      }
      for(i = numLEDs - 1; i >= 0; i--){
        digitalWrite(leds[i], HIGH);
        delay(t);
      }
      for(i = numLEDs - 1; i >= 0; i--){
        digitalWrite(leds[i], LOW);
        delay(t);
      }
      val = Serial.read();
      if(val >= 0){
        clearLEDs();
        goto top;
      }
    }
  }

  //Knight Rider
  if(val == '5'){
    unsigned long time_started[numLEDs];
    unsigned long lastTime = 0;
    unsigned int i = 0;	 //front
    unsigned int j = 0; //back
    boolean forwards = true;
    boolean runI = true;
    const unsigned int next = 20;
    const unsigned int durr = 80;
    unsigned int agcd = gcd(next, durr);
    clearArray(time_started);

    while(true){
      unsigned long time = millis();
      if(time % agcd == 0){

        if(lastTime != time)
          lastTime = time;
        else
          continue;
        ///   
        if(forwards){ //forwards
          if(runI && time % next == 0){
            digitalWrite(leds[i], HIGH);
            time_started[i] = time;
            i++;
          }

          if(time - time_started[j] == durr){
            digitalWrite(leds[j], LOW);
            j++;
          }

          if(i == numLEDs)
            runI = false;
          if(j == numLEDs){
            digitalWrite(leds[j], LOW);
            clearArray(time_started);
            runI = true;
            i = 11;
            j = 11;
            forwards = false;
          }
        }
        else{ //backwards
          if(runI && time % next == 0){
            digitalWrite(leds[i], HIGH);
            time_started[i] = time;
            i--;
          }
          if(time - time_started[j] == durr){
            digitalWrite(leds[j], LOW);
            j--;
          }

          if(i == 65535) //overflow
            runI = false;
          if(j == 65535){
            digitalWrite(leds[0], LOW);
            clearArray(time_started);
            runI = true;
            i = 0;
            j = 0;
            forwards = true;
          }
        }
      }
      ////
      val = Serial.read();
      if(val >= 0){
        clearLEDs();
        goto top;
      }
    }
  }

  //ascii conversions
  if(val > 64 && val < 78)					        //Capital Letter
    digitalWrite(leds[val - 65], LOW);
  if(val > 96 && val < 110)					        //Lower Case Letter
    digitalWrite(leds[val - 97], HIGH);
  if(val == 'Z'){
    clearLEDs();
  }
}

