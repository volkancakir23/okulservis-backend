import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OkulservisSharedModule } from '../../shared';
import {
    OkuSehirService,
    OkuSehirPopupService,
    OkuSehirComponent,
    OkuSehirDetailComponent,
    OkuSehirDialogComponent,
    OkuSehirPopupComponent,
    OkuSehirDeletePopupComponent,
    OkuSehirDeleteDialogComponent,
    okuSehirRoute,
    okuSehirPopupRoute,
    OkuSehirResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...okuSehirRoute,
    ...okuSehirPopupRoute,
];

@NgModule({
    imports: [
        OkulservisSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OkuSehirComponent,
        OkuSehirDetailComponent,
        OkuSehirDialogComponent,
        OkuSehirDeleteDialogComponent,
        OkuSehirPopupComponent,
        OkuSehirDeletePopupComponent,
    ],
    entryComponents: [
        OkuSehirComponent,
        OkuSehirDialogComponent,
        OkuSehirPopupComponent,
        OkuSehirDeleteDialogComponent,
        OkuSehirDeletePopupComponent,
    ],
    providers: [
        OkuSehirService,
        OkuSehirPopupService,
        OkuSehirResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisOkuSehirModule {}
