import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuOkul } from './oku-okul.model';
import { OkuOkulService } from './oku-okul.service';

@Component({
    selector: 'jhi-oku-okul-detail',
    templateUrl: './oku-okul-detail.component.html'
})
export class OkuOkulDetailComponent implements OnInit, OnDestroy {

    okuOkul: OkuOkul;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuOkulService: OkuOkulService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuOkuls();
    }

    load(id) {
        this.okuOkulService.find(id).subscribe((okuOkul) => {
            this.okuOkul = okuOkul;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuOkuls() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuOkulListModification',
            (response) => this.load(this.okuOkul.id)
        );
    }
}
