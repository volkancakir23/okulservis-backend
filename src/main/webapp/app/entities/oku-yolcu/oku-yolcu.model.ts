import { BaseEntity } from './../../shared';

export class OkuYolcu implements BaseEntity {
    constructor(
        public id?: number,
        public bindiMi?: boolean,
        public ulastiMi?: boolean,
        public sefer?: BaseEntity,
        public ogrenci?: BaseEntity,
    ) {
        this.bindiMi = false;
        this.ulastiMi = false;
    }
}
