import { Component } from '@angular/core';
import {BankService} from "../service/bank.service";

@Component({
  selector: 'app-bank',
  templateUrl: './bank.component.html',
  styleUrls: ['./bank.component.css']
})
export class BankComponent {
  posts : any;

  constructor(private httpService: BankService) { }

  ngOnInit() {
    this.httpService.getBanks().subscribe(
      (response) => { this.posts = response; console.log(this.posts)},
      (error) => { console.log(error);});
  }
}

