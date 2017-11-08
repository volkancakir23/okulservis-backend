import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OkulservisSharedModule } from '../../shared';
import { OkulservisAdminModule } from '../../admin/admin.module';
import {
    OkuPersonelService,
    OkuPersonelPopupService,
    OkuPersonelComponent,
    OkuPersonelDetailComponent,
    OkuPersonelDialogComponent,
    OkuPersonelPopupComponent,
    OkuPersonelDeletePopupComponent,
    OkuPersonelDeleteDialogComponent,
    okuPersonelRoute,
    okuPersonelPopupRoute,
    OkuPersonelResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...okuPersonelRoute,
    ...okuPersonelPopupRoute,
];

@NgModule({
    imports: [
        OkulservisSharedModule,
        OkulservisAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OkuPersonelComponent,
        OkuPersonelDetailComponent,
        OkuPersonelDialogComponent,
        OkuPersonelDeleteDialogComponent,
        OkuPersonelPopupComponent,
        OkuPersonelDeletePopupComponent,
    ],
    entryComponents: [
        OkuPersonelComponent,
        OkuPersonelDialogComponent,
        OkuPersonelPopupComponent,
        OkuPersonelDeleteDialogComponent,
        OkuPersonelDeletePopupComponent,
    ],
    providers: [
        OkuPersonelService,
        OkuPersonelPopupService,
        OkuPersonelResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisOkuPersonelModule {}
