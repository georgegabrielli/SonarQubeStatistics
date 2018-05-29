import {Component, OnInit, ViewChild} from '@angular/core';
import {GroupService} from '../commons/group.service';
import {GroupListComponent} from './group-list/group-list.component';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  public textFilled = false;

  @ViewChild(GroupListComponent) child;

  constructor(private groupService: GroupService) { }

  ngOnInit() {
  }

  onChange(value: String) {
    console.log(value.length);
    this.textFilled = value.length !== 0;
  }

  addGroup(value: string) {
    console.log('add');
    this.groupService.create(value).subscribe(data => {alert('Added'); }, error => {alert(error); });
    this.child.getGroups();
  }
}
