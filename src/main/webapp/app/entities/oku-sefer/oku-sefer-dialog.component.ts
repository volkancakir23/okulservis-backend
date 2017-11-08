import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuSefer } from './oku-sefer.model';
import { OkuSeferPopupService } from './oku-sefer-popup.service';
import { OkuSeferService } from './oku-sefer.service';
import { OkuGuzergah, OkuGuzergahService } from '../oku-guzergah';
import { OkuSofor, OkuSoforService } from '../oku-sofor';
import { OkuArac, OkuAracService } from '../oku-arac';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oku-sefer-dialog',
    templateUrl: './oku-sefer-dialog.component.html'
})
export class OkuSeferDialogComponent implements OnInit {

    okuSefer: OkuSefer;
    isSaving: boolean;

    okuguzergahs: OkuGuzergah[];

    okusofors: OkuSofor[];

    okuaracs: OkuArac[];
    tarihDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuSeferService: OkuSeferService,
        private okuGuzergahService: OkuGuzergahService,
        private okuSoforService: OkuSoforService,
        private okuAracService: OkuAracService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.okuGuzergahService.query()
            .subscribe((res: ResponseWrapper) => { this.okuguzergahs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.okuSoforService.query()
            .subscribe((res: ResponseWrapper) => { this.okusofors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.okuAracService.query()
            .subscribe((res: ResponseWrapper) => { this.okuaracs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuSefer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuSeferService.update(this.okuSefer));
        } else {
            this.subscribeToSaveResponse(
                this.okuSeferService.create(this.okuSefer));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuSefer>) {
        result.subscribe((res: OkuSefer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuSefer) {
        this.eventManager.broadcast({ name: 'okuSeferListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOkuGuzergahById(index: number, item: OkuGuzergah) {
        return item.id;
    }

    trackOkuSoforById(index: number, item: OkuSofor) {
        return item.id;
    }

    trackOkuAracById(index: number, item: OkuArac) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-oku-sefer-popup',
    template: ''
})
export class OkuSeferPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuSeferPopupService: OkuSeferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuSeferPopupService
                    .open(OkuSeferDialogComponent as Component, params['id']);
            } else {
                this.okuSeferPopupService
                    .open(OkuSeferDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
