import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuPersonel } from './oku-personel.model';
import { OkuPersonelPopupService } from './oku-personel-popup.service';
import { OkuPersonelService } from './oku-personel.service';
import { OkuOkul, OkuOkulService } from '../oku-okul';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oku-personel-dialog',
    templateUrl: './oku-personel-dialog.component.html'
})
export class OkuPersonelDialogComponent implements OnInit {

    okuPersonel: OkuPersonel;
    isSaving: boolean;

    okuokuls: OkuOkul[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuPersonelService: OkuPersonelService,
        private okuOkulService: OkuOkulService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.okuOkulService.query()
            .subscribe((res: ResponseWrapper) => { this.okuokuls = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuPersonel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuPersonelService.update(this.okuPersonel));
        } else {
            this.subscribeToSaveResponse(
                this.okuPersonelService.create(this.okuPersonel));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuPersonel>) {
        result.subscribe((res: OkuPersonel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuPersonel) {
        this.eventManager.broadcast({ name: 'okuPersonelListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-oku-personel-popup',
    template: ''
})
export class OkuPersonelPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuPersonelPopupService: OkuPersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuPersonelPopupService
                    .open(OkuPersonelDialogComponent as Component, params['id']);
            } else {
                this.okuPersonelPopupService
                    .open(OkuPersonelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
