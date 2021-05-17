#include <PubSubClient.h>

#include <Bridge.h>
#include <Console.h>
#include <FileIO.h>
#include <HttpClient.h>
#include <Mailbox.h>
#include <Process.h>
#include <YunClient.h>
#include <YunServer.h>
#include <TinkerKit.h>
#include <SPI.h>

//IPAddress server(192,168,137,1);
IPAddress server(10,1,217,95); 
YunClient yunClient;
TKButton button(I0);

PubSubClient client(yunClient);

const char* brokerUser = "mqtt-user";
const char* brokerPass = "12345678";


void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i=0;i<length;i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("arduinoClient", brokerUser, brokerPass)) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("/smartBell","hello world");
      // ... and resubscribe
      client.subscribe("inTopic");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

String parametri =
  "{"
    "\"title\": \"Someone has rung your doorbell\","
    "\"message\": \"Go check it out\","
    "\"token\": \"fe5Q-nBjTo273aSdpRRsQg:APA91bFsXRUcPTTkBdP-09EWQ77v4nv43DZEbmaU2RScTLXcxUjN1eIXVoDk-9f-Swysg5vMBfDIDCS35jpy-UthzqhP9IIopnZUKepxmdKel_hD-pjIBtKAPxPWoGFbsBi40brbaR2W\""
  "}";

void setup()
{
  Bridge.begin();
  Serial.begin(9600);
  
  delay(2500);
  Serial.println("connecting...");
  /*
  if (client.connect(server, 8080))
  {
    Serial.println("connected");
    delay(2500);
  }
else
    {
    Serial.println("connection failed");
    }

  */

  client.setServer(server, 1883);
  client.setCallback(callback);
}
void loop()
{
  while(!button.pressed()){}
  client.connect("arduinoClient", brokerUser, brokerPass);
  client.publish("/smartBell","hello world");
  /*reconnect();*/
  /*if (!client.connected()) {
    reconnect();
  }
  client.loop();*/

/*
  while(!button.pressed()){}
  client.connect(server, 8080);
  client.println("POST /notification/token HTTP/1.1");
  client.println("Host: faefa8a8e7a0.ngrok.io");
  client.println("Content-Type: application/json");
  client.print("Content-length: ");
  client.println(parametri.length());
  Serial.println(parametri.length());
  Serial.println(parametri);
  client.println();
  client.println(parametri);     
   
  
  if (client.available()) {
    char c = client.read();
    Serial.print(c);
  }
  */

  
  /*
  if (!client.connected()) {
    Serial.println();
    Serial.println("disconnecting.");
    client.stop(); 
    //for(;;)
    //;
  }*/
 
}
