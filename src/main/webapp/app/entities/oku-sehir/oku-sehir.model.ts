import { BaseEntity } from './../../shared';

export class OkuSehir implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public isim?: string,
    ) {
    }
}
