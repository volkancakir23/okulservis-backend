import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { OkuYolcu } from './oku-yolcu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OkuYolcuService {

    private resourceUrl = SERVER_API_URL + 'api/oku-yolcus';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/oku-yolcus';

    constructor(private http: Http) { }

    create(okuYolcu: OkuYolcu): Observable<OkuYolcu> {
        const copy = this.convert(okuYolcu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(okuYolcu: OkuYolcu): Observable<OkuYolcu> {
        const copy = this.convert(okuYolcu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OkuYolcu> {
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
     * Convert a returned JSON object to OkuYolcu.
     */
    private convertItemFromServer(json: any): OkuYolcu {
        const entity: OkuYolcu = Object.assign(new OkuYolcu(), json);
        return entity;
    }

    /**
     * Convert a OkuYolcu to a JSON which can be sent to the server.
     */
    private convert(okuYolcu: OkuYolcu): OkuYolcu {
        const copy: OkuYolcu = Object.assign({}, okuYolcu);
        return copy;
    }
}
