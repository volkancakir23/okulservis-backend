import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuYolcuComponent } from './oku-yolcu.component';
import { OkuYolcuDetailComponent } from './oku-yolcu-detail.component';
import { OkuYolcuPopupComponent } from './oku-yolcu-dialog.component';
import { OkuYolcuDeletePopupComponent } from './oku-yolcu-delete-dialog.component';

@Injectable()
export class OkuYolcuResolvePagingParams implements Resolve<any> {

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

export const okuYolcuRoute: Routes = [
    {
        path: 'oku-yolcu',
        component: OkuYolcuComponent,
        resolve: {
            'pagingParams': OkuYolcuResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuYolcus'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-yolcu/:id',
        component: OkuYolcuDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuYolcus'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuYolcuPopupRoute: Routes = [
    {
        path: 'oku-yolcu-new',
        component: OkuYolcuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuYolcus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-yolcu/:id/edit',
        component: OkuYolcuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuYolcus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-yolcu/:id/delete',
        component: OkuYolcuDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuYolcus'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
