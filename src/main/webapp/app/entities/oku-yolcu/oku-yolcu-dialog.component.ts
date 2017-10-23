import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuYolcu } from './oku-yolcu.model';
import { OkuYolcuPopupService } from './oku-yolcu-popup.service';
import { OkuYolcuService } from './oku-yolcu.service';
import { OkuSefer, OkuSeferService } from '../oku-sefer';
import { OkuOgrenci, OkuOgrenciService } from '../oku-ogrenci';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oku-yolcu-dialog',
    templateUrl: './oku-yolcu-dialog.component.html'
})
export class OkuYolcuDialogComponent implements OnInit {

    okuYolcu: OkuYolcu;
    isSaving: boolean;

    okusefers: OkuSefer[];

    okuogrencis: OkuOgrenci[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuYolcuService: OkuYolcuService,
        private okuSeferService: OkuSeferService,
        private okuOgrenciService: OkuOgrenciService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.okuSeferService.query()
            .subscribe((res: ResponseWrapper) => { this.okusefers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.okuOgrenciService.query()
            .subscribe((res: ResponseWrapper) => { this.okuogrencis = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuYolcu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuYolcuService.update(this.okuYolcu));
        } else {
            this.subscribeToSaveResponse(
                this.okuYolcuService.create(this.okuYolcu));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuYolcu>) {
        result.subscribe((res: OkuYolcu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuYolcu) {
        this.eventManager.broadcast({ name: 'okuYolcuListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOkuSeferById(index: number, item: OkuSefer) {
        return item.id;
    }

    trackOkuOgrenciById(index: number, item: OkuOgrenci) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-oku-yolcu-popup',
    template: ''
})
export class OkuYolcuPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuYolcuPopupService: OkuYolcuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuYolcuPopupService
                    .open(OkuYolcuDialogComponent as Component, params['id']);
            } else {
                this.okuYolcuPopupService
                    .open(OkuYolcuDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
