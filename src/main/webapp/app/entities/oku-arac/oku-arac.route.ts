import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuAracComponent } from './oku-arac.component';
import { OkuAracDetailComponent } from './oku-arac-detail.component';
import { OkuAracPopupComponent } from './oku-arac-dialog.component';
import { OkuAracDeletePopupComponent } from './oku-arac-delete-dialog.component';

@Injectable()
export class OkuAracResolvePagingParams implements Resolve<any> {

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

export const okuAracRoute: Routes = [
    {
        path: 'oku-arac',
        component: OkuAracComponent,
        resolve: {
            'pagingParams': OkuAracResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuAracs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-arac/:id',
        component: OkuAracDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuAracs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuAracPopupRoute: Routes = [
    {
        path: 'oku-arac-new',
        component: OkuAracPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuAracs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-arac/:id/edit',
        component: OkuAracPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuAracs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-arac/:id/delete',
        component: OkuAracDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuAracs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
