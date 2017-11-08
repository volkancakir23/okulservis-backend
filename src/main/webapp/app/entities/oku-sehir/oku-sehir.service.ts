import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { OkuSehir } from './oku-sehir.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OkuSehirService {

    private resourceUrl = SERVER_API_URL + 'api/oku-sehirs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/oku-sehirs';

    constructor(private http: Http) { }

    create(okuSehir: OkuSehir): Observable<OkuSehir> {
        const copy = this.convert(okuSehir);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(okuSehir: OkuSehir): Observable<OkuSehir> {
        const copy = this.convert(okuSehir);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OkuSehir> {
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
     * Convert a returned JSON object to OkuSehir.
     */
    private convertItemFromServer(json: any): OkuSehir {
        const entity: OkuSehir = Object.assign(new OkuSehir(), json);
        return entity;
    }

    /**
     * Convert a OkuSehir to a JSON which can be sent to the server.
     */
    private convert(okuSehir: OkuSehir): OkuSehir {
        const copy: OkuSehir = Object.assign({}, okuSehir);
        return copy;
    }
}
