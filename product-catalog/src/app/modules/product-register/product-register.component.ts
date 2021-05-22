import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ProductModel } from './product.model';
import { ProductFormService } from './product-form.service';
import { ProductService } from './product.service';

@Component({
    selector: 'app-product-register',
    templateUrl: './product-register.component.html',
    styleUrls: ['./product-register.component.scss']
})
export class ProductRegisterComponent implements OnInit {

    constructor(@Inject(MAT_DIALOG_DATA) public data: ProductModel,
                private dialogRef: MatDialogRef<ProductRegisterComponent>,
                public formService: ProductFormService,
                private service: ProductService) {
    }

    ngOnInit(): void {
        if (this.data) {
            this.formService.setValues(this.data);
        } else {
            this.formService.reset();
        }
    }

    public onClickSubmit(): void {
        const values = this.formService.getValues();
        if (this.formService.formGroup.valid) {
            if (!values.id) {
                delete values.id;
                this.service.postProduct(values).subscribe(
                    () => this.closeModal(true),
                    error => this.closeModal(false)
                );
            } else {
                this.service.putProduct(values.id, values).subscribe(
                    () => this.closeModal(true),
                    error => this.closeModal(false)
                );
            }
        } else {
            this.formService.activeValidations();
        }
    }

    public closeModal(success: boolean): void {
        this.dialogRef.close(success);
    }
}
