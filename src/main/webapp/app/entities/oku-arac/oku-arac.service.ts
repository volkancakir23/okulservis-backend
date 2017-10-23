import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { OkuArac } from './oku-arac.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OkuAracService {

    private resourceUrl = SERVER_API_URL + 'api/oku-aracs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/oku-aracs';

    constructor(private http: Http) { }

    create(okuArac: OkuArac): Observable<OkuArac> {
        const copy = this.convert(okuArac);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(okuArac: OkuArac): Observable<OkuArac> {
        const copy = this.convert(okuArac);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OkuArac> {
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
     * Convert a returned JSON object to OkuArac.
     */
    private convertItemFromServer(json: any): OkuArac {
        const entity: OkuArac = Object.assign(new OkuArac(), json);
        return entity;
    }

    /**
     * Convert a OkuArac to a JSON which can be sent to the server.
     */
    private convert(okuArac: OkuArac): OkuArac {
        const copy: OkuArac = Object.assign({}, okuArac);
        return copy;
    }
}
