import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuSofor } from './oku-sofor.model';
import { OkuSoforPopupService } from './oku-sofor-popup.service';
import { OkuSoforService } from './oku-sofor.service';

@Component({
    selector: 'jhi-oku-sofor-delete-dialog',
    templateUrl: './oku-sofor-delete-dialog.component.html'
})
export class OkuSoforDeleteDialogComponent {

    okuSofor: OkuSofor;

    constructor(
        private okuSoforService: OkuSoforService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuSoforService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuSoforListModification',
                content: 'Deleted an okuSofor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-sofor-delete-popup',
    template: ''
})
export class OkuSoforDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuSoforPopupService: OkuSoforPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuSoforPopupService
                .open(OkuSoforDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
