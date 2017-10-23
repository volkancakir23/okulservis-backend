import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuOgrenci } from './oku-ogrenci.model';
import { OkuOgrenciService } from './oku-ogrenci.service';

@Component({
    selector: 'jhi-oku-ogrenci-detail',
    templateUrl: './oku-ogrenci-detail.component.html'
})
export class OkuOgrenciDetailComponent implements OnInit, OnDestroy {

    okuOgrenci: OkuOgrenci;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuOgrenciService: OkuOgrenciService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuOgrencis();
    }

    load(id) {
        this.okuOgrenciService.find(id).subscribe((okuOgrenci) => {
            this.okuOgrenci = okuOgrenci;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuOgrencis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuOgrenciListModification',
            (response) => this.load(this.okuOgrenci.id)
        );
    }
}
