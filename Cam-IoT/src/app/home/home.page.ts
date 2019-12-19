import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { MQTTService } from 'ionic-mqtt';
import * as moment from 'moment';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  public photo: string = '';
  public detectando: boolean = false;
  public comando: string = '';
  public led: string = 'OFF'
  public urlBase: string = "http://192.168.0.102:8080/cam-iot/api/imagem";
  public now = moment().format('YYYY-MM-DD');

  private _mqttClient: any;

  private MQTT_CONFIG: {
    host: string,
    port: number,
    clientId: string,
  } = {
    host: "broker.mqttdashboard.com",
    port: 8000,
    clientId: "asdfasdfasdfasdfasdfasdfasdf"
  };

  private TOPIC: string[] = ["detectando_movimentacao/state"];

  constructor(private http: HttpClient, private camera: Camera, private mqttService: MQTTService) {
    this._mqttClient = this.mqttService.loadingMqtt(this._onConnectionLost, this._onMessageArrived.bind(this), this.TOPIC, this.MQTT_CONFIG);
  }

  private _onConnectionLost(responseObject) {
    console.log('_onConnectionLost', responseObject);
  }

  private _onMessageArrived(message) {
    this.comando = message.payloadString;
    this.detectando = true;
    this._takePhoto();
  }

  public ligarDesligar(){
    if(this.led === "ON") {
      this.led = "OFF";
      this.mqttService.publishMessage("acender-led/set", "OFF");
    }
    else {
      this.led = "ON";
      this.mqttService.publishMessage("acender-led/set", "ON");
    }
  }

  private _takePhoto() {
    this.photo = '';
    const options: CameraOptions = {
      quality: 10,
      destinationType: this.camera.DestinationType.DATA_URL,
      encodingType: this.camera.EncodingType.PNG,
      mediaType: this.camera.MediaType.PICTURE,
      targetWidth: 480,
      targetHeight: 640 
    }
    this.camera.getPicture(options).then((imageURI) => {
      this.photo  = 'data:image/png;base64,' + imageURI;
      this.detectando = false;
      this.salvarImagem();
     }, (err) => {
        console.log('Não foi possivel capturar');
        console.log(err);
        this.detectando = false;
     });
  }

  salvarImagem() {
    let imagem: {
      data: string,
      base64: string;
    } = {
      data: this.now,
      base64: this.photo
    }
    this.http.post(this.urlBase, imagem).subscribe( resp => {
      console.log("Salvo com sucesso na api");
    });
  }
}
