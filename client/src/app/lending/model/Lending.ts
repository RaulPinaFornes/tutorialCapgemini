import { Client } from "src/app/clients/model/Client";
import { Game } from "src/app/game/model/Game";


export class Lending {
    id: number;
    game: Game;
    client: Client;
    dateinit: string;
    dateend: string;
}