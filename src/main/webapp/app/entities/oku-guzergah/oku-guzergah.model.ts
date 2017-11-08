import { BaseEntity } from './../../shared';

export class OkuGuzergah implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public rota?: string,
        public harita?: string,
        public okul?: BaseEntity,
    ) {
    }
}
