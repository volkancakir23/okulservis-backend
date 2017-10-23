import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuArac } from './oku-arac.model';
import { OkuAracPopupService } from './oku-arac-popup.service';
import { OkuAracService } from './oku-arac.service';

@Component({
    selector: 'jhi-oku-arac-delete-dialog',
    templateUrl: './oku-arac-delete-dialog.component.html'
})
export class OkuAracDeleteDialogComponent {

    okuArac: OkuArac;

    constructor(
        private okuAracService: OkuAracService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuAracService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuAracListModification',
                content: 'Deleted an okuArac'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-arac-delete-popup',
    template: ''
})
export class OkuAracDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuAracPopupService: OkuAracPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuAracPopupService
                .open(OkuAracDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
