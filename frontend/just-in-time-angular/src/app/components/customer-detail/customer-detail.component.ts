import { Component, OnInit } from '@angular/core';
import { Customer } from '../../models/Customer';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from '../../services/customer/customer.service';
import { SaveService } from 'src/app/services/save/save.service';

@Component({
  selector: 'jit-customer-detail',
  templateUrl: './customer-detail.component.html',
  styleUrls: ['./customer-detail.component.scss']
})
export class CustomerDetailComponent implements OnInit {

  customer: Customer;

  constructor(private route: ActivatedRoute, 
    private customerService: CustomerService,
    private saveService: SaveService) { }

  ngOnInit() {
    this.getData();
  }

  setDirty(): void {
    this.saveService.setSaved(false);
  }

  getData(): void {
    const id: number = +this.route.snapshot.paramMap.get('id');

    if (id !== 0) {
      this.customerService.get(id).subscribe((customer) => {
        this.customer = customer;
      });
    } else {
      this.customer = new Customer();
    }
  }

  saveChanges(): void {
    if (this.customer.id === undefined) {
      this.customerService.create(this.customer).subscribe((customer) => {
        this.customer = customer;
        this.saveService.setSaved(true);
      });
    } else {
      this.customerService.update(this.customer).subscribe((customer) => {
        this.customer = customer;
        this.saveService.setSaved(true);
      });
    }
  }


}
