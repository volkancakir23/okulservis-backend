import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OkuSefer } from './oku-sefer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OkuSeferService {

    private resourceUrl = SERVER_API_URL + 'api/oku-sefers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/oku-sefers';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(okuSefer: OkuSefer): Observable<OkuSefer> {
        const copy = this.convert(okuSefer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(okuSefer: OkuSefer): Observable<OkuSefer> {
        const copy = this.convert(okuSefer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OkuSefer> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to OkuSefer.
     */
    private convertItemFromServer(json: any): OkuSefer {
        const entity: OkuSefer = Object.assign(new OkuSefer(), json);
        entity.tarih = this.dateUtils
            .convertLocalDateFromServer(json.tarih);
        return entity;
    }

    /**
     * Convert a OkuSefer to a JSON which can be sent to the server.
     */
    private convert(okuSefer: OkuSefer): OkuSefer {
        const copy: OkuSefer = Object.assign({}, okuSefer);
        copy.tarih = this.dateUtils
            .convertLocalDateToServer(okuSefer.tarih);
        return copy;
    }
}
