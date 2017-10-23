import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuYolcu } from './oku-yolcu.model';
import { OkuYolcuPopupService } from './oku-yolcu-popup.service';
import { OkuYolcuService } from './oku-yolcu.service';

@Component({
    selector: 'jhi-oku-yolcu-delete-dialog',
    templateUrl: './oku-yolcu-delete-dialog.component.html'
})
export class OkuYolcuDeleteDialogComponent {

    okuYolcu: OkuYolcu;

    constructor(
        private okuYolcuService: OkuYolcuService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuYolcuService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuYolcuListModification',
                content: 'Deleted an okuYolcu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-yolcu-delete-popup',
    template: ''
})
export class OkuYolcuDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuYolcuPopupService: OkuYolcuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuYolcuPopupService
                .open(OkuYolcuDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
