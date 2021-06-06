#include <PubSubClient.h>
#include <YunClient.h>
#include <TinkerKit.h>

#define MQTT_PORT 1883

IPAddress server(10,1,217,95); 
YunClient yunClient;
TKButton button(I0);

PubSubClient client(yunClient);

const char* brokerUser = "mqtt-user";
const char* brokerPass = "12345678";

void setup()
{
  Bridge.begin();
  Serial.begin(9600);
  
  delay(2500);
  Serial.println("connecting...");

  client.setServer(server, MQTT_PORT);
}
void loop()
{
  while(!button.pressed()){}
  while(!client.connect("arduinoClient", brokerUser, brokerPass)){}
  client.publish("/smartBell","doorbell");
}
