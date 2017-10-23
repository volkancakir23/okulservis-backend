import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuSofor } from './oku-sofor.model';
import { OkuSoforPopupService } from './oku-sofor-popup.service';
import { OkuSoforService } from './oku-sofor.service';

@Component({
    selector: 'jhi-oku-sofor-dialog',
    templateUrl: './oku-sofor-dialog.component.html'
})
export class OkuSoforDialogComponent implements OnInit {

    okuSofor: OkuSofor;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuSoforService: OkuSoforService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuSofor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuSoforService.update(this.okuSofor));
        } else {
            this.subscribeToSaveResponse(
                this.okuSoforService.create(this.okuSofor));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuSofor>) {
        result.subscribe((res: OkuSofor) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuSofor) {
        this.eventManager.broadcast({ name: 'okuSoforListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-oku-sofor-popup',
    template: ''
})
export class OkuSoforPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuSoforPopupService: OkuSoforPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuSoforPopupService
                    .open(OkuSoforDialogComponent as Component, params['id']);
            } else {
                this.okuSoforPopupService
                    .open(OkuSoforDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
