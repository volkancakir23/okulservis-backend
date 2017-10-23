import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OkulservisSharedModule } from '../../shared';
import {
    OkuGuzergahService,
    OkuGuzergahPopupService,
    OkuGuzergahComponent,
    OkuGuzergahDetailComponent,
    OkuGuzergahDialogComponent,
    OkuGuzergahPopupComponent,
    OkuGuzergahDeletePopupComponent,
    OkuGuzergahDeleteDialogComponent,
    okuGuzergahRoute,
    okuGuzergahPopupRoute,
    OkuGuzergahResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...okuGuzergahRoute,
    ...okuGuzergahPopupRoute,
];

@NgModule({
    imports: [
        OkulservisSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OkuGuzergahComponent,
        OkuGuzergahDetailComponent,
        OkuGuzergahDialogComponent,
        OkuGuzergahDeleteDialogComponent,
        OkuGuzergahPopupComponent,
        OkuGuzergahDeletePopupComponent,
    ],
    entryComponents: [
        OkuGuzergahComponent,
        OkuGuzergahDialogComponent,
        OkuGuzergahPopupComponent,
        OkuGuzergahDeleteDialogComponent,
        OkuGuzergahDeletePopupComponent,
    ],
    providers: [
        OkuGuzergahService,
        OkuGuzergahPopupService,
        OkuGuzergahResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisOkuGuzergahModule {}
