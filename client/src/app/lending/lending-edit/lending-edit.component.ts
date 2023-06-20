import { Component, OnInit } from '@angular/core';
import { ClientsService } from 'src/app/clients/clients.service';
import { Client } from 'src/app/clients/model/Client';
import { GameService } from 'src/app/game/game.service';
import { Game } from 'src/app/game/model/Game';
import { LendingService } from '../lending.service';
import { Lending } from '../model/Lending';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-lending-edit',
  templateUrl: './lending-edit.component.html',
  styleUrls: ['./lending-edit.component.scss']
})
export class LendingEditComponent implements OnInit{

  constructor (
    public dialogRef: MatDialogRef<LendingEditComponent>,
    private lendingService: LendingService,
    private clientService: ClientsService,
    private gameService: GameService
  ) {   }
  displayError: boolean = false;
  errorMensage: String ='';
  clients: Client[];
  games: Game[];
  filterTitle: Game;
  filterClient: Client;
  filterDateFirst: string;
  filterDateLast: string;
  ngOnInit(): void {
    this.clientService.getClients().subscribe(result => {
      this.clients = result;
    })
    this.gameService.getGames().subscribe(result => {
      this.games = result;
    })

    
  }

  onClose():void {
    this.dialogRef.close();
  }
  onSave(): void {
    if(this.checkDates()){
      let lending: Lending = {
        id: null,
        game: this.filterTitle,
        client: this.filterClient,
        dateinit: new Date(this.filterDateFirst),
        dateend: new Date(this.filterDateLast)
      }
      this.displayError = false;
      this.lendingService.saveLending(lending).subscribe(result => {
        if (result != null){
          this.errorMensage = result.error ;
          this.displayError = true;
        }else {
          this.displayError = false;
          this.dialogRef.close();
        }
      })
    }else {
      this.errorMensage = 'Máximo tiempo son 14 días';
      this.displayError = true;
    }
      
  }
  checkDates():boolean {
    let dateinit = new Date(this.filterDateFirst)
    let dateend = new Date(this.filterDateLast)
    let dif = dateend.getTime() - dateinit.getTime();
    console.log(this.filterDateFirst)
    console.log(this.filterDateLast)
    let MIL_TO_DAYS = 1000*60*60*24;
    let MAX_DAYS = 14;
    return ( (dif/MIL_TO_DAYS) + 1 <= MAX_DAYS && dif/MIL_TO_DAYS + 1 > 0) ? true : false;
  }

}
