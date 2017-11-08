import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuSeferComponent } from './oku-sefer.component';
import { OkuSeferDetailComponent } from './oku-sefer-detail.component';
import { OkuSeferPopupComponent } from './oku-sefer-dialog.component';
import { OkuSeferDeletePopupComponent } from './oku-sefer-delete-dialog.component';

@Injectable()
export class OkuSeferResolvePagingParams implements Resolve<any> {

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

export const okuSeferRoute: Routes = [
    {
        path: 'oku-sefer',
        component: OkuSeferComponent,
        resolve: {
            'pagingParams': OkuSeferResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSefers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-sefer/:id',
        component: OkuSeferDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSefers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuSeferPopupRoute: Routes = [
    {
        path: 'oku-sefer-new',
        component: OkuSeferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSefers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-sefer/:id/edit',
        component: OkuSeferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSefers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-sefer/:id/delete',
        component: OkuSeferDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuSefers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
