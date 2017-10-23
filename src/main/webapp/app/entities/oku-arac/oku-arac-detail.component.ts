import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OkuArac } from './oku-arac.model';
import { OkuAracService } from './oku-arac.service';

@Component({
    selector: 'jhi-oku-arac-detail',
    templateUrl: './oku-arac-detail.component.html'
})
export class OkuAracDetailComponent implements OnInit, OnDestroy {

    okuArac: OkuArac;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private okuAracService: OkuAracService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOkuAracs();
    }

    load(id) {
        this.okuAracService.find(id).subscribe((okuArac) => {
            this.okuArac = okuArac;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOkuAracs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'okuAracListModification',
            (response) => this.load(this.okuArac.id)
        );
    }
}
