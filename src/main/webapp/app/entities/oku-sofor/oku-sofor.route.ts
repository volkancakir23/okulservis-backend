import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuSoforComponent } from './oku-sofor.component';
import { OkuSoforDetailComponent } from './oku-sofor-detail.component';
import { OkuSoforPopupComponent } from './oku-sofor-dialog.component';
import { OkuSoforDeletePopupComponent } from './oku-sofor-delete-dialog.component';

@Injectable()
export class OkuSoforResolvePagingParams implements Resolve<any> {

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

export const okuSoforRoute: Routes = [
    {
        path: 'oku-sofor',
        component: OkuSoforComponent,
        resolve: {
            'pagingParams': OkuSoforResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSofors'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-sofor/:id',
        component: OkuSoforDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSofors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuSoforPopupRoute: Routes = [
    {
        path: 'oku-sofor-new',
        component: OkuSoforPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSofors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-sofor/:id/edit',
        component: OkuSoforPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSofors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-sofor/:id/delete',
        component: OkuSoforDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSofors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
