import { BaseEntity } from './../../shared';

export class OkuOgrenci implements BaseEntity {
    constructor(
        public id?: number,
        public no?: string,
        public isim?: string,
        public tc?: string,
        public aileIsim?: string,
        public aileTel?: string,
        public okul?: BaseEntity,
        public guzergah?: BaseEntity,
    ) {
    }
}
