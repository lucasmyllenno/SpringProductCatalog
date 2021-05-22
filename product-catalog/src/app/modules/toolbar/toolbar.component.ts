import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UserModel } from './user.model';
import { UserService } from './user.service';

@Component({
    selector: 'app-toolbar',
    templateUrl: './toolbar.component.html',
    styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

    public user: BehaviorSubject<UserModel | undefined>;

    constructor(private service: UserService) {
        this.user = new BehaviorSubject<UserModel | undefined>(undefined);
    }

    ngOnInit() {
        this.service.getUser().subscribe(
            user => this.user.next(user),
            error => console.error(error)
        );
    }

    public changeUser(): void {
        // TODO
    }
}
