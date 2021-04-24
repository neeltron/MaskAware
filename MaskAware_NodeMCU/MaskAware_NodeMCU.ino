#include <Wire.h>
#include <Adafruit_MLX90614.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "SSID";
const char* password = "PASS";
int sensorValue;
Adafruit_MLX90614 mlx = Adafruit_MLX90614();

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
  Serial.println(mlx.readObjectTempF());
  sensorValue = analogRead(0);
  Serial.println(sensorValue, DEC);
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin("http://maskaware.azurewebsites.net/entrypoint?username=neeltronzeta&air="+String(sensorValue)+"&ir="+String(mlx.readObjectTempF()));
    int httpCode = http.GET();
    if (httpCode > 0) {
      String payload = http.getString();
      Serial.println(payload);
    }
    http.end();
  }
  delay(1000);
}
