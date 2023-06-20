import { Component, Inject, OnInit } from '@angular/core';
import { Author } from 'src/app/author/model/Author';
import { Category } from 'src/app/category/model/Category';
import { Game } from '../model/Game';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { GameService } from '../game.service';
import { AuthorService } from 'src/app/author/author.service';
import { CategoryService } from 'src/app/category/category.service';

@Component({
  selector: 'app-game-edit',
  templateUrl: './game-edit.component.html',
  styleUrls: ['./game-edit.component.scss']
})
export class GameEditComponent implements OnInit {

  game: Game;
  authors: Author[];
  categories: Category[];

  constructor(
    public dialogRef: MatDialogRef<GameEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gameService: GameService,
    private categoryService: CategoryService,
    private authorService: AuthorService
  ) { }

  ngOnInit(): void {
    if (this.data.game != null) {
      this.game = Object.assign({}, this.data.game);
    }
    else {
      this.game = new Game();
    }

    this.categoryService.getCategories().subscribe(result => {
      this.categories = result;

      if (this.game.category != null) {
        let categoryFilter: Category[] = result.filter(category => category.id == this.data.game.category.id);
        if (categoryFilter != null) {
          this.game.category = categoryFilter[0];
      }
      }
    })

    this.authorService.getAllAuthors().subscribe(result => {
      this.authors = result;

      if (this.game.author != null) {
        let authorFilter: Author[] = result.filter(author => author.id == this.data.game.author.id);
        if (authorFilter != null) {
          this.game.author = authorFilter[0]
        }
      }
    })
  }

  onSave(): void {
    this.gameService.saveGame(this.game).subscribe(result => {
      this.dialogRef.close();
    })
  }

  onClose(): void {
    this.dialogRef.close();
  }
}
