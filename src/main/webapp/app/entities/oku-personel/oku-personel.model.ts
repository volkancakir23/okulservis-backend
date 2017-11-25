import { BaseEntity, User } from './../../shared';

export class OkuPersonel implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public isim?: string,
        public tel?: string,
        public okuOkul?: BaseEntity,
        public user?: User,
    ) {
    }
}
