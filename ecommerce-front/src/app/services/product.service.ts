import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../common/product';
import {map} from 'rxjs/operators';
import {ProductCategory} from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = 'http://localhost:8080/api/products';
  private categoryUrl = 'http://localhost:8080/api/product-category';

  constructor(private http: HttpClient) { }

  getProductListPaginate(thePage: number,
                         thePageSize: number,
                         theCategoryId: number): Observable<GetResponseProduct> {
    const url = `${this.baseUrl}/search/findByCategoryId`
      + `?id=${theCategoryId}&page=${thePage}&size=${thePageSize}`
    ;
    return this.http.get<GetResponseProduct>(url);
  }
  getProductList(theCategoryId: number): Observable<Product[]> {
    // need to build URL based on category id
    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}`;
    return this.getProducts(searchUrl);
  }
  getProductCategories(): Observable<ProductCategory[]> {

    return this.http.get<GetResponseProductCategory>(this.categoryUrl).pipe(
      map(response => response._embedded.productCategory)
    );
  }
  searchProducts(theKeyword: string | null): Observable<Product[]> {
    // need to build URL based on the keyword
    const searchUrl = `${this.baseUrl}/search/findByNameContaining?name=${theKeyword}`;
    return this.getProducts(searchUrl);
  }

  searchProductsPaginate(thePage: number,
                         thePageSize: number,
                         theKeyword: string | null): Observable<GetResponseProduct> {
    const searchUrl = `${this.baseUrl}/search/findByNameContaining`
      + `?name=${theKeyword}&page=${thePage}&size=${thePageSize}`
    ;
    return this.http.get<GetResponseProduct>(searchUrl);
  }
  getProduct(theProductId: number): Observable<Product> {
    // need to build URL based on product id
    const productUrl = `${this.baseUrl}/${theProductId}`
    ;
    return this.http.get<Product>(productUrl);
  }

  private getProducts(searchUrl: string) {
    return this.http.get<GetResponseProduct>(searchUrl).pipe(
      map(response => response._embedded.products));
  }

}
interface GetResponseProduct {
  _embedded: {
    products: Product[];
  }
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}
interface GetResponseProductCategory {
  _embedded: {
    productCategory: ProductCategory[];
  }
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}
