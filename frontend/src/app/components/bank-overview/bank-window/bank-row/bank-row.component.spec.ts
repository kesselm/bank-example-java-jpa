import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankRowComponent } from './bank-row.component';

describe('BankRowComponent', () => {
  let component: BankRowComponent;
  let fixture: ComponentFixture<BankRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BankRowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
