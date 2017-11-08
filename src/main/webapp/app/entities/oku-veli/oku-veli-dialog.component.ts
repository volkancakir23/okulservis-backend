import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OkuVeli } from './oku-veli.model';
import { OkuVeliPopupService } from './oku-veli-popup.service';
import { OkuVeliService } from './oku-veli.service';
import { OkuOgrenci, OkuOgrenciService } from '../oku-ogrenci';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oku-veli-dialog',
    templateUrl: './oku-veli-dialog.component.html'
})
export class OkuVeliDialogComponent implements OnInit {

    okuVeli: OkuVeli;
    isSaving: boolean;

    okuogrencis: OkuOgrenci[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private okuVeliService: OkuVeliService,
        private okuOgrenciService: OkuOgrenciService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.okuOgrenciService.query()
            .subscribe((res: ResponseWrapper) => { this.okuogrencis = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.okuVeli.id !== undefined) {
            this.subscribeToSaveResponse(
                this.okuVeliService.update(this.okuVeli));
        } else {
            this.subscribeToSaveResponse(
                this.okuVeliService.create(this.okuVeli));
        }
    }

    private subscribeToSaveResponse(result: Observable<OkuVeli>) {
        result.subscribe((res: OkuVeli) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OkuVeli) {
        this.eventManager.broadcast({ name: 'okuVeliListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOkuOgrenciById(index: number, item: OkuOgrenci) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-oku-veli-popup',
    template: ''
})
export class OkuVeliPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuVeliPopupService: OkuVeliPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.okuVeliPopupService
                    .open(OkuVeliDialogComponent as Component, params['id']);
            } else {
                this.okuVeliPopupService
                    .open(OkuVeliDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
