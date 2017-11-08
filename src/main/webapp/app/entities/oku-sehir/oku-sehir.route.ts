import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuSehirComponent } from './oku-sehir.component';
import { OkuSehirDetailComponent } from './oku-sehir-detail.component';
import { OkuSehirPopupComponent } from './oku-sehir-dialog.component';
import { OkuSehirDeletePopupComponent } from './oku-sehir-delete-dialog.component';

@Injectable()
export class OkuSehirResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const okuSehirRoute: Routes = [
    {
        path: 'oku-sehir',
        component: OkuSehirComponent,
        resolve: {
            'pagingParams': OkuSehirResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSehirs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-sehir/:id',
        component: OkuSehirDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSehirs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuSehirPopupRoute: Routes = [
    {
        path: 'oku-sehir-new',
        component: OkuSehirPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSehirs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-sehir/:id/edit',
        component: OkuSehirPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSehirs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-sehir/:id/delete',
        component: OkuSehirDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSehirs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
