import { Component } from '@angular/core';
import { DrawerService } from 'src/app/service/drawer.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent {
  constructor(
    public drawerService: DrawerService
  ) {}
}
