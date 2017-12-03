import { BaseEntity, User } from './../../shared';

export class OkuVeli implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public isim?: string,
        public tel?: string,
        public ogrenci?: BaseEntity,
        public user?: User,
    ) {
    }
}
