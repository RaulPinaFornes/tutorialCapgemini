import { Lending } from "src/app/lending/model/Lending";
import { Pageable } from "./Pageable";
import { SortPage } from "./SortPage";

export class LendingPage {
    content: Lending[];
    pageable: Pageable;
    totalElements: number;
}