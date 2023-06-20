import { Component, OnInit } from '@angular/core';
import { Client } from '../model/Client';
import { ClientsService } from '../clients.service';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ClientsEditComponent } from '../clients-edit/clients-edit.component';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';

@Component({
  selector: 'app-clients-list',
  templateUrl: './clients-list.component.html',
  styleUrls: ['./clients-list.component.scss']
})
export class ClientsListComponent implements OnInit{

  dataSource = new MatTableDataSource<Client>();
  displayedColumns: string[] = ['id','name','action'];

  


  constructor (
    private clientsService: ClientsService,
    public dialog: MatDialog,
  ) {  }

  ngOnInit(): void {
    this.clientsService.getClients().subscribe(result => {
      this.dataSource.data = result;
    })
  }

  editClient(client:Client): void {
    const dialogRef = this.dialog.open(ClientsEditComponent,{
      data: { client }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    })
  }
  createClient(): void {
    const dialogRef = this.dialog.open(ClientsEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    })
  }

  deleteClient(client:Client): void {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: {
        title: 'Eliminar cliente',
        description: 'Atencion si borra el cliente se perderán todos sus datos. <br /> ¿Desea eliminarlo de todas formas?'
      }
    });
    dialogRef.afterClosed().subscribe( result => {
      if( result )
        this.clientsService.deleteClient(client.id).subscribe( result => {
          this.ngOnInit();
        })
    })
  }

}
