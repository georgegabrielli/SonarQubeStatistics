import { Component, OnInit } from '@angular/core';
import {Path} from '../../commons/path.model';

@Component({
  selector: 'app-path-list',
  templateUrl: './path-list.component.html',
  styleUrls: ['./path-list.component.css']
})
export class PathListComponent implements OnInit {

  public paths: Path[];
  selectedPath: Path;

  constructor() {
    this.paths = [];
  }

  ngOnInit() {
  }


  setPaths(paths: Path[]) {
    console.log(paths);
    this.paths = paths;
  }

}
