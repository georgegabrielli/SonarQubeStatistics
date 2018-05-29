import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Path} from '../commons/path.model';
import {GroupService} from '../commons/group.service';
import {ActivatedRoute} from '@angular/router';
import {Group} from '../commons/group.model';
import {PathListComponent} from './path-list/path-list.component';

@Component({
  selector: 'app-path',
  templateUrl: './path.component.html',
  styleUrls: ['./path.component.css']
})
export class PathComponent implements OnInit {

  group: Group;

  public textFilled = false;

  @ViewChild(PathListComponent) child;

  constructor(private groupService: GroupService, private route: ActivatedRoute) {}

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.groupService.getGroup(id).subscribe(group => {
      this.group = group;
      this.child.setPaths(group.paths);
    });
  }

  addPath(value: string) {
    let path: Path;
    path = new Path();
      path.value = value;
      this.group.paths.push(path);
      this.groupService.updateGroup(this.group).subscribe(data => this.child.setPaths(this.group.paths));
  }

  onChange(value: String) {
    this.textFilled = value.length !== 0;
  }
}
