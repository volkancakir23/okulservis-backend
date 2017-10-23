import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuGuzergah } from './oku-guzergah.model';
import { OkuGuzergahService } from './oku-guzergah.service';

@Component({
    selector: 'jhi-oku-guzergah-detail',
    templateUrl: './oku-guzergah-detail.component.html'
})
export class OkuGuzergahDetailComponent implements OnInit, OnDestroy {

    okuGuzergah: OkuGuzergah;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuGuzergahService: OkuGuzergahService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuGuzergahs();
    }

    load(id) {
        this.okuGuzergahService.find(id).subscribe((okuGuzergah) => {
            this.okuGuzergah = okuGuzergah;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuGuzergahs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuGuzergahListModification',
            (response) => this.load(this.okuGuzergah.id)
        );
    }
}
