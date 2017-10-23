import { BaseEntity } from './../../shared';

export class OkuOkul implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public isim?: string,
        public mudurIsim?: string,
        public mudurTel?: string,
        public sehir?: BaseEntity,
    ) {
    }
}
