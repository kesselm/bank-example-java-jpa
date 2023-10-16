import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankWindowComponent } from './bank-window.component';

describe('BankWindowComponent', () => {
  let component: BankWindowComponent;
  let fixture: ComponentFixture<BankWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BankWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
