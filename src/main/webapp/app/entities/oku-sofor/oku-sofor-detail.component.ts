import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuSofor } from './oku-sofor.model';
import { OkuSoforService } from './oku-sofor.service';

@Component({
    selector: 'jhi-oku-sofor-detail',
    templateUrl: './oku-sofor-detail.component.html'
})
export class OkuSoforDetailComponent implements OnInit, OnDestroy {

    okuSofor: OkuSofor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuSoforService: OkuSoforService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuSofors();
    }

    load(id) {
        this.okuSoforService.find(id).subscribe((okuSofor) => {
            this.okuSofor = okuSofor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuSofors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuSoforListModification',
            (response) => this.load(this.okuSofor.id)
        );
    }
}
