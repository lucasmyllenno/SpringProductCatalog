import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProductModel } from './product.model';

@Injectable({
    providedIn: 'root'
})
export class ProductService {

    constructor(private http: HttpClient) {
    }

    public getProducts(): Observable<ProductModel[]> {
        return this.http.get<ProductModel[]>(`${environment.endpoints.path}/products`);
    }

    public getProductById(id: number): Observable<ProductModel> {
        return this.http.get<ProductModel>(`${environment.endpoints.path}/products/${id}`);
    }

    public getProductByTerm(term: string): Observable<ProductModel[]> {
        return this.http.get<ProductModel[]>(`${environment.endpoints.path}/products/search/${term}`);
    }

    public postProduct(product: ProductModel): Observable<ProductModel> {
        return this.http.post<ProductModel>(`${environment.endpoints.path}/products`, product);
    }

    public putProduct(id: string, product: ProductModel): Observable<ProductModel> {
        return this.http.put<ProductModel>(`${environment.endpoints.path}/products/${id}`, product);
    }

    public deleteProduct(id: string): Observable<ProductModel> {
        return this.http.delete<ProductModel>(`${environment.endpoints.path}/products/${id}`);
    }
}
