import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BankComponent } from './bank/bank.component';
import {HttpClientModule} from "@angular/common/http";
import { BankOverviewComponent } from './components/bank-overview/bank-overview.component';
import { BankWindowComponent } from './components/bank-overview/bank-window/bank-window.component';
import { BankRowComponent } from './components/bank-overview/bank-window/bank-row/bank-row.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BuchungssaetzeComponent } from './components/buchungssaetze/buchungssaetze.component';

@NgModule({
  declarations: [
    AppComponent,
    BankComponent,
    BankOverviewComponent,
    BankWindowComponent,
    BankRowComponent,
    BuchungssaetzeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
