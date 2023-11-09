import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSkierComponent } from './add-skier.component';

describe('AddSkierComponent', () => {
  let component: AddSkierComponent;
  let fixture: ComponentFixture<AddSkierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSkierComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSkierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
