#include <ESP8266WiFi.h> 
#include <PubSubClient.h>
#include <string.h>
#define BUTTON 0
#define ID_MQTT  "lens_3UnsltXwhIUIUxc6ETENixw5NEA"

// MQTT CONFIG
const char* mqttServer = "broker.mqttdashboard.com";
const int mqttPort = 1883;
WiFiClient espClient;
PubSubClient client(espClient);

// TÓPICOS 
const char* MQTT_TOPIC_ACENDER_LED = "acender-led/set";
const char* MQTT_STATE_DETECTANDO_MOVIMENTACAO = "detectando_movimentacao/state";

// WIFI CONFIG
const char* ssid = "SamuelGSETE"; 
const char* password = "geracaosete"; 
int count = 0;
 
void setup() {
  // INICIALIZA A SERIAL
  Serial.begin(115200);
  
  // SETA O BUTTON FLASH COMO ENTRADA
  pinMode(BUTTON, INPUT);

  // CONEXÃO WIFI
  conectarNoWiFi();
  // CONEXÃO COM O SERVIDOR MQTT
  mqttConnect();
}

void loop() {
  client.loop();
  if(detectarMovimento()) {
    client.publish(MQTT_STATE_DETECTANDO_MOVIMENTACAO, "MOVIMENTACAO_DETECTADA");
    Serial.println("Movimentação detectada"); 
    delay(1000);
  }
}

void conectarNoWiFi() {
  Serial.println(""); 
  Serial.print("Conectando a ");
  Serial.print(ssid); 
  WiFi.begin(ssid, password); 
   
  while (WiFi.status() != WL_CONNECTED) { 
    delay(500); 
    Serial.print("."); 
  }
  Serial.println(""); 
  Serial.print("Conectado a rede sem fio "); 
  Serial.println(ssid); 
}

void mqttConnect() {
  client.setServer(mqttServer, mqttPort);
  client.setCallback(callback);
  while(!client.connected()){
    Serial.println("Conectando ao brocker MQTT...");
    if(client.connect(ID_MQTT)){
      Serial.println("Conectado com sucesso ao broker MQTT!");
    } 
    else{
      Serial.println("Falha ao reconectar no broker.");
      Serial.println("Havera nova tentatica de conexao em 2s");
      delay(2000);
    }
  }
  client.subscribe(MQTT_TOPIC_ACENDER_LED);
}

boolean detectarMovimento(){
  int sinal = digitalRead(BUTTON) == HIGH;
  if(sinal == HIGH) {
    return false;
  }
  else {
    Serial.println(++count);
    return true;
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.println(topic);
  char comando[length];
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    comando[i] = (char)payload[i];
  }

  if(strcmp(MQTT_TOPIC_ACENDER_LED, topic) == 0){
    Serial.println("Tópico ascender led");
    if(strcmp(comando, "ON") == 0) {
      Serial.println("ascendendo LED");
      ascenderLED(false);
    }
    if(strcmp(comando, "OFF") == 0) { 
      Serial.println("apagando LED");
      ascenderLED(true);
    }
  }
}

void ascenderLED(boolean b) {
  pinMode(LED_BUILTIN, OUTPUT);
  if(b){
    digitalWrite(LED_BUILTIN, HIGH);
  }
  else {
     digitalWrite(LED_BUILTIN, LOW);
  }
}
