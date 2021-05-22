import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductModel } from './product.model';

@Injectable({
    providedIn: 'root'
})
export class ProductFormService {

    public formGroup: FormGroup;

    constructor(private fb: FormBuilder) {
        this.initForm();
    }

    public initForm(): void {
        this.formGroup = this.fb.group({
            id: [''],
            name: ['', Validators.required],
            description: ['', Validators.required],
            price: ['', [Validators.required, Validators.pattern('(\\d{1,4}\\.(\\d{1}[1-9]|[1-9]\\d{0,1})|\\b([1-9]\\d{0,3})\\b)')]]
        });
    }

    public reset(): void {
        this.formGroup?.reset();
        this.formGroup.get('status')?.patchValue('Ativo');
    }

    public setValues(values: ProductModel): void {
        this.formGroup?.patchValue(values);
    }

    public getValues(): ProductModel {
        return this.formGroup?.getRawValue() as ProductModel;
    }

    public activeValidations() {
        this.formGroup.markAllAsTouched();
    }
}
