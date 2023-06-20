import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/category/model/Category';
import { Game } from '../model/Game';
import { GameService } from '../game.service';
import { CategoryService } from 'src/app/category/category.service';
import { MatDialog } from '@angular/material/dialog';
import { GameEditComponent } from '../game-edit/game-edit.component';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit{

  categories: Category[];
  games: Game[];
  filterCategory: Category;
  filterTitle: string;

  constructor (
    private gameService: GameService,
    private categoryService: CategoryService,
    public dialog: MatDialog,
  ) {  }

  ngOnInit(): void {
    this.gameService.getGames().subscribe(result => {
      this.games = result;
    });

    this.categoryService.getCategories().subscribe(result => {
      this.categories = result;
    });
  }

  onCleanFilter(): void {
    this.filterCategory = null;
    this.filterTitle = null;

    this.onSearch();
  }

  onSearch(): void {

    let title = this.filterTitle;
    let category = this.filterCategory != null ? this.filterCategory.id : null;

    this.gameService.getGames(title, category).subscribe(result => {
      this.games = result;
    })
  }

  createGame(): void {
    const dialogRef = this.dialog.open(GameEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    })
  }

  editGame(game: Game): void {
    const dialogref = this.dialog.open(GameEditComponent, {
      data: { game: game }
    });

    dialogref.afterClosed().subscribe(result => {
      this.onSearch();
    })
  }
}
