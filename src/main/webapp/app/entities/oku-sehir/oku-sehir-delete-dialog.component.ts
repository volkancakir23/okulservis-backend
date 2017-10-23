import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuSehir } from './oku-sehir.model';
import { OkuSehirPopupService } from './oku-sehir-popup.service';
import { OkuSehirService } from './oku-sehir.service';

@Component({
    selector: 'jhi-oku-sehir-delete-dialog',
    templateUrl: './oku-sehir-delete-dialog.component.html'
})
export class OkuSehirDeleteDialogComponent {

    okuSehir: OkuSehir;

    constructor(
        private okuSehirService: OkuSehirService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuSehirService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuSehirListModification',
                content: 'Deleted an okuSehir'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-sehir-delete-popup',
    template: ''
})
export class OkuSehirDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuSehirPopupService: OkuSehirPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuSehirPopupService
                .open(OkuSehirDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
