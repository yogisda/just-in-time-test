import { Component, OnInit } from '@angular/core';
import { MilestoneService } from '../../services/milestone/milestone.service';
import { Milestone } from '../../models/Milestone';
import { SaveService } from 'src/app/services/save/save.service';
import { MatDatepickerInputEvent } from '@angular/material';
import { ProjectService } from 'src/app/services/project/project.service';
import { Project } from 'src/app/models/Project';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jit-milestone-detail',
  templateUrl: './milestone-detail.component.html',
  styleUrls: ['./milestone-detail.component.scss']
})

export class MilestoneDetailComponent implements OnInit {

  milestone:Milestone;
  projects:Project[];

  constructor(private route: ActivatedRoute, private milestoneService: MilestoneService, private projectService: ProjectService, private saveService:SaveService) { 

  }

  ngOnInit() {
    this.getData()
    
  }

  getData(){
    const id: number = +this.route.snapshot.paramMap.get('id');

    if (id !== 0) {
      this.milestoneService.get(id).subscribe((milestone) => {
        this.milestone = milestone;
        if(this.milestone.endDate == null) {
          this.milestone.endDate = new Date();
        }
      });
    } else {
      this.milestone = new Milestone();
      this.milestone.endDate = new Date();
    }

    this.projectService.getAll().subscribe((p)=>{
      this.projects = p;
    });
  }

  setDirty():void{
    this.saveService.setSaved(false);
  }

  setDate(date, event: MatDatepickerInputEvent<Date>): void {
      date.setFullYear(event.value.getFullYear(), event.value.getMonth(), event.value.getDate());
      this.setDirty();
  }

  saveChanges():void{
    if(this.milestone.id) {
      this.milestoneService.update(this.milestone).subscribe((m)=>{
        this.milestone = m;
        this.saveService.setSaved(true);
      })
    }
    else {
      this.milestoneService.create(this.milestone).subscribe((m)=>{
        this.milestone = m;
        this.saveService.setSaved(true);
      })
    }
  }
}
