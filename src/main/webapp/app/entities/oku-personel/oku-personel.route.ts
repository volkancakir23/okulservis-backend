import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuPersonelComponent } from './oku-personel.component';
import { OkuPersonelDetailComponent } from './oku-personel-detail.component';
import { OkuPersonelPopupComponent } from './oku-personel-dialog.component';
import { OkuPersonelDeletePopupComponent } from './oku-personel-delete-dialog.component';

@Injectable()
export class OkuPersonelResolvePagingParams implements Resolve<any> {

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

export const okuPersonelRoute: Routes = [
    {
        path: 'oku-personel',
        component: OkuPersonelComponent,
        resolve: {
            'pagingParams': OkuPersonelResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuPersonels'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-personel/:id',
        component: OkuPersonelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuPersonels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuPersonelPopupRoute: Routes = [
    {
        path: 'oku-personel-new',
        component: OkuPersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuPersonels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-personel/:id/edit',
        component: OkuPersonelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuPersonels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-personel/:id/delete',
        component: OkuPersonelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuPersonels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
