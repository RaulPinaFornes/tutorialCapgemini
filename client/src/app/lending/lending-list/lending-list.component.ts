import { Component, OnInit } from '@angular/core';
import { Lending } from '../model/Lending';
import { Client } from 'src/app/clients/model/Client';
import { LendingService } from '../lending.service';
import { ClientsService } from 'src/app/clients/clients.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { MatTableDataSource } from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { LendingFilter } from '../model/LendingFilter';
import { GameService } from 'src/app/game/game.service';
import { Game } from 'src/app/game/model/Game';
import { LendingEditComponent } from '../lending-edit/lending-edit.component';
import { DialogRef } from '@angular/cdk/dialog';

@Component({
  selector: 'app-lending-list',
  templateUrl: './lending-list.component.html',
  styleUrls: ['./lending-list.component.scss']
})

export class LendingListComponent implements OnInit{

  lendings:Lending[];
  clients: Client[];
  games: Game[];
  filterTitle: Game = null;
  filterClient: Client = null;
  filterDate: string = null;

  dataSource = new MatTableDataSource<Lending>();
  displayedColumns: string[] = ['id', 'gameName', 'clientName', 'dateInit', 'dateEnd', 'action'];
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  constructor (
    private gameService: GameService,
    private lendingService: LendingService,
    private clientService: ClientsService,
    public dialog: MatDialog
  ) {  }

  ngOnInit(): void {
    this.clientService.getClients().subscribe(result => {
      this.clients = result;
    })
    this.gameService.getGames().subscribe(result => {
      this.games = result;
    })
    
    this.loadPage();
    
  }
  
  loadPage(event?: PageEvent): void {
        
    let pageAble: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [{
        property: 'id',
        direction: 'ASC'
      }]
    }

    if (event != null) {
      pageAble.pageSize = event.pageSize;
      pageAble.pageNumber = event.pageIndex;
    }
    
    let filters: LendingFilter = {
      clientId: this.filterClient != null ? this.filterClient.id : null,
      date: this.filterDate != null ? new Date(this.filterDate) : null,
      titleId: this.filterTitle != null ? this.filterTitle.id : null,
    }

    this.lendingService.getLendings(pageAble, filters).subscribe(result => {
      
      this.dataSource.data = result.content;
      this.pageNumber = result.pageable.pageNumber;
      this.pageSize = result.pageable.pageSize;
      this.totalElements = result.totalElements;
    }); 
  }

  getMyDate(filterDate: string): string {
    let date = new Date(filterDate)
    return `${date.getDate()}-${date.getMonth()+1}-${date.getFullYear()}`
  }
  onCleanFilters(): void {
    this.filterClient = null;
    this.filterDate = null;
    this.filterTitle = null;

    this.onSearch()
  }

  onSearch(): void {

    this.loadPage(null);
  }

  deleteLending(lending: Lending): void {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: {
        title: 'Eliminar Préstamo',
        description: 'Atencion si borra el préstamo se perderán todos sus datos. <br /> ¿Desea eliminarlo de todas formas?'
      }
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result){
        this.lendingService.deleteLending(lending.id).subscribe(result => {
          this.ngOnInit();
        })
      }
    })
    
  }
  newLending():void{
    const dialogRef = this.dialog.open(LendingEditComponent)

    dialogRef.afterClosed().subscribe(result => {
      this.loadPage()
    })
  }
}
