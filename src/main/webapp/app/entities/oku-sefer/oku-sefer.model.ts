import { BaseEntity } from './../../shared';

export const enum OkuServis {
    'SABAH',
    'AKSAM',
    'DIGER'
}

export class OkuSefer implements BaseEntity {
    constructor(
        public id?: number,
        public tarih?: any,
        public servis?: OkuServis,
        public yapildiMi?: boolean,
        public guzergah?: BaseEntity,
        public sofor?: BaseEntity,
        public arac?: BaseEntity,
    ) {
        this.yapildiMi = false;
    }
}
