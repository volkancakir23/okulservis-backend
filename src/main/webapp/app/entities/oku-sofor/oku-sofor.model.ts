import { BaseEntity, User } from './../../shared';

export class OkuSofor implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public isim?: string,
        public tel?: string,
        public user?: User,
    ) {
    }
}
