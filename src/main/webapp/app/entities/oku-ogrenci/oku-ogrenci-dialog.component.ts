import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuOgrenci } from './oku-ogrenci.model';
import { OkuOgrenciPopupService } from './oku-ogrenci-popup.service';
import { OkuOgrenciService } from './oku-ogrenci.service';
import { OkuOkul, OkuOkulService } from '../oku-okul';
import { OkuGuzergah, OkuGuzergahService } from '../oku-guzergah';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oku-ogrenci-dialog',
    templateUrl: './oku-ogrenci-dialog.component.html'
})
export class OkuOgrenciDialogComponent implements OnInit {

    okuOgrenci: OkuOgrenci;
    isSaving: boolean;

    okuokuls: OkuOkul[];

    okuguzergahs: OkuGuzergah[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuOgrenciService: OkuOgrenciService,
        private okuOkulService: OkuOkulService,
        private okuGuzergahService: OkuGuzergahService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.okuOkulService.query()
            .subscribe((res: ResponseWrapper) => { this.okuokuls = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.okuGuzergahService.query()
            .subscribe((res: ResponseWrapper) => { this.okuguzergahs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuOgrenci.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuOgrenciService.update(this.okuOgrenci));
        } else {
            this.subscribeToSaveResponse(
                this.okuOgrenciService.create(this.okuOgrenci));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuOgrenci>) {
        result.subscribe((res: OkuOgrenci) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuOgrenci) {
        this.eventManager.broadcast({ name: 'okuOgrenciListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOkuOkulById(index: number, item: OkuOkul) {
        return item.id;
    }

    trackOkuGuzergahById(index: number, item: OkuGuzergah) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-oku-ogrenci-popup',
    template: ''
})
export class OkuOgrenciPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuOgrenciPopupService: OkuOgrenciPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuOgrenciPopupService
                    .open(OkuOgrenciDialogComponent as Component, params['id']);
            } else {
                this.okuOgrenciPopupService
                    .open(OkuOgrenciDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
