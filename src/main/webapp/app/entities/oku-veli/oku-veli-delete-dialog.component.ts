import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuVeli } from './oku-veli.model';
import { OkuVeliPopupService } from './oku-veli-popup.service';
import { OkuVeliService } from './oku-veli.service';

@Component({
    selector: 'jhi-oku-veli-delete-dialog',
    templateUrl: './oku-veli-delete-dialog.component.html'
})
export class OkuVeliDeleteDialogComponent {

    okuVeli: OkuVeli;

    constructor(
        private okuVeliService: OkuVeliService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuVeliService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuVeliListModification',
                content: 'Deleted an okuVeli'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-veli-delete-popup',
    template: ''
})
export class OkuVeliDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuVeliPopupService: OkuVeliPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuVeliPopupService
                .open(OkuVeliDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
