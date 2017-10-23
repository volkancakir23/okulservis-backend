import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuSehir } from './oku-sehir.model';
import { OkuSehirService } from './oku-sehir.service';

@Component({
    selector: 'jhi-oku-sehir-detail',
    templateUrl: './oku-sehir-detail.component.html'
})
export class OkuSehirDetailComponent implements OnInit, OnDestroy {

    okuSehir: OkuSehir;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuSehirService: OkuSehirService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuSehirs();
    }

    load(id) {
        this.okuSehirService.find(id).subscribe((okuSehir) => {
            this.okuSehir = okuSehir;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuSehirs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuSehirListModification',
            (response) => this.load(this.okuSehir.id)
        );
    }
}
