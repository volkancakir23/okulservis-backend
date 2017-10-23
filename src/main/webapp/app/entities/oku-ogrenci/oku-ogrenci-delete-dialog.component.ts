import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuOgrenci } from './oku-ogrenci.model';
import { OkuOgrenciPopupService } from './oku-ogrenci-popup.service';
import { OkuOgrenciService } from './oku-ogrenci.service';

@Component({
    selector: 'jhi-oku-ogrenci-delete-dialog',
    templateUrl: './oku-ogrenci-delete-dialog.component.html'
})
export class OkuOgrenciDeleteDialogComponent {

    okuOgrenci: OkuOgrenci;

    constructor(
        private okuOgrenciService: OkuOgrenciService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuOgrenciService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuOgrenciListModification',
                content: 'Deleted an okuOgrenci'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-ogrenci-delete-popup',
    template: ''
})
export class OkuOgrenciDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuOgrenciPopupService: OkuOgrenciPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuOgrenciPopupService
                .open(OkuOgrenciDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
