import { BaseEntity } from './../../shared';

export class OkuArac implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public plaka?: string,
        public marka?: string,
        public renk?: string,
    ) {
    }
}
