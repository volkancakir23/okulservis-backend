import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuOkul } from './oku-okul.model';
import { OkuOkulPopupService } from './oku-okul-popup.service';
import { OkuOkulService } from './oku-okul.service';
import { OkuSehir, OkuSehirService } from '../oku-sehir';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oku-okul-dialog',
    templateUrl: './oku-okul-dialog.component.html'
})
export class OkuOkulDialogComponent implements OnInit {

    okuOkul: OkuOkul;
    isSaving: boolean;

    okusehirs: OkuSehir[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuOkulService: OkuOkulService,
        private okuSehirService: OkuSehirService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.okuSehirService.query()
            .subscribe((res: ResponseWrapper) => { this.okusehirs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuOkul.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuOkulService.update(this.okuOkul));
        } else {
            this.subscribeToSaveResponse(
                this.okuOkulService.create(this.okuOkul));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuOkul>) {
        result.subscribe((res: OkuOkul) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuOkul) {
        this.eventManager.broadcast({ name: 'okuOkulListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOkuSehirById(index: number, item: OkuSehir) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-oku-okul-popup',
    template: ''
})
export class OkuOkulPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuOkulPopupService: OkuOkulPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuOkulPopupService
                    .open(OkuOkulDialogComponent as Component, params['id']);
            } else {
                this.okuOkulPopupService
                    .open(OkuOkulDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
