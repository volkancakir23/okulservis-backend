import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuVeliComponent } from './oku-veli.component';
import { OkuVeliDetailComponent } from './oku-veli-detail.component';
import { OkuVeliPopupComponent } from './oku-veli-dialog.component';
import { OkuVeliDeletePopupComponent } from './oku-veli-delete-dialog.component';

@Injectable()
export class OkuVeliResolvePagingParams implements Resolve<any> {

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

export const okuVeliRoute: Routes = [
    {
        path: 'oku-veli',
        component: OkuVeliComponent,
        resolve: {
            'pagingParams': OkuVeliResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuVelis'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-veli/:id',
        component: OkuVeliDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuVelis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuVeliPopupRoute: Routes = [
    {
        path: 'oku-veli-new',
        component: OkuVeliPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuVelis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-veli/:id/edit',
        component: OkuVeliPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuVelis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-veli/:id/delete',
        component: OkuVeliDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuVelis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
