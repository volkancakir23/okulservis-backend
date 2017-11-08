import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OkuGuzergah } from './oku-guzergah.model';
import { OkuGuzergahService } from './oku-guzergah.service';

@Injectable()
export class OkuGuzergahPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private okuGuzergahService: OkuGuzergahService

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
                this.okuGuzergahService.find(id).subscribe((okuGuzergah) => {
                    this.ngbModalRef = this.okuGuzergahModalRef(component, okuGuzergah);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.okuGuzergahModalRef(component, new OkuGuzergah());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    okuGuzergahModalRef(component: Component, okuGuzergah: OkuGuzergah): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.okuGuzergah = okuGuzergah;
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
