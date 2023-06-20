import { Lending } from "./Lending";


export const MOCK_LENDING: Lending[] = [
    {
        id: 1,
        game: { id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } },
        client: { id:1, name: 'Cliente 1' },
        dateinit: '2023-06-15T22:00:00.000Z',
        dateend: '2023-06-18T22:00:00.000Z'
    },
    {
        id: 1,
        game: { id: 3, title: 'Juego 3', age: 4, category: { id: 1, name: 'Categoría 1' }, author: { id: 3, name: 'Autor 3', nationality: 'Nacionalidad 3' } },
        client: { id:1, name: 'Cliente 1' },
        dateinit: '2023-06-15T22:00:00.000Z',
        dateend: '2023-06-18T22:00:00.000Z'
    },
    {
        id: 1,
        game: { id: 2, title: 'Juego 2', age: 8, category: { id: 1, name: 'Categoría 1' }, author: { id: 2, name: 'Autor 2', nationality: 'Nacionalidad 2' } },
        client: { id:1, name: 'Cliente 1' },
        dateinit: '2023-06-15T22:00:00.000Z',
        dateend: '2023-06-18T22:00:00.000Z'
    }
]

