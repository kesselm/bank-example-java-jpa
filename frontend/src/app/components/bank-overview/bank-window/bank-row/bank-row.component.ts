import { Component } from '@angular/core';
import {BankService} from "../../../../service/bank.service";

@Component({
  selector: 'app-bank-row',
  templateUrl: './bank-row.component.html',
  styleUrls: ['./bank-row.component.css']
})
export class BankRowComponent {

  constructor(private bankService: BankService) {}

  saveBank(): void {
    const bookDAO = {

    }

    this.bankService.saveBank(bookDAO)
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (e) => console.error(e)
      })

  }

}
