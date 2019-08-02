import { Component, OnInit, Input } from '@angular/core';
import { Milestone } from '../../models/Milestone';

@Component({
  selector: 'jit-milestone',
  templateUrl: './milestone.component.html',
  styleUrls: ['./milestone.component.scss']
})
export class MilestoneComponent implements OnInit {

  @Input()
  milestone: Milestone

  constructor() { }

  ngOnInit() {
  }

}
