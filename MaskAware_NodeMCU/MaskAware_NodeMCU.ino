#include <Wire.h>
#include <Adafruit_MLX90614.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "Trikuldham";
const char* password = "jaihanuman1981";
int sensorValue;
Adafruit_MLX90614 mlx = Adafruit_MLX90614();

unsigned long start, finished, elapsed;

void setup() {
  Serial.begin(9600);
  mlx.begin();
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {

    delay(1000);
    Serial.print("Connecting..");

  }
}

void loop() {
  start=millis();
  Serial.println(mlx.readObjectTempF());
  sensorValue = 1200000 - start;
  int maskH = ((12000000 - start) / 1200000)*100;
  String s = String(maskH);
  Serial.println(s);
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin("http://maskaware.azurewebsites.net/entrypoint?username=neeltron&air="+s+"&ir="+String(mlx.readObjectTempF()));
    int httpCode = http.GET();
    if (httpCode > 0) {
      String payload = http.getString();
      Serial.println(payload);
    }
    http.end();
  }
  delay(10000);
  Serial.println(start);
}
