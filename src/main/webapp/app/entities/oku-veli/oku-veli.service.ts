import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { OkuVeli } from './oku-veli.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OkuVeliService {

    private resourceUrl = SERVER_API_URL + 'api/oku-velis';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/oku-velis';

    constructor(private http: Http) { }

    create(okuVeli: OkuVeli): Observable<OkuVeli> {
        const copy = this.convert(okuVeli);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(okuVeli: OkuVeli): Observable<OkuVeli> {
        const copy = this.convert(okuVeli);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OkuVeli> {
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
     * Convert a returned JSON object to OkuVeli.
     */
    private convertItemFromServer(json: any): OkuVeli {
        const entity: OkuVeli = Object.assign(new OkuVeli(), json);
        return entity;
    }

    /**
     * Convert a OkuVeli to a JSON which can be sent to the server.
     */
    private convert(okuVeli: OkuVeli): OkuVeli {
        const copy: OkuVeli = Object.assign({}, okuVeli);
        return copy;
    }
}
