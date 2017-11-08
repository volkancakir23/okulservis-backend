import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OkulservisSharedModule } from '../../shared';
import {
    OkuAracService,
    OkuAracPopupService,
    OkuAracComponent,
    OkuAracDetailComponent,
    OkuAracDialogComponent,
    OkuAracPopupComponent,
    OkuAracDeletePopupComponent,
    OkuAracDeleteDialogComponent,
    okuAracRoute,
    okuAracPopupRoute,
    OkuAracResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...okuAracRoute,
    ...okuAracPopupRoute,
];

@NgModule({
    imports: [
        OkulservisSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OkuAracComponent,
        OkuAracDetailComponent,
        OkuAracDialogComponent,
        OkuAracDeleteDialogComponent,
        OkuAracPopupComponent,
        OkuAracDeletePopupComponent,
    ],
    entryComponents: [
        OkuAracComponent,
        OkuAracDialogComponent,
        OkuAracPopupComponent,
        OkuAracDeleteDialogComponent,
        OkuAracDeletePopupComponent,
    ],
    providers: [
        OkuAracService,
        OkuAracPopupService,
        OkuAracResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisOkuAracModule {}
