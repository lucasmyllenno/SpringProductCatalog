import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'app-confirmation-dialog',
    templateUrl: './confirmation-dialog.component.html',
    styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent {

    constructor(@Inject(MAT_DIALOG_DATA) public title: string,
                private dialog: MatDialogRef<ConfirmationDialogComponent>) {
    }

    public cancel() {
        this.dialog.close(false);
    }

    public confirm() {
        this.dialog.close(true);
    }

}
