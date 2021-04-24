#include <Wire.h>
#include <Adafruit_MLX90614.h>
Adafruit_MLX90614 mlx = Adafruit_MLX90614();

void setup() {
  Serial.begin(9600);
  mlx.begin();
}

void loop() {
  Serial.println(mlx.readAmbientTempF());
  Serial.println(mlx.readObjectTempF()+7);
  delay(1000);
}
