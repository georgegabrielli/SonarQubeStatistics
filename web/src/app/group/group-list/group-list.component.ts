import {Component, OnInit} from '@angular/core';
import {Group} from '../../commons/group.model';
import {Router} from '@angular/router';
import {GroupService} from '../../commons/group.service';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {
  errorMessage: string;
  groups: Group[];
  selectedGroup: Group;

  constructor(private groupService: GroupService, private router: Router) {
  }

  ngOnInit() {
    this.getGroups();
  }

  private getGroups() {
    this.groupService.getGroups()
      .subscribe(
        groups => this.groups = groups,
        error => {this.errorMessage = <any>error; this.handleError(error);});
  }

  onSelect(group: Group) {
    this.selectedGroup = group;
    this.router.navigate(['/groups/paths', this.selectedGroup.id]);
  }

  delete(group: Group) {
    this.groupService.delete(group.id)
      .subscribe(() => {
          this.groups = this.groups.filter(g => g !== group);
          if (this.selectedGroup === group) {
            this.selectedGroup = null;
          }
        }
      );
  }

  private handleError(error: any | Response) {
    console.log('errrrrrr!!!!!!!!!');
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    alert('ERROR');
  }
}

