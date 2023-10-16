import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuchungssaetzeComponent } from './buchungssaetze.component';

describe('BuchungssaetzeComponent', () => {
  let component: BuchungssaetzeComponent;
  let fixture: ComponentFixture<BuchungssaetzeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuchungssaetzeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuchungssaetzeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
