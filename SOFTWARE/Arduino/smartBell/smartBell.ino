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

IPAddress server(192,168,1,16); 
YunClient client;
TKButton button(I0);

String parametri =
  "{"
    "\"title\": \"Put the push notification title here\","
    "\"message\": \"Put here push notification body here\","
    "\"token\": \"fe5Q-nBjTo273aSdpRRsQg:APA91bFsXRUcPTTkBdP-09EWQ77v4nv43DZEbmaU2RScTLXcxUjN1eIXVoDk-9f-Swysg5vMBfDIDCS35jpy-UthzqhP9IIopnZUKepxmdKel_hD-pjIBtKAPxPWoGFbsBi40brbaR2W\""
  "}";

void setup()
{
  Bridge.begin();
  Serial.begin(9600);
  
  delay(2500);
  Serial.println("connecting...");
  if (client.connect(server, 8080))
  {
    Serial.println("connected");
    delay(2500);
  }
else
    {
    Serial.println("connection failed");
    }
}
void loop()
{

  while(!button.pressed()){}
  client.println("POST /notification/token HTTP/1.1");
  client.println("Host:901972c03a46.ngrok.io");
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
  /*
  if (!client.connected()) {
    Serial.println();
    Serial.println("disconnecting.");
    client.stop(); 
    //for(;;)
    //;
  }*/
 
}