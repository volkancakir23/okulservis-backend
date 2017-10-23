import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuOgrenciComponent } from './oku-ogrenci.component';
import { OkuOgrenciDetailComponent } from './oku-ogrenci-detail.component';
import { OkuOgrenciPopupComponent } from './oku-ogrenci-dialog.component';
import { OkuOgrenciDeletePopupComponent } from './oku-ogrenci-delete-dialog.component';

@Injectable()
export class OkuOgrenciResolvePagingParams implements Resolve<any> {

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

export const okuOgrenciRoute: Routes = [
    {
        path: 'oku-ogrenci',
        component: OkuOgrenciComponent,
        resolve: {
            'pagingParams': OkuOgrenciResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOgrencis'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-ogrenci/:id',
        component: OkuOgrenciDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOgrencis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuOgrenciPopupRoute: Routes = [
    {
        path: 'oku-ogrenci-new',
        component: OkuOgrenciPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOgrencis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-ogrenci/:id/edit',
        component: OkuOgrenciPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOgrencis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-ogrenci/:id/delete',
        component: OkuOgrenciDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOgrencis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
