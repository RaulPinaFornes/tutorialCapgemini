import { Component, Inject, OnInit } from '@angular/core';
import { Client } from '../model/Client';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientsService } from '../clients.service';

@Component({
  selector: 'app-clients-edit',
  templateUrl: './clients-edit.component.html',
  styleUrls: ['./clients-edit.component.scss']
})
export class ClientsEditComponent implements OnInit{
  client:Client;
  displayErrorName: boolean = false;

  constructor(
    @Inject( MAT_DIALOG_DATA) public data: any,
    private clientsService: ClientsService,
    public dialogRef: MatDialogRef<ClientsEditComponent>
  ) {  }

  ngOnInit(): void{
    if( this.data.client != null ){
      this.client = Object.assign({}, this.data.client);
    }
    else {
      this.client = new Client();
    }
  }
  onClose():void {
    this.dialogRef.close();
  }
  onSave():void {
    this.displayErrorName = false;
    this.clientsService.saveClient(this.client).subscribe( result => {
      if(result){
        this.displayErrorName = false;
        this.dialogRef.close();
      }
      else{
        this.displayErrorName = true;
      }
        
    })
  }
}
