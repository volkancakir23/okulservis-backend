import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuVeli } from './oku-veli.model';
import { OkuVeliService } from './oku-veli.service';

@Component({
    selector: 'jhi-oku-veli-detail',
    templateUrl: './oku-veli-detail.component.html'
})
export class OkuVeliDetailComponent implements OnInit, OnDestroy {

    okuVeli: OkuVeli;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuVeliService: OkuVeliService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuVelis();
    }

    load(id) {
        this.okuVeliService.find(id).subscribe((okuVeli) => {
            this.okuVeli = okuVeli;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuVelis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuVeliListModification',
            (response) => this.load(this.okuVeli.id)
        );
    }
}
