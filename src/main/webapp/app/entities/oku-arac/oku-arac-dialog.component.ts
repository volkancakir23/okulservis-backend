import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuArac } from './oku-arac.model';
import { OkuAracPopupService } from './oku-arac-popup.service';
import { OkuAracService } from './oku-arac.service';

@Component({
    selector: 'jhi-oku-arac-dialog',
    templateUrl: './oku-arac-dialog.component.html'
})
export class OkuAracDialogComponent implements OnInit {

    okuArac: OkuArac;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuAracService: OkuAracService,
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
        if (this.okuArac.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuAracService.update(this.okuArac));
        } else {
            this.subscribeToSaveResponse(
                this.okuAracService.create(this.okuArac));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuArac>) {
        result.subscribe((res: OkuArac) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuArac) {
        this.eventManager.broadcast({ name: 'okuAracListModification', content: 'OK'});
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
    selector: 'jhi-oku-arac-popup',
    template: ''
})
export class OkuAracPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuAracPopupService: OkuAracPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuAracPopupService
                    .open(OkuAracDialogComponent as Component, params['id']);
            } else {
                this.okuAracPopupService
                    .open(OkuAracDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
