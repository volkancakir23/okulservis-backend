import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OkulservisSharedModule } from '../../shared';
import {
    OkuOgrenciService,
    OkuOgrenciPopupService,
    OkuOgrenciComponent,
    OkuOgrenciDetailComponent,
    OkuOgrenciDialogComponent,
    OkuOgrenciPopupComponent,
    OkuOgrenciDeletePopupComponent,
    OkuOgrenciDeleteDialogComponent,
    okuOgrenciRoute,
    okuOgrenciPopupRoute,
    OkuOgrenciResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...okuOgrenciRoute,
    ...okuOgrenciPopupRoute,
];

@NgModule({
    imports: [
        OkulservisSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OkuOgrenciComponent,
        OkuOgrenciDetailComponent,
        OkuOgrenciDialogComponent,
        OkuOgrenciDeleteDialogComponent,
        OkuOgrenciPopupComponent,
        OkuOgrenciDeletePopupComponent,
    ],
    entryComponents: [
        OkuOgrenciComponent,
        OkuOgrenciDialogComponent,
        OkuOgrenciPopupComponent,
        OkuOgrenciDeleteDialogComponent,
        OkuOgrenciDeletePopupComponent,
    ],
    providers: [
        OkuOgrenciService,
        OkuOgrenciPopupService,
        OkuOgrenciResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisOkuOgrenciModule {}
