import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OkulservisSharedModule } from '../../shared';
import { OkulservisAdminModule } from '../../admin/admin.module';
import {
    OkuVeliService,
    OkuVeliPopupService,
    OkuVeliComponent,
    OkuVeliDetailComponent,
    OkuVeliDialogComponent,
    OkuVeliPopupComponent,
    OkuVeliDeletePopupComponent,
    OkuVeliDeleteDialogComponent,
    okuVeliRoute,
    okuVeliPopupRoute,
    OkuVeliResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...okuVeliRoute,
    ...okuVeliPopupRoute,
];

@NgModule({
    imports: [
        OkulservisSharedModule,
        OkulservisAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OkuVeliComponent,
        OkuVeliDetailComponent,
        OkuVeliDialogComponent,
        OkuVeliDeleteDialogComponent,
        OkuVeliPopupComponent,
        OkuVeliDeletePopupComponent,
    ],
    entryComponents: [
        OkuVeliComponent,
        OkuVeliDialogComponent,
        OkuVeliPopupComponent,
        OkuVeliDeleteDialogComponent,
        OkuVeliDeletePopupComponent,
    ],
    providers: [
        OkuVeliService,
        OkuVeliPopupService,
        OkuVeliResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisOkuVeliModule {}
