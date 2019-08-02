import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeBookingDetailComponent } from './time-booking-detail.component';

describe('TimeBookingDetailComponent', () => {
  let component: TimeBookingDetailComponent;
  let fixture: ComponentFixture<TimeBookingDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeBookingDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeBookingDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
