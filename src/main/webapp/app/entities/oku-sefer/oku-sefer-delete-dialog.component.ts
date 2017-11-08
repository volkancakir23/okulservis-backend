import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuSefer } from './oku-sefer.model';
import { OkuSeferPopupService } from './oku-sefer-popup.service';
import { OkuSeferService } from './oku-sefer.service';

@Component({
    selector: 'jhi-oku-sefer-delete-dialog',
    templateUrl: './oku-sefer-delete-dialog.component.html'
})
export class OkuSeferDeleteDialogComponent {

    okuSefer: OkuSefer;

    constructor(
        private okuSeferService: OkuSeferService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuSeferService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuSeferListModification',
                content: 'Deleted an okuSefer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-sefer-delete-popup',
    template: ''
})
export class OkuSeferDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuSeferPopupService: OkuSeferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuSeferPopupService
                .open(OkuSeferDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
