import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OkulservisSharedModule } from '../../shared';
import {
    OkuSeferService,
    OkuSeferPopupService,
    OkuSeferComponent,
    OkuSeferDetailComponent,
    OkuSeferDialogComponent,
    OkuSeferPopupComponent,
    OkuSeferDeletePopupComponent,
    OkuSeferDeleteDialogComponent,
    okuSeferRoute,
    okuSeferPopupRoute,
    OkuSeferResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...okuSeferRoute,
    ...okuSeferPopupRoute,
];

@NgModule({
    imports: [
        OkulservisSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OkuSeferComponent,
        OkuSeferDetailComponent,
        OkuSeferDialogComponent,
        OkuSeferDeleteDialogComponent,
        OkuSeferPopupComponent,
        OkuSeferDeletePopupComponent,
    ],
    entryComponents: [
        OkuSeferComponent,
        OkuSeferDialogComponent,
        OkuSeferPopupComponent,
        OkuSeferDeleteDialogComponent,
        OkuSeferDeletePopupComponent,
    ],
    providers: [
        OkuSeferService,
        OkuSeferPopupService,
        OkuSeferResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisOkuSeferModule {}
