import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OkuSefer } from './oku-sefer.model';
import { OkuSeferService } from './oku-sefer.service';

@Injectable()
export class OkuSeferPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private okuSeferService: OkuSeferService

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
                this.okuSeferService.find(id).subscribe((okuSefer) => {
                    if (okuSefer.tarih) {
                        okuSefer.tarih = {
                            year: okuSefer.tarih.getFullYear(),
                            month: okuSefer.tarih.getMonth() + 1,
                            day: okuSefer.tarih.getDate()
                        };
                    }
                    this.ngbModalRef = this.okuSeferModalRef(component, okuSefer);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.okuSeferModalRef(component, new OkuSefer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    okuSeferModalRef(component: Component, okuSefer: OkuSefer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.okuSefer = okuSefer;
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
