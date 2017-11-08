import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuOkulComponent } from './oku-okul.component';
import { OkuOkulDetailComponent } from './oku-okul-detail.component';
import { OkuOkulPopupComponent } from './oku-okul-dialog.component';
import { OkuOkulDeletePopupComponent } from './oku-okul-delete-dialog.component';

@Injectable()
export class OkuOkulResolvePagingParams implements Resolve<any> {

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

export const okuOkulRoute: Routes = [
    {
        path: 'oku-okul',
        component: OkuOkulComponent,
        resolve: {
            'pagingParams': OkuOkulResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOkuls'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-okul/:id',
        component: OkuOkulDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOkuls'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuOkulPopupRoute: Routes = [
    {
        path: 'oku-okul-new',
        component: OkuOkulPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOkuls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-okul/:id/edit',
        component: OkuOkulPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOkuls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-okul/:id/delete',
        component: OkuOkulDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuOkuls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
