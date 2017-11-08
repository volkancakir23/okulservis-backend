import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OkuPersonel } from './oku-personel.model';
import { OkuPersonelPopupService } from './oku-personel-popup.service';
import { OkuPersonelService } from './oku-personel.service';

@Component({
    selector: 'jhi-oku-personel-delete-dialog',
    templateUrl: './oku-personel-delete-dialog.component.html'
})
export class OkuPersonelDeleteDialogComponent {

    okuPersonel: OkuPersonel;

    constructor(
        private okuPersonelService: OkuPersonelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.okuPersonelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'okuPersonelListModification',
                content: 'Deleted an okuPersonel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oku-personel-delete-popup',
    template: ''
})
export class OkuPersonelDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private okuPersonelPopupService: OkuPersonelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.okuPersonelPopupService
                .open(OkuPersonelDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
