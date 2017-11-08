import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OkuOgrenci } from './oku-ogrenci.model';
import { OkuOgrenciService } from './oku-ogrenci.service';

@Injectable()
export class OkuOgrenciPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private okuOgrenciService: OkuOgrenciService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.okuOgrenciService.find(id).subscribe((okuOgrenci) => {
                    this.ngbModalRef = this.okuOgrenciModalRef(component, okuOgrenci);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.okuOgrenciModalRef(component, new OkuOgrenci());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    okuOgrenciModalRef(component: Component, okuOgrenci: OkuOgrenci): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.okuOgrenci = okuOgrenci;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
