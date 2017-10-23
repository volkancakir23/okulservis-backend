import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OkuGuzergahComponent } from './oku-guzergah.component';
import { OkuGuzergahDetailComponent } from './oku-guzergah-detail.component';
import { OkuGuzergahPopupComponent } from './oku-guzergah-dialog.component';
import { OkuGuzergahDeletePopupComponent } from './oku-guzergah-delete-dialog.component';

@Injectable()
export class OkuGuzergahResolvePagingParams implements Resolve<any> {

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

export const okuGuzergahRoute: Routes = [
    {
        path: 'oku-guzergah',
        component: OkuGuzergahComponent,
        resolve: {
            'pagingParams': OkuGuzergahResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuGuzergahs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oku-guzergah/:id',
        component: OkuGuzergahDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuGuzergahs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const okuGuzergahPopupRoute: Routes = [
    {
        path: 'oku-guzergah-new',
        component: OkuGuzergahPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuGuzergahs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-guzergah/:id/edit',
        component: OkuGuzergahPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuGuzergahs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oku-guzergah/:id/delete',
        component: OkuGuzergahDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OkuGuzergahs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
