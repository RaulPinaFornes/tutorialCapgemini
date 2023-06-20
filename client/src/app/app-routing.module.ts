import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryListComponent } from './category/category-list/category-list.component';
import { AuthorListComponent } from './author/author-list/author-list.component';
import { GameListComponent } from './game/game-list/game-list.component';
import { ClientsListComponent } from './clients/clients-list/clients-list.component';
import { LendingListComponent } from './lending/lending-list/lending-list.component';

const routes: Routes = [
  { path: 'categories', component: CategoryListComponent },
  { path: 'authors', component: AuthorListComponent },
  { path: 'games', component: GameListComponent },
  { path: 'clients', component: ClientsListComponent },
  { path: 'lendings', component: LendingListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
