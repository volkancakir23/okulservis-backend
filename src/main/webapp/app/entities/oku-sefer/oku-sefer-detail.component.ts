import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuSefer } from './oku-sefer.model';
import { OkuSeferService } from './oku-sefer.service';

@Component({
    selector: 'jhi-oku-sefer-detail',
    templateUrl: './oku-sefer-detail.component.html'
})
export class OkuSeferDetailComponent implements OnInit, OnDestroy {

    okuSefer: OkuSefer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuSeferService: OkuSeferService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuSefers();
    }

    load(id) {
        this.okuSeferService.find(id).subscribe((okuSefer) => {
            this.okuSefer = okuSefer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuSefers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuSeferListModification',
            (response) => this.load(this.okuSefer.id)
        );
    }
}
