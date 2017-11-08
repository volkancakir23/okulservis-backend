import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuPersonel } from './oku-personel.model';
import { OkuPersonelService } from './oku-personel.service';

@Component({
    selector: 'jhi-oku-personel-detail',
    templateUrl: './oku-personel-detail.component.html'
})
export class OkuPersonelDetailComponent implements OnInit, OnDestroy {

    okuPersonel: OkuPersonel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuPersonelService: OkuPersonelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuPersonels();
    }

    load(id) {
        this.okuPersonelService.find(id).subscribe((okuPersonel) => {
            this.okuPersonel = okuPersonel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuPersonels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuPersonelListModification',
            (response) => this.load(this.okuPersonel.id)
        );
    }
}
