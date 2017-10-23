import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuGuzergah } from './oku-guzergah.model';
import { OkuGuzergahPopupService } from './oku-guzergah-popup.service';
import { OkuGuzergahService } from './oku-guzergah.service';

@Component({
    selector: 'jhi-oku-guzergah-delete-dialog',
    templateUrl: './oku-guzergah-delete-dialog.component.html'
})
export class OkuGuzergahDeleteDialogComponent {

    okuGuzergah: OkuGuzergah;

    constructor(
        private okuGuzergahService: OkuGuzergahService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuGuzergahService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuGuzergahListModification',
                content: 'Deleted an okuGuzergah'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-guzergah-delete-popup',
    template: ''
})
export class OkuGuzergahDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuGuzergahPopupService: OkuGuzergahPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuGuzergahPopupService
                .open(OkuGuzergahDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
