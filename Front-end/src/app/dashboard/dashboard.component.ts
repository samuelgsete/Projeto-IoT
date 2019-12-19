import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public imagens: Array<any> = [];
  public urlBase: string = "http://192.168.0.102:8080/cam-iot/api/imagem/";

  constructor(private http: HttpClient, private toastr: ToastrService) {
    this.load();
  }

  load() {
    this.http.get<any[]>(this.urlBase).subscribe( resp => {
      this.toastr.success('Dados atualizados com sucesso', 'Feito', {progressBar: true});
      this.imagens = resp;
      console.log(this.imagens);
    });
  }

  remover(imagem: any) {
    Swal.fire({
      title: 'Tem certeza que deseja remover?',
      text: 'Você não poderá desfazer essa operação',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sim',
      cancelButtonText: 'Não'
    }).then((result) => {
      if (result.value) {
        this.http.delete<any>(this.urlBase + imagem.id).subscribe(r => {   
          this.toastr.success('Removido com sucesso!', 'Feito', {progressBar: true});
          this.load();
        })
        , e => {
          this.toastr.error('Não foi possível remover', 'ERRO', {progressBar: true});
          }; 
        } 
      });
  }

  ngOnInit() {
  }

}
