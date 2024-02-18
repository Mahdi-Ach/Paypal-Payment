import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupErrorModelComponent } from './popup-error-model.component';

describe('PopupErrorModelComponent', () => {
  let component: PopupErrorModelComponent;
  let fixture: ComponentFixture<PopupErrorModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PopupErrorModelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PopupErrorModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
