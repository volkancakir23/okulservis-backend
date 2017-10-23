import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuGuzergah } from './oku-guzergah.model';
import { OkuGuzergahPopupService } from './oku-guzergah-popup.service';
import { OkuGuzergahService } from './oku-guzergah.service';
import { OkuOkul, OkuOkulService } from '../oku-okul';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oku-guzergah-dialog',
    templateUrl: './oku-guzergah-dialog.component.html'
})
export class OkuGuzergahDialogComponent implements OnInit {

    okuGuzergah: OkuGuzergah;
    isSaving: boolean;

    okuokuls: OkuOkul[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuGuzergahService: OkuGuzergahService,
        private okuOkulService: OkuOkulService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.okuOkulService.query()
            .subscribe((res: ResponseWrapper) => { this.okuokuls = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuGuzergah.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuGuzergahService.update(this.okuGuzergah));
        } else {
            this.subscribeToSaveResponse(
                this.okuGuzergahService.create(this.okuGuzergah));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuGuzergah>) {
        result.subscribe((res: OkuGuzergah) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuGuzergah) {
        this.eventManager.broadcast({ name: 'okuGuzergahListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-oku-guzergah-popup',
    template: ''
})
export class OkuGuzergahPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuGuzergahPopupService: OkuGuzergahPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuGuzergahPopupService
                    .open(OkuGuzergahDialogComponent as Component, params['id']);
            } else {
                this.okuGuzergahPopupService
                    .open(OkuGuzergahDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
