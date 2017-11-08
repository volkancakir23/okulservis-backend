import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuOkul } from './oku-okul.model';
import { OkuOkulPopupService } from './oku-okul-popup.service';
import { OkuOkulService } from './oku-okul.service';

@Component({
    selector: 'jhi-oku-okul-delete-dialog',
    templateUrl: './oku-okul-delete-dialog.component.html'
})
export class OkuOkulDeleteDialogComponent {

    okuOkul: OkuOkul;

    constructor(
        private okuOkulService: OkuOkulService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuOkulService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuOkulListModification',
                content: 'Deleted an okuOkul'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-okul-delete-popup',
    template: ''
})
export class OkuOkulDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuOkulPopupService: OkuOkulPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuOkulPopupService
                .open(OkuOkulDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
