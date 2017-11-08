import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { OkulservisOkuSehirModule } from './oku-sehir/oku-sehir.module';
import { OkulservisOkuOkulModule } from './oku-okul/oku-okul.module';
import { OkulservisOkuOgrenciModule } from './oku-ogrenci/oku-ogrenci.module';
import { OkulservisOkuYolcuModule } from './oku-yolcu/oku-yolcu.module';
import { OkulservisOkuGuzergahModule } from './oku-guzergah/oku-guzergah.module';
import { OkulservisOkuSoforModule } from './oku-sofor/oku-sofor.module';
import { OkulservisOkuAracModule } from './oku-arac/oku-arac.module';
import { OkulservisOkuSeferModule } from './oku-sefer/oku-sefer.module';
import { OkulservisOkuPersonelModule } from './oku-personel/oku-personel.module';
import { OkulservisOkuVeliModule } from './oku-veli/oku-veli.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        OkulservisOkuSehirModule,
        OkulservisOkuOkulModule,
        OkulservisOkuOgrenciModule,
        OkulservisOkuYolcuModule,
        OkulservisOkuGuzergahModule,
        OkulservisOkuSoforModule,
        OkulservisOkuAracModule,
        OkulservisOkuSeferModule,
        OkulservisOkuPersonelModule,
        OkulservisOkuVeliModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OkulservisEntityModule {}
