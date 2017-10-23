import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuYolcu } from './oku-yolcu.model';
import { OkuYolcuService } from './oku-yolcu.service';

@Component({
    selector: 'jhi-oku-yolcu-detail',
    templateUrl: './oku-yolcu-detail.component.html'
})
export class OkuYolcuDetailComponent implements OnInit, OnDestroy {

    okuYolcu: OkuYolcu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuYolcuService: OkuYolcuService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuYolcus();
    }

    load(id) {
        this.okuYolcuService.find(id).subscribe((okuYolcu) => {
            this.okuYolcu = okuYolcu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuYolcus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuYolcuListModification',
            (response) => this.load(this.okuYolcu.id)
        );
    }
}
