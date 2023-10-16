import { Component } from '@angular/core';
import {BankService} from "./service/bank.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'bank-frontend-example';
  posts : any;

  constructor(private httpService: BankService) { }

  ngOnInit() {
    this.httpService.getBanks().subscribe(
      (response) => { this.posts = response; console.log(this.posts)},
      (error) => { console.log(error);});
  }
}
