import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProductModel } from '../product-register/product.model';
import { ProductService } from '../product-register/product.service';
import { ProductRegisterComponent } from '../product-register/product-register.component';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { Observable } from 'rxjs';

export interface ColumnModel {
    title: string;
    property: string;
    displayed: boolean;
    displayFn?: any;
}

@Component({
    selector: 'app-catalog-table',
    templateUrl: './catalog-table.component.html',
    styleUrls: ['./catalog-table.component.scss']
})
export class CatalogTableComponent implements OnInit {

    @ViewChild(MatPaginator, {static: false}) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

    public dataSource: MatTableDataSource<ProductModel>;
    public searchFormControl: FormControl;

    public columns: ColumnModel[] = [
        {title: 'Nome', property: 'name', displayed: true},
        {title: 'Descrição', property: 'description', displayed: true},
        {title: 'Preço', property: 'price', displayed: true, displayFn: (price: string) => `${price} R$`},
        {title: 'Criado em', property: 'createdAt', displayed: true, displayFn: (date: Date) => new Date(date).toLocaleString()},
        {title: 'Atualizado em', property: 'updatedAt', displayed: true, displayFn: (date: Date) => new Date(date).toLocaleString()}
    ];

    constructor(private dialog: MatDialog,
                private snackBar: MatSnackBar,
                private productService: ProductService) {
        this.dataSource = new MatTableDataSource();
        this.searchFormControl = new FormControl();
    }

    public get displayedColumns(): string[] {
        const displayedColumns = this.columns.filter(
            column => column.displayed
        );
        const columnsProperty = displayedColumns.map(
            column => column.property
        );
        return [...columnsProperty, 'actions'];
    }

    ngOnInit(): void {
        this.fetchData(undefined);
    }

    public fetchData(term: string | undefined): void {
        // tslint:disable-next-line:no-shadowed-variable
        let observable: Observable<ProductModel[]>;
        if (term) {
            observable = this.productService.getProductByTerm(term);
        } else {
            observable = this.productService.getProducts();
        }
        this.dataSource.data = [];
        observable.subscribe((products) => {
            if (products) {
                this.dataSource.data = products;
                this.dataSource.paginator = this.translatedPaginator(this.paginator);
                this.dataSource.sort = this.sort;
            }
        });
    }

    public createProduct(): void {
        this.dialog.open(ProductRegisterComponent).afterClosed().subscribe(result => {
            if (result) {
                this.fetchData(undefined);
            }
        });
    }

    public updateProduct(product: ProductModel): void {
        this.dialog.open(ProductRegisterComponent, {data: product}
        ).afterClosed().subscribe(result => {
            if (result) {
                this.fetchData(undefined);
            }
        });
    }

    public deleteProduct(productId: string): void {
        this.dialog.open(ConfirmationDialogComponent, {data: 'Você tem certeza que deseja remover este produto?'}
        ).afterClosed().subscribe(result => {
            if (result) {
                this.productService.deleteProduct(productId).subscribe(
                    () => this.fetchData(undefined),
                    error => console.log(error)
                );
            }
        });
    }

    public searchProduct(): void {
        let term = this.searchFormControl.value as string;
        if (term && term.length > 0) {
            term = term.trim();
            term = term.toLowerCase();
            this.fetchData(term);
        } else {
            this.fetchData(undefined);
        }
    }

    public toggleColumnVisibility(column: ColumnModel, event: any): void {
        event.stopPropagation();
        event.stopImmediatePropagation();
        column.displayed = !column.displayed;
    }

    public openSnackBar(message: string, action: string): void {
        this.snackBar.open(message, action, {
            duration: 2000,
            horizontalPosition: 'center'
        });
    }

    public translatedPaginator(paginator: MatPaginator): MatPaginator {
        paginator._intl.itemsPerPageLabel = 'Itens por página';
        paginator._intl.nextPageLabel = 'Próximos itens';
        paginator._intl.previousPageLabel = 'Itens anteriores';
        return paginator;
    }
}
